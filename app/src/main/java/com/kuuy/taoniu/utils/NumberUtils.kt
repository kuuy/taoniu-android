package com.kuuy.taoniu.utils

import kotlin.math.log10
import kotlin.math.pow

fun Float.format(): String {
  var units = listOf("", "K", "M", "B", "P")
  var group = (log10(this.toDouble()) / log10(1000.0)).toInt()
  return "%,.2f%s".format(this / 1000.0.pow(group), units[group])
}