package com.example.visitus.ui.privacypolicy

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.di.DI
import com.example.visitus.presentation.register.IRegisterView
import com.example.visitus.presentation.register.NewAccountPresenter
import com.example.visitus.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_terms_of_use.*
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class PrivacyPolicyFragment : BaseFragment(), IRegisterView {
    override val layoutRes = R.layout.fragment_terms_of_use
    override val parentScopeName = DI.APP_SCOPE
    @Inject
    lateinit var router: Router
    @InjectPresenter
    lateinit var presenter: NewAccountPresenter
    @ProvidePresenter
    fun providePresenter(): NewAccountPresenter =
        scope.getInstance(NewAccountPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        back_button.setOnClickListener { router.exit() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.exit()
    }

}
