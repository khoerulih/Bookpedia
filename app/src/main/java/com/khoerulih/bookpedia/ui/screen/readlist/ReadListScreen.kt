package com.khoerulih.bookpedia.ui.screen.readlist

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoerulih.bookpedia.R
import com.khoerulih.bookpedia.di.Injection
import com.khoerulih.bookpedia.model.Book
import com.khoerulih.bookpedia.ui.ViewModelFactory
import com.khoerulih.bookpedia.ui.common.UiState
import com.khoerulih.bookpedia.ui.components.BookItem

@Composable
fun ReadListScreen(
    modifier: Modifier = Modifier,
    viewModel: ReadListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBookReadList()
            }
            is UiState.Success -> {
                if (uiState.data.isNotEmpty()) {
                    ReadListContent(
                        book = uiState.data,
                        navigateToDetail = navigateToDetail,
                        modifier = modifier
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(text = stringResource(id = R.string.empty_read_list))
                    }
                }
            }
            is UiState.Error -> {
                Log.e("Error", "Failed to load data")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadListContent(
    book: List<Book>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        items(book) { book ->
            BookItem(
                title = book.title,
                author = book.author,
                cover = book.cover,
                modifier = Modifier.clickable {
                    navigateToDetail(book.id)
                }
            )
        }
    }
}