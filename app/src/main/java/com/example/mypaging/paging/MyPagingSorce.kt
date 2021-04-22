package com.example.mypaging.paging

import com.example.mypaging.api.ApiService
import com.example.mypaging.data.NumData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MyPagingSource @Inject constructor(
    private val api: ApiService
) {
    private var list: List<NumData>? = null

    private var activeLastPosition = 0
    private var limit = 30

    private var mutex= Mutex()

    fun isLoading(): Boolean {
        return mutex.isLocked
    }

    suspend fun nextPage(pos: Int): List<NumData>? {

        if (pos > activeLastPosition) {
            activeLastPosition = pos
        }
        return if (list.isNullOrEmpty()) {
            list = getNextData(limit, activeLastPosition)
            list
        } else if (activeLastPosition + limit > list!!.size) {
            list = list!!.plus(getNextData(limit, activeLastPosition))
            list
        } else null

    }

    private suspend fun getNextData(limit: Int, offset: Int): List<NumData> {
        mutex.withLock {
            return api.getListNum(limit)
        }
    }


}