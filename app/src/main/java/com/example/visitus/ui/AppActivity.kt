package com.example.visitus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.visitus.R
import com.example.visitus.Screens
import com.example.visitus.di.DI
import com.example.visitus.model.data.storage.Prefs
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Toothpick
import com.example.visitus.presentation.AppLauncher
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.activity_main_container.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class AppActivity : MvpAppCompatActivity(){
    @Inject
    lateinit var appLauncher: AppLauncher

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        setupNavigationBar()

        if (savedInstanceState == null) {
           appLauncher.coldStart()
        }
    }

    private fun setupNavigationBar() {
         bottomNavigationView.setOnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.nagivation_search -> {
                    router.navigateTo(Screens.SearchVisitsScreen)
                    //router.navigateTo(Screens.NewAccountScreen)
                    //println("hello")
                    //.replaceScreen(Screens.SplashScreen)
                }//localCicerone.router.replaceScreen(NavigationKeys.HOME_TAB_FRAGMENT) //envia comandos para o navigator
                R.id.nagivation_world -> {
                    router.navigateTo(Screens.ProfileVisitsScreen)
                }
                R.id.nagivation_plus -> {
                    router.navigateTo(Screens.CreateVisit1Screen)
                }
                R.id.nagivation_sms -> {}
                R.id.nagivation_user -> {router.navigateTo(Prefs.user?.let {
                    Screens.PersonProfileScreen(
                        it
                    )
                })}
            }
            true
        }
    }

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                // Fix incorrect order lifecycle callback of MainFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }
    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }
}