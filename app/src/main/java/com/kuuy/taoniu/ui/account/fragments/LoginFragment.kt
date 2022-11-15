package com.kuuy.taoniu.ui.account.fragments

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.databinding.FragmentAccountLoginBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.groceries.fragments.ProductCreateFragmentDirections
import com.kuuy.taoniu.ui.home.HomeFragmentDirections
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentAccountLoginBinding>() {
  private val viewModels by viewModels<AuthViewModel>()
  override fun viewBinding(container: ViewGroup?)
      : FragmentAccountLoginBinding {
    return FragmentAccountLoginBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    binding.btnSubmit.setOnClickListener {
      viewModels.login(
        binding.email.text.toString(),
        binding.password.text.toString()
      )
    }

    viewModels.token.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let{
            authPreferences.edit().apply {
              putString("ACCESS_TOKEN", it.access)
              putString("REFRESH_TOKEN", it.refresh)
            }.commit()
            showToast(response.message ?: "login success")
            val action = LoginFragmentDirections.toHomeFragment()
            findNavController().navigate(action)
          }
        }
        is ApiResource.Error -> {
          showLoading(false)
          showToast(response.message ?: "api failed")
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
    } else {
    }
  }
}