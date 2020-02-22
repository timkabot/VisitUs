package com.example.visitus.ui.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.visitus.R
import com.example.visitus.entity.Invite
import com.example.visitus.presentation.search.ISearchVisits
import com.example.visitus.presentation.search.SearchVisitsPresenter
import com.example.visitus.ui.global.BaseFragment
import com.example.visitus.ui.global.list.InvitesAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_search_invites.*


class SearchVisitsFragment : BaseFragment(), MaterialSearchBar.OnSearchActionListener, ISearchVisits
{
    override val layoutRes = R.layout.fragment_search_invites
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: InvitesAdapter
    lateinit var invites: ArrayList<Invite>


    @InjectPresenter
    lateinit var presenter: SearchVisitsPresenter
    @ProvidePresenter
    fun providePresenter(): SearchVisitsPresenter =
        scope.getInstance(SearchVisitsPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchBar()
        initVisitsRecycler()
    }

    private fun initSearchBar(){
        searchBar.setOnSearchActionListener(this)
        searchBar.setSpeechMode(true)
    }

    private fun initVisitsRecycler(){
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        visitsRecyclerView.layoutManager = linearLayoutManager
        invites = ArrayList()
        adapter = InvitesAdapter(invites = invites)
        visitsRecyclerView.adapter = adapter
    }

    override fun updateRecyclerView(data: List<Invite>){
        invites.clear()
        invites.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onButtonClicked(buttonCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {
        presenter.updateRecycler(text.toString())
        //startSearch(text.toString(), true, null, true);
    }
}