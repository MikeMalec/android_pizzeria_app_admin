package com.example.pizzeria_admin_app.ui.products.beverage

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.CreateUpdateBeverageFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.utils.extensions.gone
import com.example.pizzeria_admin_app.utils.extensions.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateUpdateBeverageFragment : BaseFragment(R.layout.create_update_beverage_fragment) {
    private lateinit var binding: CreateUpdateBeverageFragmentBinding

    private val args: CreateUpdateBeverageFragmentArgs by navArgs()

    private val beverage: Beverage?
        get() = args.beverage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (beverage != null) {
            inflater.inflate(R.menu.product_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteProduct) {
            showDeleteBeverageDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CreateUpdateBeverageFragmentBinding.bind(view)
        binding.btnSaveBeverage.setOnClickListener { attemptToSaveBeverage() }
        binding.rbBeverageType.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbCold -> {
                    hideRgAlcoType()
                    hideBigSmallPriceAlco()
                    hideOneSizeAlco()
                    showClSizeAndPrice()
                }
                R.id.rbHot -> {
                    hideRgAlcoType()
                    hideBigSmallPriceAlco()
                    hideOneSizeAlco()
                    showClSizeAndPrice()
                }
                R.id.rbAlcohol -> {
                    hideClSizeAndPrice()
                    hideBigSmallPriceAlco()
                    hideOneSizeAlco()
                    showRgAlcoType()
                }
            }
        }
        binding.rbAlcoholType.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbCanned, R.id.rbWine, R.id.rbNormal, R.id.rbBottled -> {
                    hideClSizeAndPrice()
                    hideBigSmallPriceAlco()
                    showOneSizeAlco()
                }
                R.id.rbDraught -> {
                    hideClSizeAndPrice()
                    hideOneSizeAlco()
                    showBigSmallPriceAlco()
                }
            }
        }
        setViews()
    }

    private fun setViews() {
        if (beverageViewModel.name !== null) {
            binding.apply {
                etBeverageName.setText(beverageViewModel.name)
                etBeverageNumber.setText(beverageViewModel.number)
                if (beverageViewModel.orderable) {
                    cbOrderable.isChecked = true
                }
                when (beverageViewModel.beverageType) {
                    null -> {
                    }
                    "cold" -> rbCold.isChecked = true
                    "hot" -> rbHot.isChecked = true
                    "alcohol" -> rbAlcohol.isChecked = true
                    null -> {
                    }
                }
                when (beverageViewModel.alcoholType) {
                    null -> {
                    }
                    "canned" -> rbCanned.isChecked = true
                    "draught" -> rbDraught.isChecked = true
                    "normal" -> rbNormal.isChecked = true
                    "bottled" -> rbBottled.isChecked = true
                    "wine" -> rbWine.isChecked = true
                }
                when (beverageViewModel.beverageType!!) {
                    null -> {
                    }
                    "cold", "hot" -> {
                        etBeveragePrice.setText(beverageViewModel.beveragePrice.toString())
                        etBeverageSize.setText(beverageViewModel.beverageSize.toString())
                    }
                    "alcohol" -> {
                        when (beverageViewModel.alcoholType) {
                            null -> {
                            }
                            "canned", "normal", "bottled", "wine" -> {
                                etAlcoholPrice.setText(beverageViewModel.alcoholPrice.toString())
                                etAlcoholSize.setText(beverageViewModel.alcoholSize.toString())
                            }
                            "draught" -> {
                                etSmallAlcoholPrice.setText(beverageViewModel.alcoholSmallPrice.toString())
                                etBigAlcoholPrice.setText(beverageViewModel.alcoholBigPrice.toString())
                            }
                        }
                    }
                    null -> {
                    }
                }
            }
        } else {
            if (beverage !== null) {
                binding.apply {
                    etBeverageName.setText(beverage!!.name)
                    etBeverageNumber.setText(beverage!!.number)
                    if (beverage!!.orderable) {
                        cbOrderable.isChecked = true
                    }
                    when (beverage!!.type) {
                        null -> {
                        }
                        "cold" -> rbCold.isChecked = true
                        "hot" -> rbHot.isChecked = true
                        "alcohol" -> rbAlcohol.isChecked = true
                        null -> {
                        }
                    }
                    when (beverage!!.alcoholType) {
                        null -> {
                        }
                        "canned" -> rbCanned.isChecked = true
                        "draught" -> rbDraught.isChecked = true
                        "normal" -> rbNormal.isChecked = true
                        "bottled" -> rbBottled.isChecked = true
                        "wine" -> rbWine.isChecked = true
                    }
                    when (beverage!!.type) {
                        null -> {
                        }
                        "cold", "hot" -> {
                            etBeveragePrice.setText(beverage!!.price.toString())
                            etBeverageSize.setText(beverage!!.size.toString())
                        }
                        "alcohol" -> {
                            when (beverage!!.alcoholType) {
                                null -> {
                                }
                                "canned", "normal", "bottled", "wine" -> {
                                    etAlcoholPrice.setText(beverage!!.price.toString())
                                    etAlcoholSize.setText(beverage!!.size.toString())
                                }
                                "draught" -> {
                                    etSmallAlcoholPrice.setText(beverage!!.smallPrice.toString())
                                    etBigAlcoholPrice.setText(beverage!!.bigPrice.toString())
                                }
                            }
                        }
                        null -> {
                        }
                    }
                }
            }
        }
    }

    private fun showClSizeAndPrice() {
        binding.clSizeAndPrice.show()
    }

    private fun hideClSizeAndPrice() {
        binding.clSizeAndPrice.gone()
    }

    private fun showRgAlcoType() {
        binding.tvAlcoType.show()
        binding.rbAlcoholType.show()
    }

    private fun hideRgAlcoType() {
        binding.tvAlcoType.gone()
        binding.rbAlcoholType.gone()
    }

    private fun showOneSizeAlco() {
        binding.clOneSizeAlcohol.show()
    }

    private fun hideOneSizeAlco() {
        binding.clOneSizeAlcohol.gone()
    }

    private fun showBigSmallPriceAlco() {
        binding.clBigSmallAlco.show()
    }

    private fun hideBigSmallPriceAlco() {
        binding.clBigSmallAlco.gone()
    }


    override fun onPause() {
        super.onPause()
        setFieldsToViewModel()
    }

    private fun setFieldsToViewModel() {
        beverageViewModel.name = getBeverageName()
        beverageViewModel.number = getBeverageNumber()
        beverageViewModel.beverageType = getBeverageType()
        beverageViewModel.orderable = getOrderable()
        beverageViewModel.beverageSize = getBeverageSize()
        beverageViewModel.beveragePrice = getBeveragePrice()
        beverageViewModel.alcoholType = getAlcoholType()
        beverageViewModel.alcoholSize = getAlcoholSize()
        beverageViewModel.alcoholPrice = getAlcoholPrice()
        beverageViewModel.alcoholSmallPrice = getSmallAlcoholPrice()
        beverageViewModel.alcoholBigPrice = getBigAlcoholPrice()
    }

    private fun getBeverageName(): String? {
        val name = binding.etBeverageName.text.toString()
        if (name.isEmpty()) return null
        return name
    }

    private fun getBeverageNumber(): String? {
        val number = binding.etBeverageNumber.text.toString()
        if (number.isEmpty()) return null
        return number
    }

    private fun getBeverageType(): String? {
        val beverageType = binding.rbBeverageType.checkedRadioButtonId
        if (beverageType == -1) return null
        val view = getView()?.findViewById<RadioButton>(beverageType)
        val text = view?.text.toString()
        return when (text) {
            null -> null
            "Zimny" -> "cold"
            "Gorący" -> "hot"
            "Alkohol" -> "alcohol"
            else -> null
        }
    }

    private fun getOrderable(): Boolean {
        return binding.cbOrderable.isChecked
    }

    private fun getBeverageSize(): String? {
        val beverageSize = binding.etBeverageSize.text.toString()
        if (beverageSize.isEmpty()) return null
        return beverageSize
    }

    private fun getBeveragePrice(): String? {
        val beveragePrice = binding.etBeveragePrice.text.toString()
        if (beveragePrice.isEmpty()) return null
        return beveragePrice
    }


    private fun getAlcoholType(): String? {
        val alcoholType = binding.rbAlcoholType.checkedRadioButtonId
        if (alcoholType == -1) return null
        val view = getView()?.findViewById<RadioButton>(alcoholType)
        val text = view?.text.toString()
        return when (text) {
            null -> null
            "W puszczce" -> "canned"
            "Lane" -> "draught"
            "Alkohole" -> "normal"
            "Butelkowe" -> "bottled"
            "Wina" -> "wine"
            else -> null
        }
    }

    private fun getAlcoholSize(): String? {
        val alcoholSize = binding.etAlcoholSize.text.toString()
        if (alcoholSize.isEmpty()) return null
        return alcoholSize
    }

    private fun getAlcoholPrice(): String? {
        val alcoholPrice = binding.etAlcoholPrice.text.toString()
        if (alcoholPrice.isEmpty()) return null
        return alcoholPrice
    }

    private fun getSmallAlcoholPrice(): String? {
        val smallAlcoholPrice = binding.etSmallAlcoholPrice.text.toString()
        if (smallAlcoholPrice.isEmpty()) return null
        return smallAlcoholPrice
    }

    private fun getBigAlcoholPrice(): String? {
        val bigAlcoholPrice = binding.etBigAlcoholPrice.text.toString()
        if (bigAlcoholPrice.isEmpty()) return null
        return bigAlcoholPrice
    }

    private fun attemptToSaveBeverage() {
        if (getBeverageName() !== null && getBeverageNumber() !== null && getBeverageType() != null) {
            when (getBeverageType()) {
                "hot", "cold" -> {
                    if (getBeverageSize() !== null && getBeveragePrice() !== null) {
                        saveBeverage()
                    } else {
                        mainActivity.shortMessage("Wypełnij wszystkie pola!")
                    }
                }
                "alcohol" -> {
                    if (getAlcoholType() !== null) {
                        when (getAlcoholType()) {
                            "canned","wine","normal","bottled" -> {
                                if (getAlcoholPrice() !== null && getAlcoholSize() !== null) {
                                    saveBeverage()
                                } else {
                                    mainActivity.shortMessage("Wypełnij wszystkie pola!")
                                }
                            }
                            "draught" -> {
                                if (getSmallAlcoholPrice() !== null && getBigAlcoholPrice() !== null) {
                                    setFieldsToViewModel()
                                    saveBeverage()
                                } else {
                                    mainActivity.shortMessage("Wypełnij wszystkie pola!")
                                }
                            }
                        }
                    } else {
                        mainActivity.shortMessage("Wypełnij wszystkie pola!")
                    }
                }
            }
        } else {
            mainActivity.shortMessage("Wypełnij wszystkie pola!")
        }
    }

    private fun saveBeverage() {
        setFieldsToViewModel()
        when (beverage) {
            null -> beverageViewModel.createBeverage()
            else -> beverageViewModel.updateBeverage(beverage!!.id!!)
        }
    }

    private fun showDeleteBeverageDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setTitle("Usunąć napój?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24).setPositiveButton("Tak") { _, _ ->
                deleteBeverage()
            }.setNegativeButton("Anuluj") { _, _ -> }.show()
    }

    private fun deleteBeverage() {
        beverageViewModel.deleteBeverage(beverage!!.id!!)
    }
}