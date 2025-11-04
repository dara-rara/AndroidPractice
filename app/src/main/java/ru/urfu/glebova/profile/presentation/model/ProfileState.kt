package ru.urfu.glebova.profile.presentation.model

import android.net.Uri

data class ProfileState(
    val name: String = "",
    val photoUri: Uri = Uri.EMPTY,
    val resumeUrl: String = "",
    val isDownloading: Boolean = false
)