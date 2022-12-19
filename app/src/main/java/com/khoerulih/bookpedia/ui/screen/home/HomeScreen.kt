package com.khoerulih.bookpedia.ui.screen.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoerulih.bookpedia.di.Injection
import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.ui.ViewModelFactory
import com.khoerulih.bookpedia.ui.common.UiState
import com.khoerulih.bookpedia.ui.components.BookItem
import com.khoerulih.bookpedia.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks()
            }
            is UiState.Success -> {
                HomeContent(
                    book = uiState.data,
                    navigateToDetail = navigateToDetail,
                    searchBar = {
                        SearchBar(query = query, onQueryChange = viewModel::searchBooks)
                    },
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                Log.e("Error", "Failed to load data")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    book: List<Book>,
    modifier: Modifier = Modifier,
    searchBar: @Composable() () -> Unit,
    navigateToDetail: (Long) -> Unit
) {
    Column {
        searchBar()
        LazyVerticalGrid(
            cells = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier.testTag("BookList")
        ) {
            items(book) { book ->
                BookItem(
                    title = book.title,
                    author = book.author,
                    cover = book.cover,
                    modifier = Modifier
                        .clickable {
                            navigateToDetail(book.id)
                        }
                )
            }
        }
    }
}