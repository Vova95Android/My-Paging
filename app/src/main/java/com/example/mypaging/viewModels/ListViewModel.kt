package com.example.mypaging.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypaging.data.NumData
import com.example.mypaging.paging.MyPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ListViewModel() : ViewModel() {

    abstract val newDataToAdapter: LiveData<List<NumData>>
}


class ListViewModelImpl(private val paging: MyPagingSource) : ListViewModel() {

    override val newDataToAdapter = MutableLiveData<List<NumData>>()

    private var job: Job? = null

    init {
        setPosition(0)
    }

    fun setPosition(pos: Int) {
        if (!paging.isLoading()) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.IO) {
                val list = paging.nextPage(pos)
                withContext(Dispatchers.Main) {
                    if (list != null) newDataToAdapter.value = list
                }
            }
        }
    }

}