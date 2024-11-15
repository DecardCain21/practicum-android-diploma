package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDiffCallback(
    private val oldList: List<Vacancy>,
    private val newList: List<Vacancy>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
