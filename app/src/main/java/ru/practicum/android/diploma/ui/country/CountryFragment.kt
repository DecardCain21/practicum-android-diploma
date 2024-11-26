package ru.practicum.android.diploma.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.domain.state.CountryState
import ru.practicum.android.diploma.ui.adapters.FilterAdapter
import ru.practicum.android.diploma.util.BindingFragment

class CountryFragment : BindingFragment<FragmentCountryBinding>() {

    private val viewModel: CountryViewModel by viewModel()
    private var filterAdapter: FilterAdapter = FilterAdapter { countryId -> selectCountry(countryId) }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCountryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCountries()
        configureBackButton()
        binding.rvCountries.adapter = filterAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                render(state)
            }
        }
    }

    private fun render(state: CountryState) {
        when (state) {
            is CountryState.Data -> {
                filterAdapter.updateCountries(state.countries)
            }

            CountryState.Empty -> {
                //
            }

            CountryState.Error -> {
                //
            }

            CountryState.Loading -> {
                //
            }
        }
    }

    private fun selectCountry(id: String) {

    }

    private fun configureBackButton() =
        binding.tbHeader.setNavigationOnClickListener { findNavController().popBackStack() }
}
