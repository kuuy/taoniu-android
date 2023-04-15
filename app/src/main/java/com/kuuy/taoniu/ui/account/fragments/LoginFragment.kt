package com.kuuy.taoniu.ui.account.fragments

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.databinding.FragmentAccountLoginBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentAccountLoginBinding>() {
  private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

  private val viewModels by viewModels<AuthViewModel>()
  override fun viewBinding(container: ViewGroup?)
      : FragmentAccountLoginBinding {
    return FragmentAccountLoginBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      requireActivity().run {
      }
    }
  }

  override fun onBind() {
    binding.btnSubmit.setOnClickListener {
      viewModels.login(
        binding.email.text.toString(),
        md5(binding.password.text.toString())
      )
    }

    binding.btnGoogleSignIn.setOnClickListener {
      googleSignIn()
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
            authPreferences.edit()
              .putString("ACCESS_TOKEN", it.access)
              .putString("REFRESH_TOKEN", it.refresh)
              .putLong("EXPIRED_AT", System.currentTimeMillis() + 895000)
              .apply()
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

  private fun googleSignIn() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(BuildConfig.GOOGLE_AUTH_CLIENT_ID)
      .requestEmail()
      .build()
    val client = GoogleSignIn.getClient(requireActivity(), gso)
    googleSignInLauncher.launch(client.signInIntent)
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
    } else {
    }
  }
}