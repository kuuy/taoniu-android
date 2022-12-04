package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot

import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor(
) : BaseViewModel() {
  var symbol: String = ""
}