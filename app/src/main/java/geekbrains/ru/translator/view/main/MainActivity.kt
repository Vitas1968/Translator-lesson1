package geekbrains.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import geekbrains.ru.translator.R
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.utils.network.isOnline
import geekbrains.ru.translator.di.injectDependencies
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import geekbrains.ru.translator.view.main.adapter.OnStartDragListener
import geekbrains.ru.translator.utils.convertMeaningsToString
import geekbrains.ru.translator.view.descriptionscreen.DescriptionActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val HISTORY_ACTIVITY_PATH = "com.google.vitaly.historyscreen.view.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
class MainActivity : com.google.vitaly.core.BaseActivity<DataModel, MainInteractor>(),OnStartDragListener {
    override lateinit var model: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener,this@MainActivity) }
    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(
                            data.meanings!!
                        ),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModel()
        initViews()
        itemTouchHelper = getItemTochHelper()
    }

    private fun getItemTochHelper()=
        adapter
            .run {
                MyItemTouchHelper(this)
            }
            .run {
                ItemTouchHelper(this)
            }
            .apply {
                attachToRecyclerView(main_activity_recyclerview)
            }

    override fun setDataToAdapter(data: List<SearchResult>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniViewModel() {
        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<DataModel> { renderData(it) })
    }

    private fun initViews() {
        search_fab.setOnClickListener(fabClickListener)
        main_activity_recyclerview.adapter = adapter

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        viewHolder?.let { itemTouchHelper.startDrag(it)}
    }
}
