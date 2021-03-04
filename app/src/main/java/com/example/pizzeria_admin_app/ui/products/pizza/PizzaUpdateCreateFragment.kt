package com.example.pizzeria_admin_app.ui.products.pizza

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
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pizza
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PizzaUpdateCreateFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class PizzaUpdateCreateFragment : BaseFragment(R.layout.pizza_update_create_fragment) {
    private lateinit var binding: PizzaUpdateCreateFragmentBinding

    private val args: PizzaUpdateCreateFragmentArgs by navArgs()

    private val pizza: Pizza?
        get() = args.pizza

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (pizza !== null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeletePizzaDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PizzaUpdateCreateFragmentBinding.bind(view)
        pizzaViewModel.setPizzaChannels()
        if (pizza !== null) {
            pizzaViewModel.currentPizzaIngredients.value = pizza!!.ingredients
        } else {
            pizzaViewModel.currentPizzaIngredients.value = listOf()
        }
        setViews()
        binding.btnSavePizza.setOnClickListener { savePizza() }
        binding.ivAddAddon.setOnClickListener {
            PizzaUpdateCreateFragmentDirections.actionPizzaUpdateCreateFragmentToPizzaIngredientDialog(
                "Pizza"
            )
                .also { findNavController().navigate(it) }
        }
        observeIngredients()
        observePizzaRequestResponse()
        observePizzaDeletionResponse()
    }

    private fun setViews() {
        if (pizzaViewModel.currentPizzaName != null) {
            binding.apply {
                etPizzaName.setText(pizzaViewModel.currentPizzaName)
                etPizzaNumber.setText(pizzaViewModel.currentPizzaNumber)
                et24Price.setText(pizzaViewModel.currentPizza24Price)
                et30Price.setText(pizzaViewModel.currentPizza30Price)
                et40Price.setText(pizzaViewModel.currentPizza40Price)
                et50price.setText(pizzaViewModel.currentPizza50Price)
            }
        } else {
            pizza?.also { pizza ->
                binding.apply {
                    etPizzaName.setText(pizza.name)
                    etPizzaNumber.setText(pizza.number.toString())
                    et24Price.setText(pizza.size24Price.toString())
                    et30Price.setText(pizza.size24Price.toString())
                    et40Price.setText(pizza.size24Price.toString())
                    et50price.setText(pizza.size24Price.toString())
                }
            }
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launchWhenStarted {
            pizzaViewModel.currentPizzaIngredients.observe(viewLifecycleOwner, Observer {
                binding.cgPizzaIngredients.removeAllViews()
                it.forEach { name ->
                    val chip = Chip(requireContext())
                    chip.text = name
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.cgPizzaIngredients.removeView(chip)
                        pizzaViewModel.removePizzaIngredient(name)
                    }
                    binding.cgPizzaIngredients.addView(chip)
                }
            })
        }
    }

    private fun observePizzaRequestResponse() {
        lifecycleScope.launchWhenStarted {
            pizzaViewModel.pizzaRequestResponse.consumeAsFlow().collect {
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

    private fun observePizzaDeletionResponse() {
        lifecycleScope.launchWhenStarted {
            pizzaViewModel.pizzaDeletionRequestResponse.consumeAsFlow().collect {
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


    override fun onPause() {
        super.onPause()
        setViewModelFields()
    }

    private val pizzaName: String
        get() = binding.etPizzaName.text.toString()
    private val pizzaNumber: String
        get() = binding.etPizzaNumber.text.toString()
    private val pizza24Price: String
        get() = binding.et24Price.text.toString()
    private val pizza30Price: String
        get() = binding.et30Price.text.toString()
    private val pizza40Price: String
        get() = binding.et40Price.text.toString()
    private val pizza50Price: String
        get() = binding.et50price.text.toString()

    private fun savePizza() {
        if (pizzaName.isNotEmpty() && pizzaNumber.isNotEmpty() && pizza24Price.isNotEmpty() && pizza30Price.isNotEmpty() && pizza40Price.isNotEmpty() && pizza50Price.isNotEmpty() && pizzaViewModel.currentPizzaIngredients.value!!.isNotEmpty()) {
            setViewModelFields()
            if (pizza !== null) {
                pizzaViewModel.updatePizza(pizza!!.id!!)
            } else {
                pizzaViewModel.createPizza()
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private fun showDeletePizzaDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć pizze?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deletePizza()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun deletePizza() {
        pizzaViewModel.deletePizza(pizza!!.id!!)
    }

    private fun setViewModelFields() {
        pizzaViewModel.currentPizzaName = pizzaName
        pizzaViewModel.currentPizzaNumber = pizzaNumber
        pizzaViewModel.currentPizza24Price = pizza24Price
        pizzaViewModel.currentPizza30Price = pizza30Price
        pizzaViewModel.currentPizza40Price = pizza40Price
        pizzaViewModel.currentPizza50Price = pizza50Price
    }
}