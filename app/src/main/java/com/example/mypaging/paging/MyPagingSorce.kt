package com.example.mypaging.paging

import com.example.mypaging.api.ApiService
import com.example.mypaging.data.NumData
import kotlinx.coroutines.Job
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MyPagingSource @Inject constructor(
    private val api: ApiService
) {
    var list: List<NumData>? = null

    private var activeLastPosition = 0
    private var limit = 30

    private var loading = false

    fun isLoading(): Boolean {
        return loading
    }

    suspend fun nextPage(pos: Int): List<NumData>? {
        if (list.isNullOrEmpty()) {
            loading = true
            list = getNextData(limit, activeLastPosition)
            loading = false
            return list
        }

        if (pos > activeLastPosition) {
            activeLastPosition = pos
        }
        return if ((!list.isNullOrEmpty()) && (activeLastPosition + limit > list!!.size)) {
            loading = true
            list = list!!.plus(getNextData(limit, activeLastPosition))
            loading = false
            list
        } else null

    }

    private suspend fun getNextData(limit: Int, offset: Int): List<NumData> {
        return api.getListNum(limit)
    }


}