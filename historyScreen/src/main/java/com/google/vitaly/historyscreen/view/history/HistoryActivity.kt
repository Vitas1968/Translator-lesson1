package com.google.vitaly.historyscreen.view.history

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.vitaly.core.BaseActivity
import com.google.vitaly.historyscreen.R
import com.google.vitaly.historyscreen.injectDependencies
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<DataModel, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<SearchResult>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        check(history_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<DataModel> { renderData(it) })
    }

    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}
