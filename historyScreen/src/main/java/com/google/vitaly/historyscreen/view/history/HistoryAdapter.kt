package com.google.vitaly.historyscreen.view.history

import com.google.vitaly.model.data.userdata.Result
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.vitaly.historyscreen.R
import kotlinx.android.synthetic.main.activity_history_recyclerview_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<com.google.vitaly.model.data.userdata.Result> = arrayListOf()

    fun setData(data: List<Result>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_history_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Result) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_history_textview_recycler_item.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
