package com.kuuy.taoniu.utils

import android.app.AlertDialog
import android.util.TypedValue
import android.widget.Toast
import androidx.fragment.app.Fragment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest

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
  Toast.makeText(activity, message, duration).show()
}

fun Fragment.showOptionsDialog(
  options: Array<String>,
  title: String? = null,
  action: (Int) -> Unit
) {
  val builder = AlertDialog.Builder(activity)
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

fun Fragment.sp2px(sp: Float): Float {
  return TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp,
    resources.displayMetrics,
  )
}

fun Fragment.md5(input:String): String {
  val md = MessageDigest.getInstance("MD5")
  return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}
