@file:Suppress("DEPRECATION")

package ru.urfu.glebova.uikit

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LikeCounter(
    likes: Int,
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeChanged: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            val newLikes = if (isLiked) likes - 1 else likes + 1
            onLikeChanged(newLikes)
        }) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = if (isLiked) "Unlike" else "Like",
                tint = if (isLiked) Color.Red else Color.Gray
            )
        }

        Text(
            text = likes.toString(),
            color = if (isLiked) Color.Red else Color.Gray
        )
    }
}