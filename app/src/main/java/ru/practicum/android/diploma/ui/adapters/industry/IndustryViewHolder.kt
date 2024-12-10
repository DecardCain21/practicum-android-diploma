package ru.practicum.android.diploma.ui.adapters.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(
    private val binding: IndustryItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industry: Industry, isChecked: Boolean) = with(binding) {
        rbIndustryButton.text = industry.name
        rbIndustryButton.isChecked = isChecked
    }

}
