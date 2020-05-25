package geekbrains.ru.translator.view.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.translator.R
import com.google.vitaly.model.data.SearchResult
import geekbrains.ru.translator.utils.convertMeaningsToString
import geekbrains.ru.translator.view.main.MainActivity
import geekbrains.ru.translator.view.main.image_loader.GlideImageLoader
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.description_textview_recycler_item
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.header_textview_recycler_item
import kotlinx.android.synthetic.main.item_card_view_image.view.*

class MainAdapter(private var onListItemClickListener: OnListItemClickListener, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>(),ItemTouchHelperAdapter {
    private var data=mutableListOf<SearchResult>()
    private val glideImageLoader=GlideImageLoader()

    fun setData(dataListSearchResult: List<SearchResult>) {
        data.addAll(dataListSearchResult)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_view_image, parent, false)
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        var itemData=data.removeAt(fromPosition)
        data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition,itemData)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)

    }

    override fun getItemCount()=data.size

    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view)/*,
        ItemTouchHelperViewHolder*/ {

        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    header_textview_recycler_item.apply {
                        text = data.text
                        setOnTouchListener { _, event ->
                            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                                mainActivity.onStartDrag(this@RecyclerItemViewHolder)
                            }
                            false
                        }
                    }
                    data.meanings?.get(0)?.previewUrl?.let{
                        glideImageLoader?.loadInto("https:$it",image_view)}
                    description_textview_recycler_item.text = convertMeaningsToString(data.meanings!!)
                    setOnClickListener { openInNewWindow(data) }
                }
            }
        }


/*
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

 */
    }
}
