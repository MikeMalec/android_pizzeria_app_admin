package com.example.pizzeria_admin_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.order.service.OrderService
import com.example.pizzeria_admin_app.databinding.ActivityMainBinding
import com.example.pizzeria_admin_app.ui.common.MainViewModel
import com.example.pizzeria_admin_app.ui.products.beverage.BeverageViewModel
import com.example.pizzeria_admin_app.ui.products.burger.BurgerViewModel
import com.example.pizzeria_admin_app.ui.products.pasta.PastaViewModel
import com.example.pizzeria_admin_app.ui.products.pita.PitaViewModel
import com.example.pizzeria_admin_app.ui.products.pizza.PizzaViewModel
import com.example.pizzeria_admin_app.ui.products.salad.SaladViewModel
import com.example.pizzeria_admin_app.utils.constants.RESTART_SOCKET
import com.example.pizzeria_admin_app.utils.constants.START_SERVICE
import com.example.pizzeria_admin_app.utils.constants.STOP_SERVICE
import com.example.pizzeria_admin_app.utils.extensions.gone
import com.example.pizzeria_admin_app.utils.extensions.show
import com.example.pizzeria_admin_app.utils.extensions.showSnackbar
import com.example.pizzeria_admin_app.utils.internet.NetworkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    val mainViewModel: MainViewModel by viewModels()
    val pizzaViewModel: PizzaViewModel by viewModels()
    val burgerViewModel: BurgerViewModel by viewModels()
    val pitaViewModel: PitaViewModel by viewModels()
    val pastaViewModel: PastaViewModel by viewModels()
    val saladViewModel: SaladViewModel by viewModels()
    val beverageViewModel: BeverageViewModel by viewModels()

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        setToolbar()
        setNavController()
    }

    override fun onResume() {
        super.onResume()
        observeToken()
        observeInternetAccess()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            networkManager.checkNetwork()
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setNavController() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }

    private var initialSetup = true
    private fun observeToken() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.tokenFlow.collect {
                Log.d("XXX", "TOKEN + $it")
                when (it.length > 12) {
                    false -> {
                        initialSetup = true
                        stopOrderService()
                        setGraphAndNavigation(false)
                    }
                    true -> {
                        lifecycleScope.launchWhenStarted {
                            delay(1000)
                            startOrderService()
                        }
                        if (initialSetup) {
                            initialSetup = false
                            setGraphAndNavigation(true)
                        }
                        mainViewModel.startActionsForAuthenticated()
                    }
                }
            }
        }
    }

    private fun setGraphAndNavigation(authenticated: Boolean) {
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.navigation_graph)
        when (authenticated) {
            true -> {
                graph.startDestination = R.id.newOrdersFragment
            }
            false -> {
                graph.startDestination = R.id.loginFragment
            }
        }
        navController.setGraph(graph, intent.extras)
        setNavigation()
    }

    private fun setNavigation() {
        binding.bottomNavigation.itemIconTintList = null
        navController.addOnDestinationChangedListener(this)
        binding.bottomNavigation.setupWithNavController(navController)
        val actionBarConfig = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.newOrdersFragment,
                R.id.oldOrdersFragment,
                R.id.productsFragment,
                R.id.orderTimeFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, actionBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.loginFragment,
            R.id.orderFragment, R.id.pizzaUpdateCreateFragment, R.id.burgerUpdateCreateFragment, R.id.pitaUpdateCreateFragment, R.id.createUpdateSaladFragment, R.id.pastaUpdateCreateFragment -> binding.bottomNavigation.gone()
            else -> binding.bottomNavigation.show()
        }
    }

    private fun observeInternetAccess() {
        lifecycleScope.launchWhenStarted {
            networkManager.isNetworkAvailable.collect {
                when (it) {
                    true -> {
                        if (networkManager.didMissInternet) {
                            networkManager.didMissInternet = false
                            mainViewModel.token?.also { token ->
                                if (token.length > 12) {
                                    restartSocket()
                                }
                            }
                        }
                    }
                    false -> {
                        showNoInternetDialog()
                        networkManager.didMissInternet = true
                    }
                }
            }
        }
    }

    private fun startOrderService() {
        mainViewModel.token?.also { token ->
            if (token.length > 12) {
                Intent(this, OrderService::class.java).let { intent ->
                    intent.action = START_SERVICE
                    startService(intent)
                }
            }
        }
    }

    private fun stopOrderService() {
        Intent(this, OrderService::class.java).let { intent ->
            intent.action = STOP_SERVICE
            startService(intent)
        }
    }

    fun restartSocket() {
        Intent(this, OrderService::class.java).let { intent ->
            intent.action = RESTART_SOCKET
            startService(intent)
        }
    }

    fun shortMessage(message: String) =
        showSnackbar(binding.bottomNavigation, binding.root, message)

    private fun showNoInternetDialog() {
        navController.navigate(R.id.noInternetDialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopOrderService()
    }
}