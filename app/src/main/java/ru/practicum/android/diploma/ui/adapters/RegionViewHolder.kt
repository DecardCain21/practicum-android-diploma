package ru.practicum.android.diploma.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.RegionItemBinding
import ru.practicum.android.diploma.domain.models.Region

class RegionViewHolder(
    private val binding: RegionItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(region: Region) {
        binding.tvRegion.text = region.name
    }
}
