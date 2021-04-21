package com.example.mypaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaging.adapters.NumAdapter
import com.example.mypaging.api.ApiService
import com.example.mypaging.dagger.DaggerApplicationGraph
import com.example.mypaging.data.NumData
import com.example.mypaging.paging.MyPagingSorce
import com.example.mypaging.viewModels.ListViewModel
import com.example.mypaging.viewModels.ListViewModelImpl
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListVIewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApplicationGraph.create().inject(this)
        val viewModel=ViewModelProvider(this,viewModelFactory).get(ListViewModelImpl::class.java)
        val list = findViewById<RecyclerView>(R.id.num_list)
        val adapter = NumAdapter(
            NumAdapter.OnClickListener {

            },
            NumAdapter.OnChangeListener {
                findViewById<TextView>(R.id.textPosition).text=it.toString()
                viewModel.setNewPosition(it)
            }
        )
        list.adapter=adapter
        viewModel.newDataToAdapter.observe(this,{
            adapter.submitList(it)
        })
    }
}