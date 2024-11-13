package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesAdapter : RecyclerView.Adapter<VacanciesHolder>() {

    private var vacancies: List<Vacancy> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacanciesHolder(VacancyItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: VacanciesHolder, position: Int) {
        // holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size

    fun setVacancies(list: List<Vacancy>) {
        vacancies = list
        notifyDataSetChanged()
    }

    interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}