package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.state.VacancyDetailsState
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.ImageAndTextHelper
import ru.practicum.android.diploma.util.SalaryFormatter
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {

    private val viewModel: VacancyViewModel by viewModel()
    private val imageAndTextHelper: ImageAndTextHelper by inject()
    private val salaryFormat = SalaryFormatter()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVacancyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetails(requireArguments().getString(VACANCY_DETAILS) ?: "")
        binding.tbHeader.setNavigationOnClickListener { findNavController().popBackStack() }
        viewModel.state.observe(viewLifecycleOwner) { render(it) }

        binding.ivSharing.setOnClickListener {
            viewModel.shareVacancyURL()
        }
    }

    private fun render(state: VacancyDetailsState) {
        when (state.details) {
            is VacancyDetailsState.Vacancy.Loading -> showLoading()
            is VacancyDetailsState.Vacancy.Empty -> showEmpty()
            is VacancyDetailsState.Vacancy.Data -> showContent(state.details.vacancy)
            is VacancyDetailsState.Vacancy.Error -> showError()
        }
    }

    private fun showEmpty() {
        with(binding) {
            groupVacancy.invisible()
            groupPlaceholder.visible()
            pbVacancy.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                ivPlaceholder,
                tvPlaceholder,
                R.drawable.vacancy_not_found_or_deleted_andy,
                resources.getString(R.string.no_vacancy)
            )
        }
    }

    private fun showLoading() {
        with(binding) {
            groupVacancy.invisible()
            groupPlaceholder.invisible()
            pbVacancy.visible()
        }
    }

    private fun showError() {
        with(binding) {
            groupVacancy.invisible()
            groupPlaceholder.visible()
            pbVacancy.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                ivPlaceholder,
                tvPlaceholder,
                R.drawable.placeholder_vacancy_server_error_cat,
                resources.getString(R.string.server_error)
            )
        }
    }

    private fun showContent(vacancy: VacancyDetails) {
        with(binding) {
            groupVacancy.visible()
            groupPlaceholder.invisible()
            pbVacancy.invisible()
            tvVacancyName.text = vacancy.name
            tvVacancySalary.text =
                salaryFormat.salaryFormat(vacancy.salaryTo, vacancy.salaryFrom, vacancy.currency, resources)
            tvVacancyEmployerName.text = vacancy.employerName
            tvVacancyEmployerCity.text = vacancy.city
            tvVacancyExperience.text = vacancy.experience
            tvVacancySchedule.text = vacancy.schedule
            tvVacancyDescription.setText(Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT))
            tvVacancySkills.text = vacancy.descriptionSkills
            if (vacancy.descriptionSkills.isEmpty()) {
                tvVacancyTitleSkills.invisible()
                tvVacancySkills.invisible()
            }

            Glide.with(requireContext())
                .load(vacancy.imageUrl)
                .placeholder(R.drawable.placeholder_logo)
                .centerCrop()
                .into(binding.sivVacancyLogo)
        }
    }

    companion object {
        private const val VACANCY_DETAILS = "vacancy_details"
        fun createArgs(id: String): Bundle =
            bundleOf(VACANCY_DETAILS to id)
    }
}
