package com.khoerulih.bookpedia.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoerulih.bookpedia.data.BookRepository
import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Book>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Book>>
        get() = _uiState

    fun getBookById(id: Long) {
        viewModelScope.launch{
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBookById(id))
        }
    }

    fun updateReadList(id: Long, newStatus: Boolean){
        viewModelScope.launch {
            repository.updateBookReadList(id, newStatus)
        }
    }
}