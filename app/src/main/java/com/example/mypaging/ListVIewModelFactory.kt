package com.example.mypaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypaging.paging.MyPagingSource
import com.example.mypaging.viewModels.ListViewModelImpl
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ListVIewModelFactory @Inject constructor(private val paging: MyPagingSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModelImpl::class.java))
            return ListViewModelImpl(paging) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}