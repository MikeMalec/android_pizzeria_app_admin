package com.example.pizzeria_admin_app.ui.products.pasta

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
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pasta
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PastaUpdateCreateFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

class PastaUpdateCreateFragment : BaseFragment(R.layout.pasta_update_create_fragment) {

    private lateinit var binding: PastaUpdateCreateFragmentBinding

    private val args: PastaUpdateCreateFragmentArgs by navArgs()

    val pasta: Pasta?
        get() = args.pasta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (pasta != null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeletePastaDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PastaUpdateCreateFragmentBinding.bind(view)
        pastaViewModel.setPastaChannels()
        if (pasta !== null) {
            pastaViewModel.currentPastaIngredients.value = pasta!!.ingredients
        } else {
            pastaViewModel.currentPastaIngredients.value = listOf()
        }
        setViews()
        binding.btnSavePasta.setOnClickListener { savePasta() }
        binding.ivAddAddon.setOnClickListener {
            PastaUpdateCreateFragmentDirections.actionPastaUpdateCreateFragmentToPizzaIngredientDialog(
                "Pasta"
            ).also {
                findNavController().navigate(it)
            }
        }
        observeIngredients()
        observePastaRequestResponse()
        observePastaDeletionResponse()
    }

    private fun setViews() {
        if (pastaViewModel.currentPastaName !== null) {
            binding.apply {
                etPastaName.setText(pastaViewModel.currentPastaName!!)
                etPastaNumber.setText(pastaViewModel.currentPastaNumber!!)
                etSmallPrice.setText(pastaViewModel.currentPastaSmallPrice!!)
                etBigPrice.setText(pastaViewModel.currentPastaBigPrice!!)
            }
        } else {
            pasta?.also { pasta ->
                binding.apply {
                    etPastaName.setText(pasta.name)
                    etPastaNumber.setText(pasta.number.toString())
                    etSmallPrice.setText(pasta.smallPrice.toString())
                    etBigPrice.setText(pasta.bigPrice.toString())
                }
            }
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launchWhenStarted {
            pastaViewModel.currentPastaIngredients.observe(viewLifecycleOwner, Observer {
                binding.cgPastaIngredients.removeAllViews()
                it.forEach { name ->
                    val chip = Chip(requireContext())
                    chip.text = name
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.cgPastaIngredients.removeView(chip)
                        burgerViewModel.removeBurgerIngredient(name)
                    }
                    binding.cgPastaIngredients.addView(chip)
                }
            })
        }
    }

    private fun observePastaRequestResponse() {
        lifecycleScope.launchWhenStarted {
            pastaViewModel.pastaRequestResponse.consumeAsFlow().collect {
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

    private fun observePastaDeletionResponse() {
        lifecycleScope.launchWhenStarted {
            pastaViewModel.pastaDeletionRequestResponse.consumeAsFlow().collect {
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

    private fun showDeletePastaDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć makaron?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deletePasta()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun deletePasta() {
        pastaViewModel.deletePasta(pasta!!.id!!)
    }

    private fun savePasta() {
        if (pastaName.isNotEmpty() && pastaNumber.isNotEmpty() && smallPrice.isNotEmpty() && bigPrice.isNotEmpty() && pastaViewModel.currentPastaIngredients.value!!.isNotEmpty()) {
            setViewModelFields()
            if (pasta !== null) {
                pastaViewModel.updatePasta(pasta!!.id!!)
            } else {
                pastaViewModel.createPasta()
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private val pastaName: String
        get() = binding.etPastaName.text.toString()
    private val pastaNumber: String
        get() = binding.etPastaNumber.text.toString()
    private val smallPrice: String
        get() = binding.etSmallPrice.text.toString()
    private val bigPrice: String
        get() = binding.etBigPrice.text.toString()

    private fun setViewModelFields() {
        pastaViewModel.currentPastaName = pastaName
        pastaViewModel.currentPastaNumber = pastaNumber
        pastaViewModel.currentPastaSmallPrice = smallPrice
        pastaViewModel.currentPastaBigPrice = bigPrice
    }

    override fun onPause() {
        super.onPause()
        setViewModelFields()
    }
}