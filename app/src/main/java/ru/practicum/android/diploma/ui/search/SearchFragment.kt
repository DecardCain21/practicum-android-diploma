package ru.practicum.android.diploma.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.Source.SEARCH
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.state.VacancyState
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Data
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Empty
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Error
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Loading
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.NoInternet
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Start
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.EndingConvertor
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.getConnected
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()
    private var onVacancyClickDebounce: ((String) -> Unit)? = null
    private var vacanciesAdapter: VacanciesAdapter = VacanciesAdapter { vacancyId ->
        onVacancyClickDebounce?.let { it(vacancyId) }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onVacancyClickDebounce =
            debounce(
                coroutineScope = viewLifecycleOwner.lifecycleScope,
                useLastParam = false
            ) { vacancyId ->
                findNavController().navigate(
                    R.id.action_searchFragment_to_vacancyFragment,
                    VacancyFragment.createArgs(id = vacancyId, source = SEARCH)
                )
            }

        configureRecycler()
        configureSearchInput()
        configureFilterButton()
        checkFilterIcon()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                render(state)
            }
        }
    }

    private fun checkFilterIcon() {
        val resId = when (viewModel.isFilterApplied()) {
            false -> R.drawable.ic_filter_default
            true -> R.drawable.ic_filter_applied
        }
        binding.ivFilter.setImageResource(resId)
    }

    private fun render(state: VacancyState) {
        when (state.vacanciesList) {
            is Start -> showStart()
            is NoInternet -> showNoInternet()
            is Empty -> showNoResult()
            is Loading -> showLoading()
            is Error -> showError()
            is Data -> showContent(state.vacanciesList)
        }
    }

    private fun showStart() {
        with(binding) {
            pbSearch.invisible()
            rvVacancies.invisible()
            ivLookingForPlaceholder.visible()
            placeholder.layoutPlaceholder.invisible()
            tvCountVacancies.invisible()
            vacanciesAdapter.clear()
        }
    }

    private fun showNoInternet() {
        if (vacanciesAdapter.itemCount == 0) {
            with(binding) {
                rvVacancies.invisible()
                ivLookingForPlaceholder.invisible()
                pbSearch.invisible()
                placeholder.layoutPlaceholder.visible()
                tvCountVacancies.invisible()
                placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_no_internet_skull)
                placeholder.tvPlaceholder.text = resources.getString(R.string.no_internet)
            }
        } else {
            showToast(R.string.toast_check_your_internet_connection)
        }
    }

    private fun showNoResult() {
        with(binding) {
            rvVacancies.invisible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            placeholder.layoutPlaceholder.visible()
            tvCountVacancies.visible()
            tvCountVacancies.text = resources.getText(R.string.no_such_vacancies)
            vacanciesAdapter.clear()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_vacancy_list_or_region_plate_cat)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_vacancy_list)
        }
    }

    private fun showLoading() {
        with(binding) {
            pbSearch.visible()
            rvVacancies.invisible()
            ivLookingForPlaceholder.invisible()
            placeholder.layoutPlaceholder.invisible()
            tvCountVacancies.invisible()
        }
    }

    private fun showError() {
        with(binding) {
            rvVacancies.invisible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            placeholder.layoutPlaceholder.visible()
            tvCountVacancies.invisible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_server_error_cry)
            placeholder.tvPlaceholder.text = resources.getString(R.string.server_error)
            showToast(R.string.toast_error_has_occurred)
        }
    }

    private fun showContent(vacanciesList: Data) {
        with(binding) {
            rvVacancies.visible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            vacanciesAdapter.updateVacancies(vacanciesList.vacancies)
            placeholder.layoutPlaceholder.invisible()
            tvCountVacancies.let {
                it.visible()
                it.text = convertToFoundVacancies(vacanciesList.totalVacancyCount)
            }
        }
    }

    private fun configureSearchInput() = binding.etSearch.doOnTextChanged { text, _, _, _ ->
        binding.ivEditTextButton.isEnabled = false
        with(binding.ivEditTextButton) {
            setImageResource(if (text.isNullOrEmpty()) R.drawable.ic_search else R.drawable.ic_close)
            setOnClickListener {
                val inputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.ivEditTextButton.windowToken, 0)
                binding.etSearch.text.clear()
                viewModel.clearSearch()
                clearFocus()
            }
        }
        text?.let {
            viewModel.searchDebounce(it.toString())
            binding.ivEditTextButton.isEnabled = true
        }
    }

    private fun configureRecycler() {
        binding.rvVacancies.apply {
            adapter = vacanciesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0 && shouldLoadNextPage()) {
                        handleLoadingIndicator()
                        viewModel.onLastItemReached()
                    }
                }
            })
        }
    }

    private fun shouldLoadNextPage(): Boolean {
        val layoutManager = binding.rvVacancies.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = vacanciesAdapter.itemCount
        return lastVisibleItemPosition >= itemCount - 1 && viewModel.isNotLastPage
    }

    private fun handleLoadingIndicator() {
        binding.pbSearch.visibility = if (getConnected()) View.VISIBLE else View.INVISIBLE
    }

    private fun configureFilterButton() {
        binding.ivFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtersFragment)
        }
    }

    private fun convertToFoundVacancies(amount: Int): String {
        val vacanciesWord = resources.getString(EndingConvertor.vacancies(amount))
        return "${resources.getString(R.string.found_word)} $amount $vacanciesWord"
    }
}
