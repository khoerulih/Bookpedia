package com.khoerulih.bookpedia.data

import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.model.DummyBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {

    private val books = mutableListOf<Book>()

    init {
        DummyBook.dummyBook.forEach { book ->
            books.add(
                Book(
                    book.id,
                    book.title,
                    book.author,
                    book.description,
                    book.cover,
                    book.isInReadList
                )
            )
        }
    }

    fun getAllBooks(): Flow<List<Book>> {
        return flowOf(books)
    }

    fun getBookById(id: Long): Book {
        return books.first { book ->
            book.id == id
        }
    }

    fun updateBookReadList(id: Long, newReadListStatus: Boolean): Flow<Boolean> {
        val index = books.indexOfFirst { book -> book.id == id }
        val result = if (index >= 0) {
            val book = books[index]
            books[index] =
                book.copy(
                    id = book.id,
                    title = book.title,
                    author = book.author,
                    description = book.description,
                    cover = book.cover,
                    isInReadList = newReadListStatus
                )
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getBookReadList(): Flow<List<Book>> {
        return getAllBooks()
            .map { book ->
                book.filter {
                    it.isInReadList
                }
            }
    }

    fun searchBook(query: String): Flow<List<Book>> {
        return getAllBooks()
            .map { book ->
                book.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}