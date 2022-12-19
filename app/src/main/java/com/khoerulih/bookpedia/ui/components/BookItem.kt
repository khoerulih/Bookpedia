package com.khoerulih.bookpedia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khoerulih.bookpedia.R
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme

@Composable
fun BookItem(
    title: String,
    author: String,
    cover: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {

        Column {
            Image(
                painter = painterResource(id = cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(170.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = title,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookItemPreview() {
    BookpediaTheme {
        BookItem(
            title = "Man Search For Meaning",
            author = "Viktor E.Frankl",
            cover = R.drawable.man_search_for_meaning
        )
    }
}