package com.example.visitus.presentation.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.visitus.model.interactors.InvitesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class SearchVisitsPresenter @Inject constructor(private val visitsInteractor: InvitesInteractor) : MvpPresenter<ISearchVisits>(){
    var compositeDisposable = CompositeDisposable()
    fun updateRecycler(city: String){
            visitsInteractor.getInvites(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        success ->
                        viewState.updateRecyclerView(success)

                    },
                    {
                        error ->
                        Timber.d("Error when get visits by location $error")
                    }
                ).addTo(compositeDisposable)
    }

}