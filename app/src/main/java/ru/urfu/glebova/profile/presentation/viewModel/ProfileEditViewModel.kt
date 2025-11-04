package ru.urfu.glebova.profile.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import ru.urfu.glebova.profile.data.repository.IProfileRepository
import ru.urfu.glebova.profile.presentation.model.EditProfileState
import java.io.File

class ProfileEditViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val repository: IProfileRepository
) : ViewModel() {

    private val state = MutableStateFlow(EditProfileState())
    val viewState: StateFlow<EditProfileState> = state.asStateFlow()

    private var tempImageUri: Uri? = null

    init {
        viewModelScope.launch {
            repository.getProfile()?.let { profile ->
                state.update {
                    it.copy(
                        name = profile.name,
                        resumeUrl = profile.url,
                        photoUri = profile.photoUri.toUri(),
                        isNeedToShowPermission = false
                    )
                }
            }
        }
    }

    fun onNameChanged(name: String) {
        state.update { it.copy(name = name) }
    }

    fun onResumeUrlChanged(url: String) {
        state.update { it.copy(resumeUrl = url) }
    }

    fun onDoneClicked() {
        viewModelScope.launch {
            val currentState = state.value
            repository.setProfile(
                photoUri = currentState.photoUri.toString(),
                name = currentState.name,
                url = currentState.resumeUrl
            )
            back()
        }
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let {
            state.update { it.copy(photoUri = uri) }
        }
    }

    fun onSelectDismiss() {
        state.update { it.copy(isNeedToShowSelect = false) }
    }

    fun showPhotoSourceDialog() {
        state.update {
            it.copy(
                isNeedToShowSelect = true,
                isNeedToShowPermission = false
            )
        }
    }

    fun hidePhotoSourceDialog() {
        state.update { it.copy(isNeedToShowSelect = false) }
    }

    fun onAvatarClicked(context: Context) {
        checkAndRequestPermissions(context)
    }

    fun checkAndRequestPermissions(context: Context) {
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (permissionsToRequest.isNotEmpty()) {
            state.update { it.copy(pendingPermissionRequest = permissionsToRequest.toTypedArray()) }
        } else {
            showPhotoSourceDialog()
        }
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            showPhotoSourceDialog()
        } else {
            hidePhotoSourceDialog()
        }
    }

    fun launchCamera(context: Context): Uri? {
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile(
                "profile_${System.currentTimeMillis()}",
                ".jpg",
                picturesDir
            )
            tempImageUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                imageFile
            )
            tempImageUri
        } else {
            null
        }
    }

    fun launchGallery(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            false
        }
    }

    fun getTempImageUri(): Uri? = tempImageUri

    fun back() {
        topLevelBackStack.removeLast()
    }

    //для теста
//    fun fillTestData() {
//        state.update {
//            it.copy(
//                resumeUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
//                name = "Иванов Иван Иванович"
//            )
//        }
//    }
}
