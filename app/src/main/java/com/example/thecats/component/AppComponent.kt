package com.example.thecats.component

import com.example.thecats.fragment.FavoriteFragment
import com.example.thecats.fragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: FavoriteFragment)
}
