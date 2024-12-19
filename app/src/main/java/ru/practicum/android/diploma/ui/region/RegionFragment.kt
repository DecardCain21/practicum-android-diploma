package ru.practicum.android.diploma.ui.region

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.domain.state.RegionState
import ru.practicum.android.diploma.domain.state.RegionState.Data.Data
import ru.practicum.android.diploma.domain.state.RegionState.Data.Empty
import ru.practicum.android.diploma.domain.state.RegionState.Data.Error
import ru.practicum.android.diploma.domain.state.RegionState.Data.Loading
import ru.practicum.android.diploma.domain.state.RegionState.Data.NoInternet
import ru.practicum.android.diploma.ui.adapters.FilterAdapter
import ru.practicum.android.diploma.ui.adapters.FilterAdapter.SaveFilterListener
import ru.practicum.android.diploma.ui.adapters.ItemFilter
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class RegionFragment : BindingFragment<FragmentRegionBinding>() {

    private val filterAdapter = FilterAdapter()
    private val viewModel: RegionViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureBackButton()
        configureRegionsAdapter()
        configureSearchInput()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state -> render(state) }
        }

        viewModel.getRegions()
    }

    private fun render(state: RegionState) {
        when (state.data) {
            is Data -> showContent(state.data.regions)
            is Empty -> showEmpty()
            is Error -> showError()
            is Loading -> showLoading()
            is NoInternet -> showNoInternet()
        }
    }

    private fun configureBackButton() =
        binding.tbHeader.setNavigationOnClickListener { findNavController().popBackStack() }

    private fun configureRegionsAdapter() = with(binding) {
        rvRegions.adapter = filterAdapter
        filterAdapter.saveFilterListener = object : SaveFilterListener {
            override fun onItemClicked(item: ItemFilter) {
                viewModel.setRegion(item.data as Region)
                findNavController().navigateUp()
            }
        }
    }

    private fun configureSearchInput() = binding.etSearch.doOnTextChanged { text, _, _, _ ->
        with(binding.ivEditTextButton) {
            setImageResource(if (text.isNullOrEmpty()) R.drawable.ic_search else R.drawable.ic_close)
            setOnClickListener {
                val inputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.ivEditTextButton.windowToken, 0)
                binding.etSearch.text.clear()
                viewModel.clearRegion()
                clearFocus()
            }
        }
        text?.let { viewModel.sortDebounce(it.toString()) }
    }

    private fun showLoading() {
        with(binding) {
            pbSearch.visible()
            rvRegions.invisible()
            placeholder.layoutPlaceholder.invisible()
        }
    }

    private fun showNoInternet() {
        with(binding) {
            rvRegions.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_no_internet_skull)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_internet)
        }
    }

    private fun showEmpty() {
        with(binding) {
            rvRegions.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_vacancy_list_or_region_plate_cat)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_such_region)
        }
    }

    private fun showError() {
        with(binding) {
            rvRegions.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_region_list_carpet)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_region_list)
            showToast(R.string.toast_error_has_occurred)
        }
    }

    private fun showContent(regionList: List<Region>) {
        with(binding) {
            filterAdapter.updateRegions(regionList)
            rvRegions.visible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.invisible()
        }
    }
}
