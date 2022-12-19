package com.khoerulih.bookpedia.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoerulih.bookpedia.R
import com.khoerulih.bookpedia.di.Injection
import com.khoerulih.bookpedia.ui.ViewModelFactory
import com.khoerulih.bookpedia.ui.common.UiState
import com.khoerulih.bookpedia.ui.components.UpdateReadListButton
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme

@Composable
fun DetailScreen(
    id: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToReadList: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBookById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    title = data.title,
                    author = data.author,
                    description = data.description,
                    cover = data.cover,
                    isReadList = data.isInReadList,
                    onBackClick = navigateBack,
                    onUpdateReadList = {
                        viewModel.updateReadList(data.id, !data.isInReadList)
                        navigateToReadList()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    author: String,
    description: String,
    cover: Int,
    isReadList: Boolean,
    onBackClick: () -> Unit,
    onUpdateReadList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(id = cover),
                    contentDescription = null,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .padding(top = 32.dp, end = 16.dp, bottom = 4.dp, start = 16.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            UpdateReadListButton(
                text =
                if (isReadList)
                    stringResource(id = R.string.remove_from_read_list)
                else
                    stringResource(id = R.string.add_to_read_list),
                onClick = {
                    onUpdateReadList()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    BookpediaTheme {
        DetailContent(
            title = "Pulang",
            author = "Tere Liye",
            description = "Lorem Ipsum",
            cover = R.drawable.pulang,
            isReadList = false,
            onBackClick = { },
            onUpdateReadList = { })
    }
}