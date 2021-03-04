package com.example.pizzeria_admin_app.ui.products.salad

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
import com.example.pizzeria_admin_app.data.remote.product.api.models.Salad
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.CreateUpdateSaladFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class CreateUpdateSaladFragment : BaseFragment(R.layout.create_update_salad_fragment) {
    private lateinit var binding: CreateUpdateSaladFragmentBinding

    private val args: CreateUpdateSaladFragmentArgs by navArgs()

    private val salad: Salad?
        get() = args.salad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (salad != null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeleteSaladDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CreateUpdateSaladFragmentBinding.bind(view)
        saladViewModel.setSaladChannels()
        if (salad !== null) {
            saladViewModel.currentSaladIngredients.value = salad!!.ingredients
        } else {
            saladViewModel.currentSaladIngredients.value = listOf()
        }
        setViews()
        binding.btnSaveSalad.setOnClickListener { saveSalad() }
        binding.ivAddAddon.setOnClickListener {
            CreateUpdateSaladFragmentDirections.actionCreateUpdateSaladFragmentToPizzaIngredientDialog(
                "Salad"
            ).also {
                findNavController().navigate(it)
            }
        }
        observeIngredients()
        observeSaladRequestResponse()
        observeSaladDeletionResponse()
    }


    private fun setViews() {
        if (saladViewModel.currentSaladName !== null) {
            binding.apply {
                etSaladName.setText(saladViewModel.currentSaladName!!)
                etSaladNumber.setText(saladViewModel.currentSaladNumber!!)
                etPrice.setText(saladViewModel.currentSaladPrice!!)
            }
        } else {
            salad?.also { salad ->
                binding.apply {
                    etSaladName.setText(salad.name)
                    etSaladNumber.setText(salad.number.toString())
                    etPrice.setText(salad.price.toString())
                }
            }
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launchWhenStarted {
            saladViewModel.currentSaladIngredients.observe(viewLifecycleOwner, Observer {
                binding.cgSaladIngredients.removeAllViews()
                it.forEach { name ->
                    val chip = Chip(requireContext())
                    chip.text = name
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.cgSaladIngredients.removeView(chip)
                        burgerViewModel.removeBurgerIngredient(name)
                    }
                    binding.cgSaladIngredients.addView(chip)
                }
            })
        }
    }

    private fun observeSaladRequestResponse() {
        lifecycleScope.launchWhenStarted {
            saladViewModel.saladRequestResponse.consumeAsFlow().collect {
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

    private fun observeSaladDeletionResponse() {
        lifecycleScope.launchWhenStarted {
            saladViewModel.saladDeletionRequestResponse.consumeAsFlow().collect {
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

    private fun saveSalad() {
        if (saladName.isNotEmpty() && saladNumber.isNotEmpty() && price.isNotEmpty() && saladViewModel.currentSaladIngredients.value!!.isNotEmpty()) {
            setViewModelFields()
            if (salad !== null) {
                saladViewModel.updateSalad(salad!!.id!!)
            } else {
                saladViewModel.createSalad()
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private fun showDeleteSaladDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć sałatke?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deleteSalad()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun deleteSalad() {
        saladViewModel.deleteSalad(salad!!.id!!)
    }

    private val saladName: String
        get() = binding.etSaladName.text.toString()
    private val saladNumber: String
        get() = binding.etSaladNumber.text.toString()
    private val price: String
        get() = binding.etPrice.text.toString()

    private fun setViewModelFields() {
        saladViewModel.currentSaladName = saladName
        saladViewModel.currentSaladNumber = saladNumber
        saladViewModel.currentSaladPrice = price
    }

    override fun onPause() {
        super.onPause()
        setViewModelFields()
    }
}