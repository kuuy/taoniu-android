package com.kuuy.taoniu.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.R
import com.kuuy.taoniu.databinding.ActivityMainBinding
import com.kuuy.taoniu.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
  private lateinit var navController: NavController
  private lateinit var navHostFragment: NavHostFragment

  override fun viewBinding(): ActivityMainBinding {
    return ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onBind() {
    navHostFragment = supportFragmentManager.findFragmentById(
      R.id.navHostFragment
    ) as NavHostFragment
    navController = navHostFragment.navController

    setupActionBarWithNavController(navController)

    binding.mainNav.setOnItemSelectedListener {
      when (it.itemId) {
        R.id.navHome -> {
          navController.navigate(R.id.productListingsFragment)
        }
        R.id.navStrategyHome -> {
          navController.navigate(R.id.binanceSpotPlansDailyFragment)
        }
        R.id.navOrderHome -> {
          navController.navigate(R.id.binanceSpotAnalysisTradingsFishersGridsFragment)
//          navController.navigate(R.id.tradingviewAnalysisFragment)
//          navController.navigate(R.id.orderHomeFragment)
        }
        R.id.navCounter -> {
          navController.navigate(R.id.binanceSpotTradingsSymbolsFragment)
//          navController.navigate(R.id.counterFragment)
        }
        R.id.navScanner -> {
          navController.navigate(R.id.binanceSpotMarginIsolatedTradingsGridsFragment)
//          navController.navigate(R.id.scannerFragment)
        }
        else -> {
          return@setOnItemSelectedListener false
        }
      }

      return@setOnItemSelectedListener true
    }
  }

  override fun onResume() {
    super.onResume()
    if (!authPreferences.contains("ACCESS_TOKEN")) {
      navController.navigate(R.id.loginFragment)
    }
  }
}
