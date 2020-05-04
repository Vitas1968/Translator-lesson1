package geekbrains.ru.translator.view.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.translator.R
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.view.main.ItemTouchHelperViewHolder
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*
import java.util.*

class MainAdapter(private var onListItemClickListener: OnListItemClickListener, private val dataList: List<SearchResult>) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>(),ItemTouchHelperAdapter {

    private var data=dataList.toMutableList()
    fun setData(data: List<SearchResult>) {
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }


//        var itemData=data.removeAt(fromPosition)
//        data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition,itemData)

        notifyItemMoved(fromPosition, toPosition)

        return true
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)

    }

    override fun getItemCount(): Int {
        return data.size
    }
    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {

        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text = data.meanings?.get(0)?.translation?.translation
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

}
