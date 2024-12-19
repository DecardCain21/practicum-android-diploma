package ru.practicum.android.diploma.ui.industry

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.state.IndustryState
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Data
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Empty
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Error
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Loading
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.NoInternet
import ru.practicum.android.diploma.ui.adapters.industry.IndustryAdapter
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class IndustryFragment : BindingFragment<FragmentIndustryBinding>() {

    private val viewModel: IndustryViewModel by viewModel()

    private var inputMethodManager: InputMethodManager? = null
    private val filterAdapter: IndustryAdapter = IndustryAdapter { industry ->
        viewModel.setFilters(industry)
        binding.cbApplyButton.isVisible = industry != null
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentIndustryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSearch()
        configureBackButton()
        configureApplyButton()
        configureIndustriesAdapter()

        inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state -> render(state) }
        }
        viewModel.getIndustries()
    }

    private fun render(state: IndustryState) {
        handleSelectedIndustry(state.selectedIndustry)

        when (state.data) {
            is Empty -> showEmpty()
            is Error -> showError()
            is Loading -> showLoading()
            is NoInternet -> showNoInternet()
            is Data -> showContent(state.data.industries)
        }
    }

    private fun configureBackButton() = binding.tbHeader
        .setNavigationOnClickListener { findNavController().popBackStack() }

    private fun configureApplyButton() = binding.cbApplyButton
        .setOnClickListener { findNavController().popBackStack() }

    private fun configureIndustriesAdapter() =
        with(binding) { rvVacancies.adapter = filterAdapter }

    private fun showNoInternet() = with(binding) {
        rvVacancies.invisible()
        pbSearch.invisible()
        cbApplyButton.invisible()
        placeholder.layoutPlaceholder.visible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_no_internet_skull)
        placeholder.tvPlaceholder.text = resources.getString(R.string.no_internet)
    }

    private fun showLoading() = with(binding) {
        pbSearch.visible()
        rvVacancies.invisible()
        cbApplyButton.invisible()
        placeholder.layoutPlaceholder.invisible()
    }

    private fun showEmpty() = with(binding) {
        rvVacancies.invisible()
        pbSearch.invisible()
        cbApplyButton.invisible()
        placeholder.layoutPlaceholder.visible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_vacancy_list_or_region_plate_cat)
        placeholder.tvPlaceholder.text = resources.getString(R.string.no_such_industry)
    }

    private fun showError() = with(binding) {
        rvVacancies.invisible()
        cbApplyButton.invisible()
        pbSearch.invisible()
        placeholder.layoutPlaceholder.visible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_region_list_carpet)
        placeholder.tvPlaceholder.text = resources.getString(R.string.no_region_list)
        showToast(R.string.toast_error_has_occurred)
    }

    private fun showContent(industryList: List<Industry>) = with(binding) {
        rvVacancies.visible()
        pbSearch.invisible()
        placeholder.layoutPlaceholder.invisible()
        filterAdapter.updateIndustries(industryList)
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun configureSearch() = binding.etSearch.doOnTextChanged { text, _, _, _ ->
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
        text?.let { viewModel.searchDebounce(it.toString()) }
    }

    private fun handleSelectedIndustry(industry: Industry?) {
        filterAdapter.setCheckedIndustry(industry)
        binding.cbApplyButton.isVisible = industry != null
    }
}
