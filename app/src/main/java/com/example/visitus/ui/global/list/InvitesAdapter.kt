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
import toothpick.Toothpick
import javax.inject.Inject


class InvitesAdapter (private val invites: ArrayList<Invite>) :
    RecyclerView.Adapter<InvitesAdapter.VisitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitsHolder {
        val inflatedView = parent.inflate(R.layout.card_invite, false)
        return VisitsHolder(inflatedView)
    }

    override fun getItemCount() = invites.size

    override fun onBindViewHolder(holder: VisitsHolder, position: Int) {
        //TODO finish with real data
        holder.location.text = invites[position].location
        holder.price.text = invites[position].price
        holder.inviteType.text = invites[position].category
        invites[position].creatorAvatarLink?.let {
            holder.creatorAvatar.downloadImage(it)
        }
        println(invites[position].image)
        invites[position].image?.let {
            holder.visitBackground.downloadImage(it)
        }
        holder.currentVisit = invites[position]
        holder.userLogin.text = invites[position].user.login

        holder.title.text = invites[position].title
        holder.description.text = invites[position].description

    }

    inner class VisitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var currentVisit = Invite()
        val location = itemView.findViewById<TextView>(R.id.locationTextView)
        val price = itemView.findViewById<TextView>(R.id.priceTxetView)
        val inviteType = itemView.findViewById<TextView>(R.id.inviteTypeTextView)
        val creatorAvatar = itemView.findViewById<ImageView>(R.id.profile_image)
        val visitBackground = itemView.findViewById<ImageView>(R.id.visitBackground)
        val userLogin = itemView.findViewById<TextView>(R.id.userLogin)
        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)

        init {
            itemView.setOnClickListener(this)
            Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, "VisitsAdapter"))
        }
        @Inject
        lateinit var router: Router
        override fun onClick(p0: View?) {
            router.navigateTo(Screens.ShowInviteScreen(currentVisit))
            Log.d("Click on some visit", "CLICK!")
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Toothpick.closeScope("VisitsAdapter")

    }
}

