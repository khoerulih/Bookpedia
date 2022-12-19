package com.khoerulih.bookpedia.model

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val description: String,
    val cover: Int,
    val isInReadList: Boolean,
)