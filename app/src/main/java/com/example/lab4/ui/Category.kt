package com.example.lab4.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lab4.AppBar
import com.example.lab4.R

class Category(
    name: String,
    @DrawableRes imageId: Int = R.drawable.ic_launcher_foreground,
    var list : List<Recommendation> = listOf()
) : HavingBriefDescription(
    name = name,
    imageId = imageId
) {
    @Composable
    fun scrollableListScreen(
        onButtonRecommendationClicked: (Recommendation) -> Unit = {},
        onGoBackButtonClicked: () -> Unit = {},
        modifier: Modifier = Modifier) {
        Column (modifier = modifier) {
            AppBar(
                text = name,
                isHaveButton = true,
                buttonFunction = onGoBackButtonClicked
            )
            LazyColumn {
                items(list) { item ->
                    TextButton(
                        onClick = { onButtonRecommendationClicked(item) }
                    ) {
                        item.briefDescription()
                    }
                }
            }
        }
    }
}