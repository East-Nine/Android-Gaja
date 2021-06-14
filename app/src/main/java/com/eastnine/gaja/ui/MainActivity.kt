package com.eastnine.gaja.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.eastnine.gaja.R
import com.eastnine.gaja.databinding.ActivityMainBinding
import com.eastnine.gaja.util.base.BaseActivity
import com.eastnine.gaja.viewmodel.DataViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val dataViewModel by viewModels<DataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment_container).navigateUp() || super.onSupportNavigateUp()

    private fun setupListener() {
        binding.tabSection.addOnTabSelectedListener(
            object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        when (it.text) {
                            getString(R.string.main_tab_full) -> {
                                findNavController(R.id.nav_main).apply {
                                    popBackStack()
                                    navigate(R.id.action_global_onlineFragment)
                                }
                            }
                            getString(R.string.main_tab_favorite) -> {
                                findNavController(R.id.nav_main).apply {
                                    popBackStack()
                                    navigate(R.id.action_global_favoriteFragment)
                                }
                            }
                            else -> {}
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            }
        )
    }
}