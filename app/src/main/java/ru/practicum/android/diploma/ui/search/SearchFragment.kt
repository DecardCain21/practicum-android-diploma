package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.state.VacancyState
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList.Data
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.ImageAndTextHelper
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val vacancyAdapter = VacanciesAdapter()
    private val viewModel: SearchViewModel by viewModel()
    private val imageAndTextHelper: ImageAndTextHelper by inject()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecycler()
        configureSearchInput()
        listenRecycler()

        viewModel.state.observe(viewLifecycleOwner) { render(it) }
    }

    private fun render(state: VacancyState) {
        when (state.vacanciesList) {
            is VacancyState.VacanciesList.Empty -> showEmpty()
            is VacancyState.VacanciesList.NoInternet -> showNoInternet()
            is VacancyState.VacanciesList.Loading -> showLoading()
            is VacancyState.VacanciesList.Error -> showError()
            is Data -> showContent(state.vacanciesList.vacancies)
        }
    }

    private fun showEmpty() {
        with(binding) {
            rvVacancies.invisible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            groupPlaceholder.visible()
            tvCountVacancies.visible()
            tvCountVacancies.text = resources.getText(R.string.not_vacancies)
            imageAndTextHelper.setImageAndText(
                requireContext(),
                ivPlaceholder,
                tvPlaceholder,
                R.drawable.placeholder_no_vacancy_list_or_region_plate_cat,
                resources.getString(R.string.no_vacancy_list)
            )
        }
    }

    private fun showNoInternet() {
        with(binding) {
            rvVacancies.invisible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            groupPlaceholder.visible()
            tvCountVacancies.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                ivPlaceholder,
                tvPlaceholder,
                R.drawable.placeholder_vacancy_search_no_internet_skull,
                resources.getString(R.string.no_internet)
            )
        }
    }

    private fun showLoading() {
        with(binding) {
            pbSearch.visible()
            rvVacancies.invisible()
            ivLookingForPlaceholder.invisible()
            groupPlaceholder.invisible()
            tvCountVacancies.invisible()
        }
    }

    private fun showError() {
        with(binding) {
            rvVacancies.invisible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
            groupPlaceholder.visible()
            tvCountVacancies.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                ivPlaceholder,
                tvPlaceholder,
                R.drawable.placeholder_no_region_list_carpet,
                resources.getString(R.string.no_region_list)
            )
        }
    }

    private fun showContent(vacancies: List<Vacancy>) {
        with(binding) {
            rvVacancies.visible()
            pbSearch.invisible()
            ivLookingForPlaceholder.invisible()
//            vacancyAdapter.setVacancies(vacancies)
            vacancyAdapter.updateVacancies(vacancies)
            groupPlaceholder.invisible()
            // Пока что скрою tvCountVacancies, потом нужно будет передать количество найденных вакансий
            tvCountVacancies.invisible()
        }
    }

    private fun configureSearchInput() = binding.etSearch.doOnTextChanged { text, _, _, _ ->
        text?.let { viewModel.searchDebounce(it.toString()) }
    }

    private fun configureRecycler() {
        binding.rvVacancies.adapter = vacancyAdapter
    }

    private fun listenRecycler() {
        binding.rvVacancies.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.rvVacancies.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount-1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
    }

}
