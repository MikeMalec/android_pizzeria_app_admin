package com.example.pizzeria_admin_app.ui.time

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.databinding.OrderTimeFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderTimeFragment : BaseFragment(R.layout.order_time_fragment) {
    private lateinit var binding: OrderTimeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OrderTimeFragmentBinding.bind(view)
        mainViewModel.fetchTime()
        observeTime()
        binding.btnSetTime.setOnClickListener {
            val time = binding.etTime.text.toString()
            if(time.isNotEmpty()) {
                mainViewModel.updateTime(time.toInt())
            }
        }
    }

    private fun observeTime() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.time.observe(viewLifecycleOwner, Observer {
                binding.etTime.setText(it.time.toString())
            })
        }
    }
}