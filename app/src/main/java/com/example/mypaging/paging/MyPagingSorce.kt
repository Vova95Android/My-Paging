package com.example.mypaging.paging

import com.example.mypaging.api.ApiService
import com.example.mypaging.data.NumData
import javax.inject.Inject

class MyPagingSorce @Inject constructor(
    private val api:ApiService
){

    suspend fun getNextData(limit: Int, offset:Int): List<NumData>{
        return api.getListNum(limit)
    }
}