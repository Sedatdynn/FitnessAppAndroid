package com.example.fitnessapp.feature.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.feature.home.profile.model.ProfileItemModel

class ProfileAdapter(
    private val context: Context,
    private val items: List<ProfileItemModel>, var onItemClick: ((Int) -> Unit)? = null
) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val currentItem = items[position]
        holder.iconImageView.setImageResource(currentItem.iconResId)
        holder.titleTextView.text = currentItem.title
        holder.subtitleTextView.text = currentItem.subtitle
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem.id)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.profile_iv)
        val titleTextView: TextView = itemView.findViewById(R.id.profile_tv_title)
        val subtitleTextView: TextView = itemView.findViewById(R.id.profile_tv_description)

    }
}
