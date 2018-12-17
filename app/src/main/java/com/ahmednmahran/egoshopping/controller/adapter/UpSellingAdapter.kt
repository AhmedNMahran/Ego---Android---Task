package com.ahmednmahran.egoshopping.controller.adapter

/**
 * Created by Ahmed Nabil on 12/17/18.
 */
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.model.App
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_upselling.view.*


class UpSellingAdapter(val items: List<App>?, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    val imageFilePath = "file:///android_asset/images/"
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items?.size!!
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_upselling, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = items?.get(position)
        holder.tvItemName?.text = app?.name
        holder.tvItemPrice?.text = "${app?.price} ${context.getString(R.string.riyal)}"
        val productImageId = context.resources.getIdentifier(
            app?.imageName?.toLowerCase(), "drawable", context.packageName
        )
        Picasso.Builder(context).build().load(productImageId)
            .fit()
            .error(android.R.drawable.stat_notify_error) // On error image
            .placeholder(android.R.drawable.presence_away) // Place holder image
            .into(holder.imgItem)

    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvItemName = view.tvItemName
    val tvItemPrice = view.tvItemPrice
    val imgItem = view.imgItem
}