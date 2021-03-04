package com.example.pizzeria_admin_app.ui.products.pita

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
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pita
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PitaUpdateCreateFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class PitaUpdateCreateFragment : BaseFragment(R.layout.pita_update_create_fragment) {
    private lateinit var binding: PitaUpdateCreateFragmentBinding

    private val args: PitaUpdateCreateFragmentArgs by navArgs()

    private val pita: Pita?
        get() = args.pita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (pita != null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeletePitaDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PitaUpdateCreateFragmentBinding.bind(view)
        pitaViewModel.setPitaChannels()
        if (pita !== null) {
            pitaViewModel.currentPitaIngredients.value = pita!!.ingredients
        } else {
            pitaViewModel.currentPitaIngredients.value = listOf()
        }
        setViews()
        binding.btnSavePita.setOnClickListener { savePita() }
        binding.ivAddAddon.setOnClickListener {
            PitaUpdateCreateFragmentDirections.actionPitaUpdateCreateFragmentToPizzaIngredientDialog(
                "Pita"
            ).also {
                findNavController().navigate(it)
            }
        }
        observeIngredients()
        observePitaRequestResponse()
        observePitaDeletionResponse()
    }

    private fun setViews() {
        if (pitaViewModel.currentPitaName !== null) {
            binding.apply {
                etPitaName.setText(pitaViewModel.currentPitaName!!)
                etPitaNumber.setText(pitaViewModel.currentPitaNumber!!)
                etSmallPrice.setText(pitaViewModel.currentPitaSmallPrice!!)
                etBigPrice.setText(pitaViewModel.currentPitaBigPrice!!)
            }
        } else {
            pita?.also { pita ->
                binding.apply {
                    etPitaName.setText(pita.name)
                    etPitaNumber.setText(pita.number.toString())
                    etSmallPrice.setText(pita.smallPrice.toString())
                    etBigPrice.setText(pita.bigPrice.toString())
                }
            }
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launchWhenStarted {
            pitaViewModel.currentPitaIngredients.observe(viewLifecycleOwner, Observer {
                binding.cgPitaIngredients.removeAllViews()
                it.forEach { name ->
                    val chip = Chip(requireContext())
                    chip.text = name
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.cgPitaIngredients.removeView(chip)
                        burgerViewModel.removeBurgerIngredient(name)
                    }
                    binding.cgPitaIngredients.addView(chip)
                }
            })
        }
    }

    private fun observePitaRequestResponse() {
        lifecycleScope.launchWhenStarted {
            pitaViewModel.pitaRequestResponse.consumeAsFlow().collect {
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

    private fun observePitaDeletionResponse() {
        lifecycleScope.launchWhenStarted {
            pitaViewModel.pitaDeletionRequestResponse.consumeAsFlow().collect {
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

    private fun showDeletePitaDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć pite?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deletePita()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun savePita() {
        if (pitaName.isNotEmpty() && pitaNumber.isNotEmpty() && smallPrice.isNotEmpty() && bigPrice.isNotEmpty() && pitaViewModel.currentPitaIngredients.value!!.isNotEmpty()) {
            setViewModelFields()
            if (pita !== null) {
                pitaViewModel.updatePita(pita!!.id!!)
            } else {
                pitaViewModel.createPita()
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private fun deletePita() {
        pitaViewModel.deletePita(pita!!.id!!)
    }

    private val pitaName: String
        get() = binding.etPitaName.text.toString()
    private val pitaNumber: String
        get() = binding.etPitaNumber.text.toString()
    private val smallPrice: String
        get() = binding.etSmallPrice.text.toString()
    private val bigPrice: String
        get() = binding.etBigPrice.text.toString()

    private fun setViewModelFields() {
        pitaViewModel.currentPitaName = pitaName
        pitaViewModel.currentPitaNumber = pitaNumber
        pitaViewModel.currentPitaSmallPrice = smallPrice
        pitaViewModel.currentPitaBigPrice = bigPrice
    }

    override fun onPause() {
        super.onPause()
        setViewModelFields()
    }
}