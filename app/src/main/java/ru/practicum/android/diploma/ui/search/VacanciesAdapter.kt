package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesAdapter(
    private val clickListener: VacancyClickListener
) : RecyclerView.Adapter<VacanciesHolder>() {

    private var vacancies: List<Vacancy> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacanciesHolder(
            binding = VacancyItemBinding.inflate(layoutInspector, parent, false),
            clickListener = clickListener
        )
    }

    override fun onBindViewHolder(holder: VacanciesHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
    }

    override fun getItemCount(): Int = vacancies.size

    fun updateVacancies(newVacancies: List<Vacancy>) {
        val startPosition = vacancies.size
        if (newVacancies == vacancies) return
        vacancies = vacancies + newVacancies
        notifyItemRangeInserted(startPosition, newVacancies.size)
    }

    fun clear() {
        vacancies = emptyList()
        notifyDataSetChanged()
        val startPosition = vacancies.size
        notifyItemRangeInserted(startPosition, 0)
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancyId: String)
    }
}
