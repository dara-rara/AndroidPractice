package ru.urfu.glebova

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.java.KoinJavaComponent.inject
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.screen.CharactersDetailsDialog
import ru.urfu.glebova.characters.presentation.screen.CharactersFilterDialog
import ru.urfu.glebova.characters.presentation.screen.CharactersListScreen
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import ru.urfu.glebova.profile.presentation.screen.EditProfileScreen
import ru.urfu.glebova.profile.presentation.screen.ProfileScreen

interface TopLevelRoute : Route {
    val icon: ImageVector
}

data object Characters : TopLevelRoute {
    override val icon = Icons.Default.Menu
}

data class CharactersDetails(val character: CharacterUiModel) : Route
data object CharactersFilter : Route

data object Profile : TopLevelRoute {
    override val icon = Icons.Default.Person // Иконка профиля
}

data object EditProfile : Route

@Composable
fun MainScreen() {
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
    val dialogStrategy = remember {
        DialogSceneStrategy<Route>()
    }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Characters, Profile).forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, null) },
                    selected = topLevelBackStack.topLevelKey == route,
                    onClick = {
                        topLevelBackStack.addTopLevel(route)
                    }
                )
            }
        }
    }) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier.padding(padding),
            sceneStrategy = dialogStrategy,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Characters> {
                    CharactersListScreen()
                }
                entry<CharactersDetails> {
                    with(DialogSceneStrategy.dialog(DialogProperties())) {
                        CharactersDetailsDialog(character = it.character)
                    }
                }
                entry<CharactersFilter> {
                    with(DialogSceneStrategy.dialog(DialogProperties())) {
                        CharactersFilterDialog()
                    }
                }

                entry<Profile> {
                    ProfileScreen()
                }

                entry<EditProfile> {
                    EditProfileScreen()
                }
            }
        )
    }
}