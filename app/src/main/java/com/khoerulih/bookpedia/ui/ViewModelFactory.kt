package com.khoerulih.bookpedia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khoerulih.bookpedia.data.BookRepository
import com.khoerulih.bookpedia.ui.screen.detail.DetailViewModel
import com.khoerulih.bookpedia.ui.screen.home.HomeViewModel
import com.khoerulih.bookpedia.ui.screen.readlist.ReadListViewModel

class ViewModelFactory(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ReadListViewModel::class.java)) {
            return ReadListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}