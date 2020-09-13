package com.dev_app.firestorewallify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_page_item.view.*


class ViewPagerAdapter (private val items: ArrayList<DetailItem>, private val context: Context):
    RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        return PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.detail_page_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val item = items[position]
        Picasso.get().load(item.url).into(imageContainer)
    }

    class PagerVH (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}