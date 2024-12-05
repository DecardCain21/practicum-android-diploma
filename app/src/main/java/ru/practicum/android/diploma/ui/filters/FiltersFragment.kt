package ru.practicum.android.diploma.ui.filters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.AppConstants.EMPTY_PARAM_VALUE
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.state.FiltersState
import ru.practicum.android.diploma.domain.state.FiltersState.Data.Payload
import ru.practicum.android.diploma.domain.state.FiltersState.Editor.Changed
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.toIntOrNull

class FiltersFragment : BindingFragment<FragmentFilterBinding>() {

    private val viewModel: FiltersViewModel by viewModel()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFilterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureBackButton()
        configureLocationWorkButton()
        configureIndustryButton()
        configureSalaryInput()
        configureWithoutSalaryCheckbox()
        configureApplyButton()
        configureResetButton()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state -> render(state) }
        }

        // viewModel.getFilters()
    }

    private fun render(state: FiltersState) {
        binding.cbApplyButton.isVisible = state.editor == Changed

        when (val dataState = state.data) {
            is Payload -> {
                showContent(dataState.filters)
                binding.tvResetButton.isVisible = dataState.filters != Filter.empty
            }
        }
    }

    private fun configureBackButton() =
        binding.tbHeader.setNavigationOnClickListener {
            viewModel.clearTmpFilters()
            findNavController().popBackStack()
        }

    private fun configureLocationWorkButton() = with(binding) {
        tlPlaceWorkLayout.editText?.setOnClickListener {
            findNavController().navigate(R.id.action_filters_fragment_to_location_fragment)
        }
        tlPlaceWorkLayout.setEndIconOnClickListener {
            when (tlPlaceWorkLayout.editText?.text.toString().isEmpty()) {
                true -> findNavController().navigate(R.id.action_filters_fragment_to_location_fragment)
                else -> {
                    viewModel.clearLocation()
                    setViewState(tlPlaceWorkLayout, EMPTY_PARAM_VALUE)
                }
            }
        }
    }

    private fun configureIndustryButton() = with(binding) {
        tlBranchLayout.editText?.setOnClickListener {
            findNavController().navigate(R.id.action_filters_fragment_to_industryFragment)
        }
        tlBranchLayout.setEndIconOnClickListener {
            when (tlBranchLayout.editText?.text.isNullOrEmpty()) {
                true -> findNavController().navigate(R.id.action_filters_fragment_to_industryFragment)
                else -> {
                    viewModel.clearIndustry()
                    setViewState(tlBranchLayout, EMPTY_PARAM_VALUE)
                }
            }
        }
    }

    private fun configureSalaryInput() = with(binding) {
        tiSalaryInputText.doOnTextChanged { text, _, _, _ ->
            tlSalaryLayout.editText?.isFocusable = true
            viewModel.setSalary(text.toString())
        }
        tlSalaryLayout.setEndIconOnClickListener {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(tlSalaryLayout.windowToken, 0)
            tlSalaryLayout.clearFocus()
            viewModel.setSalary("")
        }
    }

    private fun configureWithoutSalaryCheckbox() = with(binding) {
        cbWithoutSalaryButton.setOnCheckedChangeListener { _, isChecked ->
            val hintColor = if (isChecked) R.color.black else R.color.blue
            tlSalaryLayout.hintTextColor = ContextCompat.getColorStateList(requireContext(), hintColor)
            viewModel.setWithoutSalaryButton(isChecked)
        }
    }

    private fun configureApplyButton() = with(binding) {
        cbApplyButton.setOnClickListener {
            viewModel.saveFilters()
            findNavController().popBackStack()
        }
    }

    private fun configureResetButton() = with(binding) {
        tvResetButton.setOnClickListener { viewModel.clearFilters() }
    }

    private fun showContent(state: Filter) {
        setViewState(binding.tlPlaceWorkLayout, state.location?.description)
        setViewState(binding.tlBranchLayout, state.industry?.name)
        if (binding.tiSalaryInputText.text.toIntOrNull() != state.salary) {
            binding.tiSalaryInputText.setText(state.salary?.toString() ?: "")
        }
        val isFieldEmpty = binding.tiSalaryInputText.text.isNullOrEmpty()
        if (!isFieldEmpty) {
            binding.tlSalaryLayout.defaultHintTextColor =
                requireContext().getColorStateList(R.color.text_color_hint_selection)
        } else {
            binding.tlSalaryLayout.defaultHintTextColor = requireContext().getColorStateList(R.color.text_color_hint)
        }

        binding.cbWithoutSalaryButton.isChecked = state.withoutSalaryButton
    }

    private fun setViewState(layout: TextInputLayout, content: String?) {
        layout.editText?.setText(content)
        when {
            layout.editText?.text.isNullOrEmpty() -> {
                layout.setEndIconDrawable(R.drawable.ic_arrow_forward)
                layout.defaultHintTextColor = requireContext().getColorStateList(R.color.dark_grey)
            }

            else -> {
                layout.setEndIconDrawable(R.drawable.ic_close)
                layout.defaultHintTextColor = requireContext().getColorStateList(R.color.text_color_hint_selection)
            }
        }
    }
}
