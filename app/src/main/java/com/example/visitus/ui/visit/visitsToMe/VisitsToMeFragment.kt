package com.example.visitus.ui.visit.visitsToMe

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Visit
import com.example.visitus.presentation.visit.IVisitsListView
import com.example.visitus.presentation.visit.visitsToMe.VisitsToMePresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.VisitsAdapter
import kotlinx.android.synthetic.main.fragment_visits_to_me.*

class VisitsToMeFragment : BaseFragment(), IVisitsListView {

    override val layoutRes = R.layout.fragment_visits_to_me
    private lateinit var linearLayoutManager: LinearLayoutManager
    @InjectPresenter
    lateinit var presenter: VisitsToMePresenter

    @ProvidePresenter
    fun providePresenter() : VisitsToMePresenter =
        scope.getInstance(VisitsToMePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getVisitsToMe()
    }

    override fun updateVisits(visits: ArrayList<Visit>) {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        visitsToMeList.layoutManager = linearLayoutManager

        val adapter = VisitsAdapter(visits)
        visitsToMeList.adapter = adapter
    }
}