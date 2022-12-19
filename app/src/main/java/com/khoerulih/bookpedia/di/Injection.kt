package com.khoerulih.bookpedia.di

import com.khoerulih.bookpedia.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}