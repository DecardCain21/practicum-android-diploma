package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.root_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isVisible = when (destination.id) {
                R.id.filters_fragment,
                R.id.vacancy_fragment,
                R.id.industry_fragment,
                R.id.location_fragment,
                R.id.country_fragment,
                R.id.region_fragment -> false

                else -> true
            }
            binding.bottomNavigationView.isVisible = isVisible
        }
        // Пример использования access token для HeadHunter API
        // networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }
    /* раскоментить при необходимости
    private fun networkRequestExample(accessToken: String) {
        Log.e(RootActivity::class.simpleName, accessToken)
    }
     */
}
