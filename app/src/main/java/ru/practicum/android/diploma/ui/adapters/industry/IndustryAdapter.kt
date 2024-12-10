package ru.practicum.android.diploma.ui.adapters.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(
    private val clickListener: IndustryClickListener
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private var lastCheckedIndustry: Industry? = null
    private var industries: List<Industry> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IndustryItemBinding.inflate(layoutInflater, parent, false)
        return IndustryViewHolder(binding)
    }

    override fun getItemCount(): Int = industries.size

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industries[position]
        holder.bind(industry, lastCheckedIndustry == industry)

        holder.itemView.setOnClickListener {
            if (lastCheckedIndustry == industry) {
                // Сбрасываем
                lastCheckedIndustry = null
                notifyDataSetChanged()
                clickListener.onIndustryClick(null)
            } else {
                // Устанавливаем
                lastCheckedIndustry = industry
                notifyDataSetChanged()
                clickListener.onIndustryClick(industry)
            }
        }
    }

    fun updateIndustries(industries: List<Industry>) {
        this.industries = industries
        notifyDataSetChanged()
    }

    fun setCheckedIndustry(industry: Industry?) {
        lastCheckedIndustry = industry
        notifyDataSetChanged()
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(industry: Industry?)
    }
}
