package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.analysis.margin.isolated.tradings.fishers

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.kuuy.taoniu.R
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GridsFragment: BaseFragment<FragmentCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding>() {
  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding {
    return FragmentCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initBarChart()
  }

  private fun initBarChart() {
    binding.barChart.extraBottomOffset = 15f
    binding.barChart.description.isEnabled = false
    binding.barChart.legend.isEnabled = false
    binding.barChart.axisRight.isEnabled = false

    binding.barChart.setMaxVisibleValueCount(7)
    binding.barChart.setDrawBarShadow(false)
    binding.barChart.setDrawValueAboveBar(false)
    binding.barChart.setPinchZoom(false)
    binding.barChart.setDrawGridBackground(false)

    binding.barChart.axisLeft.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/OpenSans-Light.ttf")
    binding.barChart.axisLeft.textSize = 13f

    binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    binding.barChart.xAxis.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/OpenSans-Regular.ttf")
    binding.barChart.xAxis.textSize = 16f
    binding.barChart.xAxis.labelCount = 5
    binding.barChart.xAxis.granularity = 1f

    binding.barChart.xAxis.setDrawGridLines(false)
    binding.barChart.xAxis.setDrawAxisLine(false)
    binding.barChart.xAxis.setCenterAxisLabels(true)

    val values = ArrayList<BarEntry>()
    values.add(BarEntry(1f, floatArrayOf(-10f, 10f)))
    values.add(BarEntry(2f, floatArrayOf(-12f, 13f)))
    values.add(BarEntry(3f, floatArrayOf(-15f, 15f)))
    values.add(BarEntry(4f, floatArrayOf(-17f, 17f)))
    values.add(BarEntry(5f, floatArrayOf(-19f, 20f)))
    values.add(BarEntry(6f, floatArrayOf(-15f, 50f)))
    values.add(BarEntry(7f, floatArrayOf(-17f, 5f)))
    values.add(BarEntry(8f, floatArrayOf(-11f, 11f)))
    values.add(BarEntry(9f, floatArrayOf(-12f, 12f)))
    values.add(BarEntry(10f, floatArrayOf(-13f, 18f)))

    val dates = listOf("02/26", "02/25", "02/24", "02/23", "02/22", "02/21", "02/20", "02/19", "02/18", "02/17")

    val set = BarDataSet(values, "").apply {
      valueTextSize = 16f
      setDrawIcons(false)
      setDrawValues(false)
      binding.barChart.context.let{
        setColors(
          binding.barChart.context.getColor(R.color.material_green300),
          binding.barChart.context.getColor(R.color.material_red300),
        )
      }
    }

    binding.barChart.xAxis.valueFormatter = object : ValueFormatter() {
      override fun getFormattedValue(value: Float): String {
        return "99.999"
      }
      override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val index = value.toInt()
        if (index in 0..dates.lastIndex) {
          return dates[value.toInt()]
        }
        return ""
      }
    }

    binding.barChart.data = BarData(set).apply {
      barWidth = 0.9f
    }
//    binding.barChart.setFitBars(true)
    binding.barChart.invalidate()
  }
}