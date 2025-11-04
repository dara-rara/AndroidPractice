package ru.urfu.glebova.profile.presentation.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.urfu.glebova.EditProfile
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import ru.urfu.glebova.profile.data.repository.IProfileRepository
import ru.urfu.glebova.profile.presentation.model.ProfileState

class ProfileViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val repository: IProfileRepository
) : ViewModel() {

    private val state = MutableStateFlow(ProfileState())
    val viewState: StateFlow<ProfileState> = state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeProfile().collect { profile ->
                state.update {
                    it.copy(
                        name = profile.name,
                        photoUri = safeStringToUri(profile.photoUri),
                        resumeUrl = profile.url
                    )
                }
            }
        }
    }

    private fun safeStringToUri(uriString: String): Uri {
        return try {
            if (uriString.isNotEmpty()) Uri.parse(uriString) else Uri.EMPTY
        } catch (e: Exception) {
            Uri.EMPTY
        }
    }

    fun onEditClick() {
        topLevelBackStack.add(EditProfile)
    }

    fun downloadResume(context: Context) {
        viewModelScope.launch {
            try {
                state.update { it.copy(isDownloading = true) }

                val resumeUrl = state.value.resumeUrl
                if (resumeUrl.isNotEmpty()) {
                    val uri = Uri.parse(resumeUrl)
                    if (uri.scheme == null) {
                        val fixedUrl = "https://$resumeUrl"
                        openResumeInBrowser(context, fixedUrl)
                    } else {
                        openResumeInBrowser(context, resumeUrl)
                    }
                } else {
                    showToast(context, "Portfolio link not provided")
                }
            } catch (e: Exception) {
                showToast(context, "Error opening portfolio: ${e.message}")
                e.printStackTrace()
            } finally {
                state.update { it.copy(isDownloading = false) }
            }
        }
    }

    private fun openResumeInBrowser(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
        } catch (e: Exception) {
            showToast(context, "Failed to open portfolio")
            throw e
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}