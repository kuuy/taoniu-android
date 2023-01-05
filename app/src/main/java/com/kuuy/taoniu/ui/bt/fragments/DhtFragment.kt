package com.kuuy.taoniu.ui.bt.fragments

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kuuy.taoniu.databinding.FragmentBtDhtBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.libtorrent4j.AlertListener
import org.libtorrent4j.SessionManager
import org.libtorrent4j.alerts.Alert
import org.libtorrent4j.alerts.AlertType
import timber.log.Timber

@AndroidEntryPoint
class DhtFragment
  : BaseFragment<FragmentBtDhtBinding>() {
  private val viewModel by viewModels<DhtViewModel>()
  override fun viewBinding(container: ViewGroup?): FragmentBtDhtBinding {
    return FragmentBtDhtBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    var session = SessionManager()
    session.addListener(object : AlertListener {
      override fun types(): IntArray? = null

      override fun alert(alert: Alert<*>) {
        when (alert.type()) {
          AlertType.DHT_BOOTSTRAP -> {
            Timber.tag(TAG).d(alert.message())
          }
          AlertType.DHT_STATS -> {
            Timber.tag(TAG).d(alert.message())
          }
          AlertType.LISTEN_SUCCEEDED -> {
            Timber.tag(TAG).d(alert.message())
          }
          AlertType.LISTEN_FAILED -> {
            Timber.tag(TAG).d(alert.message())
          }
          AlertType.DHT_PUT -> {
            Timber.tag(TAG).d(alert.message())
          }
          else -> {}
        }
      }
    })
    session.start()

  }

  companion object {
    private val TAG = "DHT"
  }
}