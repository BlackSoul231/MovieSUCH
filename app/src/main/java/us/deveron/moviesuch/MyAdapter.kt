package us.deveron.moviesuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(var data: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = "${position + 1}"
        holder.icon.setImageResource(data[position].idIcon)
        holder.textName.text = data[position].name
        holder.textDesc.text = data[position].desc
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            payloads.find {
                it is String && it == "icon"
            }
                .let {
                    holder.icon.setImageResource(data[position].idIcon)
                }
            payloads.find {
                it is String && it == "desc"
            }
                .let {
                    holder.textDesc.text = data[position].desc
                }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}