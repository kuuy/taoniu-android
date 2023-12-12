package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.analysis.tradings.fishers

import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.analysis.tradings.fishers.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.analysis.margin.isolated.tradings.fishers.transform
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotAnalysisTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.analysis.tradings.fishers.GridsAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.analysis.margin.isolated.tradings.fishers.GridsAdapter as MarginIsolatedGridsAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.analysis.tradings.fishers.TabPagerAdapter
import com.kuuy.taoniu.utils.OnScrollListener
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GridsFragment: BaseFragment<FragmentCryptosBinanceSpotAnalysisTradingsFishersGridsBinding>() {
  private val viewModel by viewModels<GridsViewModel>()
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val adapter by lazy { GridsAdapter() }
  private val marginIsolatedAdapter by lazy { MarginIsolatedGridsAdapter() }
  private val pagerAdapter by lazy { TabPagerAdapter(::initRecyclerView) }
  private val tabs = arrayOf("现货", "杠杆")
  private var isLoading = false
  private var current = 1
  private val pageSize = 10

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotAnalysisTradingsFishersGridsBinding {
    return FragmentCryptosBinanceSpotAnalysisTradingsFishersGridsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initTabPager()
    initBarChart()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      mainHandler.postDelayed({
        binding.swipeRefreshLayout.isRefreshing = false
      }, 2000)
    }
  }

  private fun initTabPager() {
    binding.pager.adapter = pagerAdapter
    binding.pager.tabs(tabs, TabLayout.MODE_FIXED, viewLifecycleOwner)
  }

  private fun initViewModel() {
    viewModel.series.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val values = ArrayList<BarEntry>()
            val dates = mutableListOf<String>()
            it.forEachIndexed { index, item ->
              values.add(BarEntry(index.toFloat()+1, floatArrayOf(
                item[0].toString().toFloat(),
                -item[1].toString().toFloat(),
              )))
              dates.add(item[2].toString())
            }

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
            binding.barChart.invalidate()
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }

    viewModel.gridsPaginate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val grids = it.data.transform()
            adapter.addDatas(grids)
            current = it.current
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }

    viewModel.marginIsolatedSeries.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val values = ArrayList<BarEntry>()
            val dates = mutableListOf<String>()
            it.forEachIndexed { index, item ->
              values.add(BarEntry(index.toFloat()+1, floatArrayOf(
                item[0].toString().toFloat(),
                -item[1].toString().toFloat(),
              )))
              dates.add(item[2].toString())
            }

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
            binding.barChart.invalidate()
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }

    viewModel.marginIsolatedGridsPaginate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val grids = it.data.transform()
            marginIsolatedAdapter.addDatas(grids)
            current = it.current
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
  }

  private fun initRecyclerView(rvListings: RecyclerView, position: Int) {
    binding.barChart.clear()
    when (position) {
      0 -> {
        adapter.clear()
        viewModel.getSeries()
        viewModel.listings(1, pageSize)
      }
      1 -> {
        marginIsolatedAdapter.clear()
        viewModel.getMarginIsolatedSeries()
        viewModel.marginIsolatedListings(1, pageSize)
      }
    }

    rvListings.apply {
      layoutManager ?: run {
        layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(
          requireContext(),
          DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
          requireContext(),
          R.drawable.divider_transparent
        )?.let {
          divider.setDrawable(it)
        }
        addItemDecoration(divider)
        setHasFixedSize(true)
      }

      when (position) {
        0 -> {
          adapter = this@GridsFragment.adapter
          addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
            viewModel.listings( current+1, pageSize)
          })
        }
        1 -> {
          adapter = this@GridsFragment.marginIsolatedAdapter
          addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
            viewModel.marginIsolatedListings( current+1, pageSize)
          })
        }
      }
    }
  }

  private fun initBarChart() {
    binding.barChart.extraBottomOffset = 13f
    binding.barChart.description.isEnabled = false
    binding.barChart.legend.isEnabled = false
    binding.barChart.axisRight.isEnabled = false

    binding.barChart.setDrawBarShadow(false)
    binding.barChart.setDrawValueAboveBar(false)
    binding.barChart.setPinchZoom(false)
    binding.barChart.setDrawGridBackground(false)

    binding.barChart.axisLeft.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/OpenSans-Light.ttf")
    binding.barChart.axisLeft.textSize = 13f
    binding.barChart.axisLeft.valueFormatter = object : ValueFormatter() {
      override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return kotlin.math.abs(value.toInt()).toString()
      }
    }

    binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    binding.barChart.xAxis.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/OpenSans-Regular.ttf")
    binding.barChart.xAxis.textSize = 16f
    binding.barChart.xAxis.labelCount = 5
    binding.barChart.xAxis.granularity = 1f

    binding.barChart.xAxis.setDrawGridLines(false)
    binding.barChart.xAxis.setDrawAxisLine(false)
    binding.barChart.xAxis.setCenterAxisLabels(true)
  }

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}