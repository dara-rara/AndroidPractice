package ru.urfu.glebova.characters.presentation.screen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.urfu.glebova.Characters
import ru.urfu.glebova.CharactersDetails
import ru.urfu.glebova.characters.presentation.MockData
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.model.CharactersListViewState
import ru.urfu.glebova.characters.presentation.viewModel.CharactersListViewModel
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import ru.urfu.glebova.uikit.FullscreenError
import ru.urfu.glebova.uikit.FullscreenLoading
import ru.urfu.glebova.uikit.Spacing

@Composable
fun CharactersListScreen() {
    val viewModel = koinViewModel<CharactersListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    CharactersListScreenContent(
        state.state,
        viewModel::onCharactersClick,
        viewModel::onRetryClick,
    )
}

@Composable
private fun CharactersListScreenContent(
    state: CharactersListViewState.State,
    onCharactersClick: (CharacterUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
) {
    when (state) {
        CharactersListViewState.State.Loading -> {
            FullscreenLoading()
        }

        is CharactersListViewState.State.Error -> {
            FullscreenError(
                retry = { onRetryClick() },
                text = state.error
            )
        }

        is CharactersListViewState.State.Success -> {
            LazyColumn(
                modifier = Modifier.padding(vertical = Spacing.small),
                verticalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                state.data.forEach { character ->
                    item {
                        CharactersListItem(character) { onCharactersClick(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun CharactersListItem(character: CharacterUiModel, onCharacterClick: (CharacterUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.medium)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { onCharacterClick(character) },
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.mini),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Spacing.medium))
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.small)
                ) {
                    StatusChip(status = character.status)
                    Text(
                        text = character.species,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, text) = when (status.lowercase()) {
        "alive" -> Color(0xFF4CAF50) to "ALIVE"
        "dead" -> Color(0xFFF44336) to "DEAD"
        else -> Color(0xFF9E9E9E) to "UNKNOWN"
    }

    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(Spacing.medium))
            .padding(horizontal = Spacing.small, vertical = Spacing.mini)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersListPreview() {
    CharactersListScreenContent(
        CharactersListViewState.State.Success(MockData.getCharacters())
    )
}

//@Composable
//fun CharactersListScreen(topLevelBackStack: TopLevelBackStack<Route>) {
//    val characters = remember { MockData.getCharacters() }
//    LaunchedEffect(characters) {
//        android.util.Log.d("CHARACTERS_DEBUG", "CharactersListScreen: ${characters.size} characters loaded")
//    }
//}


