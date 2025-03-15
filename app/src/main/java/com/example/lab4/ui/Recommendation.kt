package com.example.lab4.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lab4.AppBar
import com.example.lab4.R

class Recommendation(
    name: String,
    @DrawableRes imageId: Int = R.drawable.ic_launcher_foreground,
    val description: String = ""
) : HavingBriefDescription(
    name = name,
    imageId = imageId
) {
    @Composable
    fun fullDescription(
        onGoBackButtonClicked: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            AppBar(
                text = name,
                isHaveButton = true,
                buttonFunction = onGoBackButtonClicked
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.padding(16.dp).fillMaxWidth()
                )
            }
            Text(
                text = description,
                modifier = modifier.padding(16.dp),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}