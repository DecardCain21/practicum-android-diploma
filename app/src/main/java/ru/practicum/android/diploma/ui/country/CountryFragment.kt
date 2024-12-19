package ru.practicum.android.diploma.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.state.CountryState
import ru.practicum.android.diploma.domain.state.CountryState.Data
import ru.practicum.android.diploma.domain.state.CountryState.Empty
import ru.practicum.android.diploma.domain.state.CountryState.Error
import ru.practicum.android.diploma.domain.state.CountryState.Loading
import ru.practicum.android.diploma.domain.state.CountryState.NoInternet
import ru.practicum.android.diploma.ui.adapters.FilterAdapter
import ru.practicum.android.diploma.ui.adapters.FilterAdapter.SaveFilterListener
import ru.practicum.android.diploma.ui.adapters.ItemFilter
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class CountryFragment : BindingFragment<FragmentCountryBinding>() {

    private val filterAdapter = FilterAdapter()
    private val viewModel: CountryViewModel by viewModel()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCountryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureBackButton()
        configureCountriesAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state -> render(state) }
        }

        viewModel.getCountries()
    }

    private fun render(state: CountryState) {
        when (state) {
            is Data -> showContent(state.countries)
            is Empty -> showEmpty()
            is Error -> showError()
            is Loading -> showLoading()
            is NoInternet -> showNoInternet()
        }
    }

    private fun configureBackButton() =
        binding.tbHeader.setNavigationOnClickListener { findNavController().popBackStack() }

    private fun configureCountriesAdapter() = with(binding) {
        rvCountries.adapter = filterAdapter
        filterAdapter.saveFilterListener = object : SaveFilterListener {
            override fun onItemClicked(item: ItemFilter) {
                viewModel.setCountry(item.data as Country)
                findNavController().navigateUp()
            }
        }
    }

    private fun showNoInternet() {
        with(binding) {
            rvCountries.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_no_internet_skull)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_internet)
        }
    }

    private fun showLoading() {
        with(binding) {
            pbSearch.visible()
            rvCountries.invisible()
            placeholder.layoutPlaceholder.invisible()
        }
    }

    private fun showEmpty() {
        with(binding) {
            rvCountries.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_no_vacancy_list_or_region_plate_cat)
            placeholder.tvPlaceholder.text = resources.getString(R.string.no_vacancy_list)
        }
    }

    private fun showError() {
        with(binding) {
            rvCountries.invisible()
            pbSearch.invisible()
            placeholder.layoutPlaceholder.visible()
            placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_server_error_cry)
            placeholder.tvPlaceholder.text = resources.getString(R.string.server_error)
            showToast(R.string.toast_error_has_occurred)
        }
    }

    private fun showContent(countryList: List<Country>) {
        with(binding) {
            rvCountries.visible()
            pbSearch.invisible()
            filterAdapter.updateCountries(countryList)
            placeholder.layoutPlaceholder.invisible()
        }
    }
}
