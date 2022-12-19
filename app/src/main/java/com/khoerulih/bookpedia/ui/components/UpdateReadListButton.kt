package com.khoerulih.bookpedia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme

@Composable
fun UpdateReadListButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "Update Book Read List Button"
            }
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun UpdateReadListButtonPreview() {
    BookpediaTheme() {
        UpdateReadListButton(
            text = "Add to Read List",
            onClick = {}
        )
    }
}