package com.example.pizzeria_admin_app.ui.products.burger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Burger
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.BurgerUpdateCreateFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class BurgerUpdateCreateFragment : BaseFragment(R.layout.burger_update_create_fragment) {
    private lateinit var binding: BurgerUpdateCreateFragmentBinding

    private val args: BurgerUpdateCreateFragmentArgs by navArgs()
    private val burger: Burger?
        get() = args.burger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (burger !== null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeleteBurgerDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BurgerUpdateCreateFragmentBinding.bind(view)
        burgerViewModel.setBurgerChannels()
        if (burger !== null) {
            burgerViewModel.currentBurgerIngredients.value = burger!!.ingredients
        } else {
            burgerViewModel.currentBurgerIngredients.value = listOf()
        }
        setViews()
        binding.btnSaveBurger.setOnClickListener { saveBurger() }
        binding.ivAddAddon.setOnClickListener {
            BurgerUpdateCreateFragmentDirections.actionBurgerUpdateCreateFragmentToPizzaIngredientDialog(
                "Burger"
            ).also {
                findNavController().navigate(it)
            }
        }
        observeIngredients()
        observeBurgerRequestResponse()
        observeBurgerDeletionResponse()
    }

    private fun setViews() {
        if (burgerViewModel.currentBurgerName !== null) {
            binding.apply {
                etBurgerName.setText(burgerViewModel.currentBurgerName!!)
                etBurgerNumber.setText(burgerViewModel.currentBurgerName!!)
                etSetPrice.setText(burgerViewModel.currentBurgerSetPrice!!)
                etSoloPrice.setText(burgerViewModel.currentBurgerSoloPrice!!)
            }
        } else {
            burger?.also { burger ->
                binding.apply {
                    etBurgerName.setText(burger.name)
                    etBurgerNumber.setText(burger.number.toString())
                    etSetPrice.setText(burger.setPrice.toString())
                    etSoloPrice.setText(burger.soloPrice.toString())
                }
            }
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launchWhenStarted {
            burgerViewModel.currentBurgerIngredients.observe(viewLifecycleOwner, Observer {
                binding.cgBurgerIngredients.removeAllViews()
                it.forEach { name ->
                    val chip = Chip(requireContext())
                    chip.text = name
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.cgBurgerIngredients.removeView(chip)
                        burgerViewModel.removeBurgerIngredient(name)
                    }
                    binding.cgBurgerIngredients.addView(chip)
                }
            })
        }
    }

    private fun observeBurgerRequestResponse() {
        lifecycleScope.launchWhenStarted {
            burgerViewModel.burgerRequestResponse.consumeAsFlow().collect {
                Log.d("XXX", "RESP = $it")
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun observeBurgerDeletionResponse() {
        lifecycleScope.launchWhenStarted {
            burgerViewModel.burgerDeletionRequestResponse.consumeAsFlow().collect {
                Log.d("XXX", "RESP = $it")
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun showDeleteBurgerDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć burgera?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deleteBurger()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun deleteBurger() {
        burgerViewModel.deleteBurger(burger!!.id!!)
    }

    private fun saveBurger() {
        if (burgerName.isNotEmpty() && burgerNumber.isNotEmpty() && burgerSetPrice.isNotEmpty() && burgerSoloPrice.isNotEmpty() && burgerViewModel.currentBurgerIngredients.value!!.isNotEmpty()) {
            setViewModelFields()
            if (burger !== null) {
                burgerViewModel.updateBurger(burger!!.id!!)
            } else {
                burgerViewModel.createBurger()
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private val burgerName: String
        get() = binding.etBurgerName.text.toString()
    private val burgerNumber: String
        get() = binding.etBurgerNumber.text.toString()
    private val burgerSoloPrice: String
        get() = binding.etSoloPrice.text.toString()
    private val burgerSetPrice: String
        get() = binding.etSetPrice.text.toString()


    private fun setViewModelFields() {
        burgerViewModel.currentBurgerName = burgerName
        burgerViewModel.currentBurgerNumber = burgerNumber
        burgerViewModel.currentBurgerSetPrice = burgerSetPrice
        burgerViewModel.currentBurgerSoloPrice = burgerSoloPrice
    }

    override fun onPause() {
        super.onPause()
        setViewModelFields()
    }
}