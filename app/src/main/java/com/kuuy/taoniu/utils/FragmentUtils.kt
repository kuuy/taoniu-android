package com.kuuy.taoniu.utils

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Fragment.delayToast(
  message: String,
  duration: Int = Toast.LENGTH_SHORT
) {
  withContext(Dispatchers.Main) {
    showToast(message, duration)
  }
}


fun Fragment.showToast(
  message: String,
  duration: Int = Toast.LENGTH_SHORT
) {
  Toast.makeText(getActivity(), message, duration).show()
}

fun Fragment.showOptionsDialog(
  options: Array<String>,
  title: String? = null,
  action: (Int) -> Unit
) {
  val builder = AlertDialog.Builder(getActivity())
  title?.let {
    builder.setTitle(it)
  }
  builder.setItems(options) { _,option ->
    action(option)
  }
  val dialog = builder.create()

  dialog?.run {
    show()
  }
}
