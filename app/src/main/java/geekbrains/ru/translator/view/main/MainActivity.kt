package geekbrains.ru.translator.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.translator.R
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.presenter.Presenter
import geekbrains.ru.translator.view.base.BaseActivity
import geekbrains.ru.translator.view.base.View
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import geekbrains.ru.translator.view.main.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<DataModel>(),OnStartDragListener {

    private var adapter: MainAdapter? = null
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun createPresenter(): Presenter<DataModel, View> {
        return MainPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data
                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        adapter= MainAdapter(onListItemClickListener, searchResult, this)
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                       // main_activity_recyclerview.adapter =adapter
                       // val callBack=MyItemTouchHelper(adapter)
                        //itemTouchHelper=ItemTouchHelper(callBack)
                       // itemTouchHelper.attachToRecyclerView(main_activity_recyclerview)
                        itemTouchHelper= adapter
                            .run{
                            main_activity_recyclerview.adapter =this
                            MyItemTouchHelper(this)}
                            .run{
                                ItemTouchHelper(this)}
                            .apply {
                                attachToRecyclerView(main_activity_recyclerview)}

                    } else {
                        adapter!!.setData(searchResult)
                    }
                }
            }
            is DataModel.Loading -> {
                showViewLoading()
                if (dataModel.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = dataModel.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        viewHolder?.let { itemTouchHelper.startDrag(it)}
    }
}
