package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.NavigationJobApp.Companion.applicationContext
import ru.practicum.android.diploma.common.Source
import ru.practicum.android.diploma.data.formatter.SalaryFormatter
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.state.VacancyDetailsState
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {

    private val viewModel: VacancyViewModel by viewModel()
    private val salaryFormatter = SalaryFormatter(applicationContext())

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVacancyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = requireArguments().getString(VACANCY_DETAILS).orEmpty()
        val vacancySource = requireArguments().getSerializable(SOURCE_KEY) as Source

        configureBackButton()
        configureShareButton()
        configureAddToFavoriteButton(vacancyId)

        viewModel.state.observe(viewLifecycleOwner) { render(it) }
        viewModel.getVacancyDetails(vacancyId, vacancySource)
    }

    private fun render(state: VacancyDetailsState) {
        when (state.data) {
            is VacancyDetailsState.Data.Loading -> showLoading()
            is VacancyDetailsState.Data.Empty -> showEmpty()
            is VacancyDetailsState.Data.Payload -> showContent(state.data.details)
            is VacancyDetailsState.Data.Error -> showError()
            is VacancyDetailsState.Data.NoInternet -> showNoInternet()
        }

        val resId = when (state.favorite) {
            is VacancyDetailsState.Favorite.InFavorite -> R.drawable.ic_favorite_added
            is VacancyDetailsState.Favorite.NotInFavorite -> R.drawable.ic_favorite_add
        }
        binding.ivFavorites.setImageResource(resId)
    }

    private fun configureBackButton() =
        binding.tbHeader.setNavigationOnClickListener { findNavController().popBackStack() }

    private fun configureAddToFavoriteButton(vacancyId: String) =
        binding.ivFavorites.setOnClickListener { viewModel.onFavoriteClicked(vacancyId) }

    private fun configureShareButton() =
        binding.ivSharing.setOnClickListener { viewModel.share() }

    private fun showEmpty() = with(binding) {
        groupVacancy.invisible()
        placeholder.layoutPlaceholder.visible()
        pbVacancy.invisible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.vacancy_not_found_or_deleted_andy)
        placeholder.tvPlaceholder.text = resources.getString(R.string.no_vacancy)
    }

    private fun showLoading() = with(binding) {
        groupVacancy.invisible()
        placeholder.layoutPlaceholder.invisible()
        pbVacancy.visible()
    }

    private fun showError() = with(binding) {
        groupVacancy.invisible()
        placeholder.layoutPlaceholder.visible()
        pbVacancy.invisible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_server_error_cat)
        placeholder.tvPlaceholder.text = resources.getString(R.string.server_error)
    }

    private fun showNoInternet() = with(binding) {
        groupVacancy.invisible()
        placeholder.layoutPlaceholder.visible()
        pbVacancy.invisible()
        placeholder.ivPlaceholder.setImageResource(R.drawable.placeholder_vacancy_search_no_internet_skull)
        placeholder.tvPlaceholder.text = resources.getString(R.string.no_internet)
    }

    private fun showContent(vacancy: VacancyDetails) = with(binding) {
        groupVacancy.visible()
        placeholder.layoutPlaceholder.invisible()
        pbVacancy.invisible()
        tvVacancyName.text = vacancy.name
        tvVacancyEmployerName.text = vacancy.employerName
        tvVacancyEmployerCity.text = if (vacancy.address != "") vacancy.address else vacancy.area
        tvVacancyExperience.text = vacancy.experience
        tvVacancySchedule.text = vacancy.schedule
        tvVacancyDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT)
        tvVacancySkills.text = vacancy.descriptionSkills
        with(vacancy) {
            tvVacancySalary.text = salaryFormatter.salaryFormat(
                salaryTo = salaryTo,
                salaryFrom = salaryFrom,
                currency = currency
            )
        }

        if (vacancy.descriptionSkills.isEmpty()) {
            tvVacancyTitleSkills.invisible()
            tvVacancySkills.invisible()
        }

        Glide.with(requireContext())
            .load(vacancy.imageUrl)
            .placeholder(R.drawable.placeholder_logo)
            .fitCenter()
            .into(binding.sivVacancyLogo)
    }

    companion object {
        private const val VACANCY_DETAILS = "vacancy_details"
        private const val SOURCE_KEY = "source_key"
        fun createArgs(id: String, source: Source) = bundleOf(
            VACANCY_DETAILS to id,
            SOURCE_KEY to source
        )
    }
}
