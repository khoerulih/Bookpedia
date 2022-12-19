package com.khoerulih.bookpedia.ui.screen.readlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoerulih.bookpedia.data.BookRepository
import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ReadListViewModel(private val repository: BookRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Book>>>
        get() = _uiState

    fun getBookReadList() {
        viewModelScope.launch {
            repository.getBookReadList()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { book ->
                    _uiState.value = UiState.Success(book)
                }
        }
    }
}