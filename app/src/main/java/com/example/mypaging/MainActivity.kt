package com.example.mypaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaging.adapters.NumAdapter
import com.example.mypaging.dagger.DaggerApplicationGraph
import com.example.mypaging.viewModels.ListViewModelImpl
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListVIewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApplicationGraph.create().inject(this)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModelImpl::class.java)
        val textPos = findViewById<TextView>(R.id.textPosition)
        val list = findViewById<RecyclerView>(R.id.num_list)
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility= View.VISIBLE
        val adapter = NumAdapter(
            NumAdapter.OnClickListener {

            },
            NumAdapter.OnChangeListener {
                textPos.text = it.toString()
                viewModel.setPosition(it)
            }
        )
        list.adapter = adapter
        viewModel.newDataToAdapter.observe(this, {
            progressBar.visibility=View.GONE
            adapter.submitList(it)
        })
    }
}