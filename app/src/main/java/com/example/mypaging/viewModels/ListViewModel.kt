package com.example.mypaging.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypaging.data.NumData
import com.example.mypaging.paging.MyPagingSorce
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class ListViewModel() : ViewModel() {

    abstract val newDataToAdapter: LiveData<List<NumData>>
}


class ListViewModelImpl(private val paging: MyPagingSorce) : ListViewModel() {

    override val newDataToAdapter = MutableLiveData<List<NumData>>()

    var limit = 30
    private var position = 0
    var job: Job?=null

    init {
        getNumb()
    }

    fun setNewPosition(pos: Int) {
        position = pos
        if (position + limit > newDataToAdapter.value!!.size) {
            getNumb()
        }
    }

    fun getNumb(){
        job?.cancel()
        job = viewModelScope.launch {
            val data = paging.getNextData(limit * 3, position)
            if (newDataToAdapter.value==null) newDataToAdapter.value=data
            else newDataToAdapter.value = newDataToAdapter.value!!.plus(data)
        }
    }

}