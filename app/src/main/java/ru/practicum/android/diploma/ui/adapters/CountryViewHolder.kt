package ru.practicum.android.diploma.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding
import ru.practicum.android.diploma.domain.models.Country

class CountryViewHolder(
    private val binding: AreaItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.tvCountry.text = country.name
    }
}
