package ru.practicum.android.diploma.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(industry: Industry) {
        binding.rbIndustryButton.text = industry.name
    }
}
