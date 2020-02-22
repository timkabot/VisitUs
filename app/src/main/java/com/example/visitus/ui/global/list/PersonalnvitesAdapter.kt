package com.example.visitus.ui.global.list

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visitus.R
import com.example.visitus.Screens
import com.example.visitus.di.DI
import com.example.visitus.entity.Invite
import com.example.visitus.utils.downloadImage
import com.example.visitus.utils.inflate
import ru.terrakok.cicerone.Router
import timber.log.Timber
import toothpick.Toothpick
import javax.inject.Inject

class PersonalnvitesAdapter (private val invites: ArrayList<Invite>) :
    RecyclerView.Adapter<PersonalnvitesAdapter.VisitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitsHolder {
        val inflatedView = parent.inflate(R.layout.card_personal_invite, false)
        return VisitsHolder(inflatedView)
    }

    override fun getItemCount() = invites.size

    override fun onBindViewHolder(holder: VisitsHolder, position: Int) {
        //TODO finish with real data
        holder.location.text = invites[position].location
        holder.price.text = invites[position].price
        holder.inviteType.text = invites[position].category
        holder.title.text = invites[position].title
        holder.description.text = invites[position].description
        // println(visits[position].image)
        invites[position].image.let {
            holder.visitBackground.downloadImage(it)
        }
        holder.currentVisit = invites[position]
    }

    inner class VisitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var currentVisit = Invite()

        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val location: TextView = itemView.findViewById(R.id.locationTextView)
        val price: TextView = itemView.findViewById(R.id.priceTextView)
        val inviteType: TextView = itemView.findViewById(R.id.inviteTypeTextView)
        val visitBackground: ImageView = itemView.findViewById(R.id.visitBackground)
        init {
            itemView.setOnClickListener(this)
            Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, "PersonalVisitsAdapter"))

        }
        @Inject
        lateinit var router: Router
        override fun onClick(p0: View?) {
            router.navigateTo(Screens.ShowInviteScreen(currentVisit))

            Timber.d("Clicked to personal visit, ${currentVisit}")
            Log.d("RecyclerView", "CLICK!")
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Toothpick.closeScope("VisitsAdapter")

    }
}

