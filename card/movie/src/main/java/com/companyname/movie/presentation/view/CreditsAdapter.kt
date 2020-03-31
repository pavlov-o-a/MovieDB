package com.companyname.movie.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.companyname.common.Constants
import com.companyname.movie.R
import kotlinx.android.synthetic.main.credit_holder.view.*

class CreditsAdapter(val data: List<CreditData>): RecyclerView.Adapter<CreditsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsHolder {
        return CreditsHolder(LayoutInflater.from(parent.context).inflate(R.layout.credit_holder, parent,false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CreditsHolder, position: Int) {
        holder.bind(data[position])
    }
}

class CreditsHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(data: CreditData){
        itemView.creditName.text = data.name
        itemView.creditRole.text = data.role
        if (data.imgPath.isNotEmpty()) {
            val coverWidth =
                itemView.context.resources.getDimensionPixelSize(R.dimen.credit_holder_width)
            val posterSize = Constants.POSTER_SIZES.first { it >= coverWidth }
            val imageUrl = Constants.IMAGES_URL +
                    "w$posterSize/" +
                    data.imgPath
            Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(itemView.creditCover)
        }
    }
}

data class CreditData(val name: String, val role: String, val imgPath: String)