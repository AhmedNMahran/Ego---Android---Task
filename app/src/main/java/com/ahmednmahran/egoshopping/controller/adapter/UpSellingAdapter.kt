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
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick


class UpSellingAdapter(val items: List<App>?, val context: Context, val listener: OnAppSelectedListener) : RecyclerView.Adapter<ViewHolder>() {
    private var selectedItem: Int? = null
    private var selectedContainer: View? = null
    private var selectedColor = 0
    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return items?.size!!
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_upselling, parent, false))
    }

    // Binds each item in the ArrayList to a view
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

        if(selectedContainer == null){
            deselectView(holder.itemContainer)
        }else{
            selectView(selectedContainer)
        }
        holder.itemContainer.onClick {
            if (selectedContainer == null) {
                selectedContainer = it
                selectView(selectedContainer)
                listener.onItemSelected(items?.get(position))
            } else {
                if (selectedContainer == it) {
                    deselectView(selectedContainer)
                    selectedContainer = null
                    listener.onItemSelected(null)
                }
                else {
                    deselectView(selectedContainer)
                    selectedContainer = it
                    selectView(selectedContainer)
                    listener.onItemSelected(items?.get(position))
                }
            }
        }
    }

    private fun selectView(view: View?){
        view?.backgroundColor = context.resources.getColor(R.color.colorSelectedItem)
    }
    private fun deselectView(view: View?){
        view?.backgroundColor  = context.resources.getColor(android.R.color.white)

    }
}
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.tvItemName
    val tvItemPrice = view.tvItemPrice
    val imgItem = view.imgItem
    val itemContainer = view.itemContainer
}

interface OnAppSelectedListener {
    fun onItemSelected(app: App?)
}