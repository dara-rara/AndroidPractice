package ru.urfu.glebova.characters.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.urfu.glebova.characters.presentation.model.CharactersFilterGender
import ru.urfu.glebova.characters.presentation.viewModel.CharactersFilterViewModel

@Composable
fun CharactersFilterDialog() {
    val viewModel = koinViewModel<CharactersFilterViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    CharactersFilterContent(
        state,
        viewModel::onFilterCheckedChange,
        viewModel::onSaveFilter,
        viewModel::onBack,
    )
}

@Composable
fun CharactersFilterContent(
    state: CharactersFilterGender,
    onFilterCheckedChange: (String) -> Unit = {},
    onSaveFilter: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {

    Dialog(
        onDismissRequest = { onBack() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Фильтрация",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Пол",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )

                val genderOptions = listOf(
                    "" to "Все",
                    "Male" to "Мужчины",
                    "Female" to "Женщины",
                    "Genderless" to "Бесполые",
                    "unknown" to "Неизвестно"
                )

                genderOptions.forEach { (value, label) ->
                    FilterRadioOption(
                        text = label,
                        isSelected = state.gender == value,
                        onSelected = { onFilterCheckedChange(value) }
                    )
                }
            }

            TextButton(
                onClick = { onSaveFilter(state.gender) },
                modifier = Modifier.align(End)
            ) {
                Text("Сохранить")
            }
        }
    }
}

@Composable
private fun FilterRadioOption(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelected() }
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersFilterDialogPreview() {
    MaterialTheme {
        CharactersFilterContent(
            state = CharactersFilterGender("male")
        )
    }
}
