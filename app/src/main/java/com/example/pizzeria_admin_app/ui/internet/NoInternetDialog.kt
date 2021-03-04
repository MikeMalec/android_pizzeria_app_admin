package com.example.pizzeria_admin_app.ui.internet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.pizzeria_admin_app.databinding.NoInternetDialogBinding
import com.example.pizzeria_admin_app.utils.internet.NetworkManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class NoInternetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: NoInternetDialogBinding

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoInternetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener { dismiss() }
        lifecycleScope.launchWhenStarted {
            networkManager.isNetworkAvailable.collect {
                if (it) dismiss()
            }
        }
    }
}