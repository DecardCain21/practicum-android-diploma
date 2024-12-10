package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.AppConstants.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.common.Source.FAVORITE
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.state.FavoriteVacanciesState
import ru.practicum.android.diploma.ui.search.VacanciesAdapter
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.ImageAndTextHelper
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.invisible
import ru.practicum.android.diploma.util.visible

class FavoriteFragment : BindingFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()
    private val imageAndTextHelper: ImageAndTextHelper by inject()
    private var onVacancyClickDebounce: ((String) -> Unit)? = null
    private var vacanciesAdapter: VacanciesAdapter = VacanciesAdapter { vacancyId ->
        onVacancyClickDebounce?.let { it(vacancyId) }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onVacancyClickDebounce =
            debounce(
                CLICK_DEBOUNCE_DELAY,
                viewLifecycleOwner.lifecycleScope,
                false
            ) { vacancyId ->
                findNavController().navigate(
                    R.id.action_favoriteFragment_to_vacancyFragment,
                    VacancyFragment.createArgs(id = vacancyId, source = FAVORITE)
                )
            }

        viewModel.getFavoriteVacancies()
        binding.rvVacancies.adapter = vacanciesAdapter
        viewModel.state.observe(viewLifecycleOwner) { render(it) }
    }

    private fun render(state: FavoriteVacanciesState) {
        when (state) {
            is FavoriteVacanciesState.Data -> showContent(state.vacancies)
            is FavoriteVacanciesState.Empty -> showEmpty()
            is FavoriteVacanciesState.Error -> showError()
        }
    }

    private fun showContent(vacancies: List<Vacancy>) {
        with(binding) {
            placeholder.layoutPlaceholder.invisible()
            rvVacancies.visible()
            vacanciesAdapter.clear()
            vacanciesAdapter.updateVacancies(vacancies)
        }
    }

    private fun showEmpty() {
        with(binding) {
            placeholder.layoutPlaceholder.visible()
            rvVacancies.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                placeholder.ivPlaceholder,
                placeholder.tvPlaceholder,
                R.drawable.favorite_empty_list_andy,
                resources.getString(R.string.list_is_empty)
            )
        }
    }

    private fun showError() {
        with(binding) {
            placeholder.layoutPlaceholder.visible()
            rvVacancies.invisible()
            imageAndTextHelper.setImageAndText(
                requireContext(),
                placeholder.ivPlaceholder,
                placeholder.tvPlaceholder,
                R.drawable.placeholder_no_vacancy_list_or_region_plate_cat,
                resources.getString(R.string.no_vacancy_list)
            )
        }
    }
}
