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
import com.example.visitus.entity.Visit
import com.example.visitus.utils.downloadImage
import com.example.visitus.utils.inflate
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject


class VisitsAdapter (private val visits: ArrayList<Visit>) :
    RecyclerView.Adapter<VisitsAdapter.VisitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitsHolder {
        val inflatedView = parent.inflate(R.layout.card_visit, false)
        return VisitsHolder(inflatedView)
    }

    override fun getItemCount() = visits.size

    override fun onBindViewHolder(holder: VisitsHolder, position: Int) {

        holder.location.text = visits[position].invite.location
        holder.inviteType.text = visits[position].invite.category

        visits[position].invite.user.profilePhotos.avatar?.let {
            holder.creatorAvatar.downloadImage(it)
        }

        visits[position].user.profilePhotos.avatar?.let {
            holder.guestAvatar.downloadImage(it)
        }

        visits[position].invite.image?.let {
            holder.visitBackground.downloadImage(it)
        }

        holder.currentVisit = visits[position]
        holder.whomToWhom.text = visits[position].user.profileInfo.name + " к \n" + visits[position].invite.user.profileInfo.name

    }

    inner class VisitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        lateinit var currentVisit: Visit
        val location = itemView.findViewById<TextView>(R.id.location)
        val inviteType = itemView.findViewById<TextView>(R.id.inviteTypeTextView)
        val creatorAvatar = itemView.findViewById<ImageView>(R.id.fromUserAvatar)
        val guestAvatar = itemView.findViewById<ImageView>(R.id.toUserAvatar)
        val visitBackground = itemView.findViewById<ImageView>(R.id.visitBackground)
        val whomToWhom = itemView.findViewById<TextView>(R.id.whomToWhom)

        init {
            itemView.setOnClickListener(this)
            Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, "VisitsAdapter"))
        }
        @Inject
        lateinit var router: Router
        override fun onClick(p0: View?) {
            router.navigateTo(Screens.VisitPageScreen(currentVisit))
            Log.d("Click on some visit", "CLICK!")
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Toothpick.closeScope("VisitsAdapter")

    }
}

