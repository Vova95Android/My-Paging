package com.example.mypaging.dagger

import com.example.mypaging.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface ApplicationGraph {

    fun inject(activity: MainActivity)

}