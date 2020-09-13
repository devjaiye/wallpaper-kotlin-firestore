package com.dev_app.firestorewallify

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class WallAdapter (private var items: List<WallItem>, private val context: Context):
    RecyclerView.Adapter<WallAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: WallAdapter.ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.image).into(holder.titleImage)
        holder.titleTextView.text = item.title

        holder.titleImage.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("ref", item.ref)
            context.startActivity(intent)
        }
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val titleImage: ImageView = view.findViewById(R.id.titleImageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    }

}