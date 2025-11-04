package ru.urfu.glebova.profile.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import ru.urfu.glebova.R
import ru.urfu.glebova.profile.presentation.viewModel.ProfileEditViewModel
import ru.urfu.glebova.uikit.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen() {
    val viewModel = koinViewModel<ProfileEditViewModel>()
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.onImageSelected(uri)
        }
    }

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val tempUri = viewModel.getTempImageUri()
            if (tempUri != null) {
                viewModel.onImageSelected(tempUri)
            }
        }
    }

    val requestPermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        viewModel.onPermissionResult(allGranted)
    }

    LaunchedEffect(state.pendingPermissionRequest) {
        state.pendingPermissionRequest?.let { permissions ->
            requestPermissions.launch(permissions)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.edit_profile)) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        modifier = Modifier
                            .clickable { viewModel.back() }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Сохранить",
                        modifier = Modifier
                            .padding(end = Spacing.small)
                            .clickable { viewModel.onDoneClicked() }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            AsyncImage(
                model = state.photoUri.takeIf { it != Uri.EMPTY },
                contentDescription = "Аватар",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .clickable {
                        viewModel.onAvatarClicked(context)
                    }
                    .align(Alignment.CenterHorizontally),
                error = painterResource(R.drawable.ic_launcher_foreground),
                placeholder = painterResource(R.drawable.ic_launcher_foreground)
            )

            TextField(
                value = state.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { Text(text = stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.resumeUrl,
                onValueChange = { viewModel.onResumeUrlChanged(it) },
                label = { Text(text = stringResource(R.string.link)) },
                modifier = Modifier.fillMaxWidth()
            )

//            if (state.resumeUrl.isEmpty()) {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { viewModel.fillTestData() },
//                    shape = RoundedCornerShape(Spacing.small),
//                    color = MaterialTheme.colorScheme.primaryContainer
//                ) {
//                    Column(
//                        modifier = Modifier.padding(Spacing.medium),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "ТЕСТ: Автозаполнить",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.onPrimaryContainer
//                        )
//                        Text(
//                            text = "Нажмите чтобы заполнить тестовыми данными",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
//                        )
//                    }
//                }
//            }
        }
    }

    if (state.isNeedToShowSelect) {
        Dialog(onDismissRequest = { viewModel.onSelectDismiss() }) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(Spacing.medium)) {
                    Text(
                        text = stringResource(R.string.source),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = Spacing.medium)
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onSelectDismiss()
                                val cameraUri = viewModel.launchCamera(context)
                                cameraUri?.let { takePicture.launch(it) }
                            },
                        shape = RoundedCornerShape(Spacing.small),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = stringResource(R.string.camera),
                            modifier = Modifier.padding(Spacing.medium)
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onSelectDismiss()
                                if (viewModel.launchGallery(context)) {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            },
                        shape = RoundedCornerShape(Spacing.small),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = stringResource(R.string.gallery),
                            modifier = Modifier.padding(Spacing.medium)
                        )
                    }
                }
            }
        }
    }
}