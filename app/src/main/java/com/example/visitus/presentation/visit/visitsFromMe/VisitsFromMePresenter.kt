package com.example.visitus.presentation.visit.visitsToMe

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.model.interactors.VisitsInteractor
import com.example.visitus.presentation.visit.IVisitsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class VisitsFromMePresenter @Inject constructor(private val visitsInteractor: VisitsInteractor) :
    MvpPresenter<IVisitsListView>() {
    val disposables = CompositeDisposable()

    fun getVisitsFromMe() {
        visitsInteractor.getVisitsFromMe().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.updateVisits(it)
                },
                {
                    Timber.d("Error getting visits from me $it")
                }
            )
            .addTo(disposables)
    }
}