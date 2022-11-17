package com.kuuy.taoniu.ui.groceries.fragments

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.fragment.findNavController

import com.kuuy.taoniu.R
import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.DbResource

import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.widget.SwipeToDelete
import com.kuuy.taoniu.ui.groceries.adapters.CounterOrderListAdapter
import com.kuuy.taoniu.ui.groceries.dialogs.CounterArchiveDialog
import com.kuuy.taoniu.ui.groceries.dialogs.CounterOrderDialog
import com.kuuy.taoniu.databinding.FragmentGroceriesCounterBinding

@AndroidEntryPoint
class CounterFragment
    : BaseFragment<FragmentGroceriesCounterBinding>() {

  private val viewModel by viewModels<CounterViewModel>()
  private val adapter by lazy {
    CounterOrderListAdapter( { model ->
      val dialog = CounterOrderDialog()
      dialog.show(getParentFragmentManager(), "counter_order")
    }, { model ->
      viewModel.removeOrder(model.id)      
    })
  }

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesCounterBinding {
    return FragmentGroceriesCounterBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    viewModel.getOrderListings()

    initRecycler()
    initViewModel()

    binding.btnAdd.setOnClickListener {
      val action = CounterFragmentDirections
          .toProductListingsFragment()
      findNavController().navigate(action)
    }
    binding.btnClear.setOnClickListener {
      viewModel.clear()
    }
    binding.btnArchive.setOnClickListener {
      val dialog = CounterArchiveDialog()
      dialog.show(getParentFragmentManager(), "counter_archive")
    }
  }

  private fun initRecycler() {
    binding.rvOrderList.apply {
      adapter = this@CounterFragment.adapter
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
    }
    swipeToDelete(binding.rvOrderList)
  }

  private fun initViewModel() {
    viewModel.orderListings.observe(
      viewLifecycleOwner
    ) { result->
      when (result) {
        is DbResource.Loading -> {
          showLoading(true)
          binding.rvOrderList.visibility = View.GONE
        }
        is DbResource.Success -> {
          showLoading(false)
          result.data?.let {
            adapter.setData(it.orders)
            viewModel.settlement(it.orders)
            settlement()
          }
          binding.rvOrderList.visibility = View.VISIBLE
        }
        is DbResource.Error -> {
          showLoading(false)
          binding.rvOrderList.visibility = View.GONE
        }
      }
    }

    viewModel.counterClear.observe(
      viewLifecycleOwner
    ) { result->
      when (result) {
        is DbResource.Loading -> {
          showLoading(true)
        }
        is DbResource.Success -> {
          showLoading(false)
          viewModel.getOrderListings()
        }
        is DbResource.Error -> {
          showLoading(false)
          showToast(result.message ?: "db failed")
        }
      }
    }
  }

  private fun settlement() {
    binding.tvSubTotalAmount.text = "%,.2f".format(
      viewModel.subTotalAmount
    )
    binding.tvSettlementAmount.text = "%,.2f".format(
      viewModel.settlementAmount
    )
    binding.btnSubmit.text = "收款 %,.2f 元".format(
      viewModel.settlementAmount
    )
  }

  private fun swipeToDelete(recyclerView: RecyclerView) {
    val swipeCallBack = object : SwipeToDelete() {
      override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
      ) {
        adapter.remove(viewHolder.absoluteAdapterPosition)
      }
    }

    val itemTouchHelper = ItemTouchHelper(swipeCallBack)
    itemTouchHelper.attachToRecyclerView(recyclerView)
  }

  private fun showLoading(isLoading: Boolean) {
  }

}
