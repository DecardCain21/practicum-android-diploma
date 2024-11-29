package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.databinding.CountryItemBinding
import ru.practicum.android.diploma.databinding.RegionItemBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region

class FilterAdapter : RecyclerView.Adapter<ViewHolder>() {

    var saveFilterListener: SaveFilterListener? = null
    var items: List<ItemFilter> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemFilter.TYPE_AREA -> CountryViewHolder(
                binding = CountryItemBinding.inflate(layoutInflater, parent, false)
            )

            ItemFilter.TYPE_REGION -> RegionViewHolder(
                binding = RegionItemBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CountryViewHolder -> {
                holder.bind(item.data as Country)
                holder.itemView.setOnClickListener { saveFilterListener?.onItemClicked(item) }
            }

            is RegionViewHolder -> {
                holder.bind(item.data as Region)
                holder.itemView.setOnClickListener { saveFilterListener?.onItemClicked(item) }
            }

            else -> throw IllegalArgumentException("Unknown ViewHolder for position $position")
        }
    }

    fun updateCountries(data: List<Country>) {
        updateItems(convertToItemFilter(data, ItemFilter.TYPE_AREA))
    }

    fun updateRegions(data: List<Region>) {
        updateItems(convertToItemFilter(data, ItemFilter.TYPE_REGION))
    }

    private fun updateItems(items: List<ItemFilter>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun convertToItemFilter(data: List<Any>, typeItemFilter: Int): List<ItemFilter> {
        return data.map { ItemFilter(data = it, type = typeItemFilter) }
    }

    interface SaveFilterListener {
        fun onItemClicked(item: ItemFilter)
    }
}
