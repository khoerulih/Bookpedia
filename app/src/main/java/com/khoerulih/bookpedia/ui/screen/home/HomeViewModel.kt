package com.khoerulih.bookpedia.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoerulih.bookpedia.data.BookRepository
import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Book>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { book ->
                    _uiState.value = UiState.Success(book)
                }
        }
    }

    fun searchBooks(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchBook(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ book ->
                    _uiState.value = UiState.Success(book)
                }
        }
    }
}