package com.example.visitus.ui.visit.visitsFromMe

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Visit
import com.example.visitus.presentation.visit.IVisitsListView
import com.example.visitus.presentation.visit.visitsToMe.VisitsFromMePresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.VisitsAdapter
import kotlinx.android.synthetic.main.fragment_visits_from_me.*

class VisitsFromMeFragment : BaseFragment(), IVisitsListView {
    override val layoutRes = R.layout.fragment_visits_from_me
    private lateinit var linearLayoutManager: LinearLayoutManager
    @InjectPresenter
    lateinit var presenter: VisitsFromMePresenter

    @ProvidePresenter
    fun providePresenter() : VisitsFromMePresenter =
        scope.getInstance(VisitsFromMePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getVisitsFromMe()
    }

    override fun updateVisits(visits: ArrayList<Visit>) {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        visitsFromMeList.layoutManager = linearLayoutManager

        val adapter = VisitsAdapter(visits)
        visitsFromMeList.adapter = adapter
    }
}