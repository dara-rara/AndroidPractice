package ru.urfu.glebova.characters.presentation.screen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.model.CharactersListFilter
import ru.urfu.glebova.characters.presentation.model.CharactersListViewState
import ru.urfu.glebova.characters.presentation.viewModel.CharactersListViewModel
import ru.urfu.glebova.uikit.FullscreenError
import ru.urfu.glebova.uikit.FullscreenLoading
import ru.urfu.glebova.uikit.Spacing

@Composable
fun CharactersListScreen() {
    val viewModel = koinViewModel<CharactersListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    CharactersListScreenContent(
        state,
        viewModel::onCharactersClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick,
        viewModel::onFilterChange,
        viewModel.shouldShowBadge(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersListScreenContent(
    state: CharactersListViewState,
    onCharactersClick: (CharacterUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFilterChange: (CharactersListFilter) -> Unit = {},
    shouldShowBadge: Boolean,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        floatingActionButton = {
            BadgedBox(
                badge = {
                    if (shouldShowBadge) {
                        Badge(
                            containerColor = Color(0xFFFF9800),
                            modifier = Modifier.size(Spacing.medium)
                        )
                    }
                }
            ) {
                FloatingActionButton(onClick = { onSettingsClick() }) {
                    Icon(Icons.Default.MoreVert, "Фильтр")
                }
            }
        },
        topBar = {
            TopAppBar(
                { CharactersListFilter(state, onFilterChange) },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Box(Modifier.padding(it)) {
            when (state.state) {
                CharactersListViewState.State.Loading -> {
                    FullscreenLoading()
                }

                is CharactersListViewState.State.Error -> {
                    FullscreenError(
                        retry = { onRetryClick() },
                        text = state.state.error
                    )
                }

                is CharactersListViewState.State.Success -> {
                    LazyColumn(
                        modifier = Modifier.padding(vertical = Spacing.small),
                        verticalArrangement = Arrangement.spacedBy(Spacing.small)
                    ) {
                        items(state.state.data) { character ->
                            CharactersListItem(
                                character = character,
                                onCharacterClick = { onCharactersClick(character) }
                            )
                        }
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
private fun CharactersListFilter(
    state: CharactersListViewState,
    onFilterChange: (CharactersListFilter) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        state.filters.forEach { filter ->
            FilterChip(
                selected = filter == state.currentFilter,
                label = { Text(filter.text) },
                onClick = { onFilterChange(filter) },
            )
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
//
//@Preview(showBackground = true)
//@Composable
//fun CharactersListPreview() {
//    CharactersListScreenContent(
//        CharactersListViewState(CharactersListViewState.State.Success(MockData.getCharacters()))
//    )
//}


