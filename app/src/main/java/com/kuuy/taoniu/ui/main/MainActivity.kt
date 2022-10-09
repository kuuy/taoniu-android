package com.kuuy.taoniu.ui.main

import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

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

    binding.navView.setOnItemSelectedListener {
      when (it.itemId) {
        R.id.navHome -> {
          navController.navigate(R.id.productListingsFragment)
        }
        R.id.navStrategyHome -> {
          navController.navigate(R.id.strategyHomeFragment)
        }
        R.id.navOrderHome -> {
          navController.navigate(R.id.orderHomeFragment)
        }
        R.id.navCounter -> {
          navController.navigate(R.id.counterFragment)
        }
        R.id.navScanner -> {
          navController.navigate(R.id.scannerFragment)
        }
        else -> {
          return@setOnItemSelectedListener false
        }
      }

      return@setOnItemSelectedListener true
    }
  }
}
