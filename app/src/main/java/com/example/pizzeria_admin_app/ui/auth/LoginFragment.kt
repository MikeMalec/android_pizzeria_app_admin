package com.example.pizzeria_admin_app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.LoginFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.utils.extensions.gone
import com.example.pizzeria_admin_app.utils.extensions.show
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    private lateinit var binding: LoginFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        observeLoginRequest()
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun observeLoginRequest() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.loginRequestResponse.consumeAsFlow().collect {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Error -> {
                        hideLoading()
                        it.error?.also {
                            mainActivity.shortMessage("Niepoprawne dane")
                        }
                    }
                    is Resource.Success -> {
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbAuth.show()
    }

    private fun hideLoading() {
        binding.pbAuth.gone()
    }

    private val login: String
        get() = binding.etLogin.text.toString()

    private val password: String
        get() = binding.etPassword.text.toString()

    private fun login() {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            mainViewModel.login(login, password)
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }
}