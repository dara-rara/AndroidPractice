package ru.urfu.glebova.characters.presentation.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.urfu.glebova.characters.presentation.MockData
import ru.urfu.glebova.characters.presentation.model.CharacterDetailsViewState
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.viewModel.CharactersDetailsViewModel
import ru.urfu.glebova.uikit.LikeCounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersDetailsDialog(
    character: CharacterUiModel,
) {
    val viewModel = koinViewModel<CharactersDetailsViewModel> {
        parametersOf(character)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = { viewModel.onBack() },
    ) {
        CharacterDetailsContent(state, viewModel::onLikeChanged)
    }
}

@Composable
fun CharacterDetailsContent(
    state: CharacterDetailsViewState,
    onLikeChanged: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val context = LocalContext.current

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Character Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Icon(
                Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        shareCharacter(context, state.character.name)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = state.character.image,
                contentDescription = state.character.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = state.character.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                StatusChip(status = state.character.status)

                Text(
                    text = "${state.character.species} • ${state.character.gender}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoRow("Origin", state.character.origin.name)
            InfoRow("Location", state.character.location.name)
            InfoRow("Type", state.character.type.ifEmpty { "Unknown" })

            if (state.character.episode.isNotEmpty()) {
                InfoRow("Episodes", state.character.episode.size.toString())
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Like this character?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LikeCounter(
                likes = state.likes,
                isLiked = state.isLiked,
                onLikeChanged = onLikeChanged
            )

            if (state.isLiked) {
                Text(
                    text = "You liked this character!",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun InfoRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End
        )
    }
}

fun shareCharacter(context: Context, characterName: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Check out this Rick and Morty character: $characterName!")
    }
    context.startActivity(Intent.createChooser(intent, "Share character"))
}

@Preview(showBackground = true)
@Composable
fun CharactersDetailDialogPreview() {
    CharacterDetailsContent(CharacterDetailsViewState(MockData.getCharacters().first()))
}