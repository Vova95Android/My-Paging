package com.example.mypaging.api

import com.example.mypaging.data.NumData
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class ApiService @Inject constructor() {

    suspend fun getListNum(size: Int): List<NumData> {
        return suspendCoroutine {
            var list = listOf<NumData>()
            Thread {
                Thread.sleep(1500)
                val r = Random(Calendar.getInstance().time.time.toInt())
                for (i in 0..size) {
                    list = list.plus(NumData(r.nextInt().toString()))
                }

                it.resume(list)
            }.start()
        }
    }
}