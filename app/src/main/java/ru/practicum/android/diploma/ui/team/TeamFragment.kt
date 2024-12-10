package ru.practicum.android.diploma.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.util.BindingFragment

class TeamFragment : BindingFragment<FragmentTeamBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTeamBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contributors = resources.getStringArray(R.array.contributors).toList().shuffled()

        listOf(
            binding.tvContributor0,
            binding.tvContributor1,
            binding.tvContributor2,
            binding.tvContributor3,
            binding.tvContributor4
        ).forEachIndexed { index, textView ->
            textView.text = contributors.getOrNull(index) ?: ""
        }
    }
}
