package com.example.visitus.ui.global.list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visitus.R
import com.example.visitus.entity.InviteCategory
import com.example.visitus.ui.invite.createInvite.CreateVisitFragment1
import com.example.visitus.utils.downloadImage
import com.example.visitus.utils.inflate


class InviteTypesAdapter (private val inviteTypes: ArrayList<InviteCategory>, val fragment1: CreateVisitFragment1) :
    RecyclerView.Adapter<InviteTypesAdapter.VisitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitsHolder {
        val inflatedView = parent.inflate(R.layout.card_invite_category, false)
        return VisitsHolder(inflatedView)
    }

    override fun getItemCount() = inviteTypes.size

    override fun onBindViewHolder(holder: VisitsHolder, position: Int) {
        //TODO finish with real data
        holder.title.text = inviteTypes[position].name
        holder.image.downloadImage(inviteTypes[position].photo)
    }

    inner class VisitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val image: ImageView = itemView.findViewById(R.id.visitTypeImage)
        val title: TextView = itemView.findViewById(R.id.visitTypeTitle)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            fragment1.visit.category = title.text.toString()
            fragment1.onNextClicked()
        }
    }
}

