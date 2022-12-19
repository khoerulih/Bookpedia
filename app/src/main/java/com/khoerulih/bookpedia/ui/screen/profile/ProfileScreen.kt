package com.khoerulih.bookpedia.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khoerulih.bookpedia.R
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painterResource(id = R.drawable.profile),
            contentDescription = "Photo Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .size(120.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Ikhsan Khoerul Rohman",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(4.dp)
        )
        Text(
            text = "khoerulih.r@gmail.com",
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    BookpediaTheme() {
        ProfileScreen()
    }
}