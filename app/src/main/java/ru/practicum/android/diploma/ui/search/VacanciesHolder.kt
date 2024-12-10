package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.formatter.SalaryFormatter
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesHolder(
    private val binding: VacancyItemBinding,
    private val clickListener: VacanciesAdapter.VacancyClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val salaryFormatter = SalaryFormatter(binding.root.context)
    fun bind(vacancy: Vacancy) = with(binding) {
        vacancy.imageUrl?.let { setLogo(it) }
        vacancyTitle.text = vacancy.name
        vacancyEmployer.text = vacancy.employerName
        vacancySalary.text = setSalary(vacancy)

        itemView.setOnClickListener {
            clickListener.onVacancyClick(vacancy.id)
        }
    }

    private fun setLogo(url: String) = Glide.with(itemView)
        .load(url)
        .fitCenter()
        .placeholder(R.drawable.placeholder_logo)
        .into(binding.vacancyLogo)

    private fun setSalary(vacancy: Vacancy): String {
        return with(vacancy) {
            salaryFormatter.salaryFormat(
                salaryTo = salaryTo,
                salaryFrom = salaryFrom,
                currency = currency
            )
        }
    }
}
