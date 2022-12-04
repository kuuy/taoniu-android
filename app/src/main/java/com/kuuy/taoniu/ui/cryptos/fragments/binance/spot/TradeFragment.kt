package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotTradeBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeFragment : BaseFragment<FragmentCryptosBinanceSpotTradeBinding>() {

  private val args by navArgs<TradeFragmentArgs>()
  private val viewModels by viewModels<TradeViewModel>()
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotTradeBinding {
    return FragmentCryptosBinanceSpotTradeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
    }
  }

  private fun initViewModel() {
    viewModels.symbol = args.symbol
  }

  private fun showLoading(isLoading: Boolean) {
  }
}