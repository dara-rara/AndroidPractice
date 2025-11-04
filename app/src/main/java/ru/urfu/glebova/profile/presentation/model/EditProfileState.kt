package ru.urfu.glebova.profile.presentation.model

import android.net.Uri

data class EditProfileState(
    val photoUri: Uri = Uri.EMPTY,
    val name: String = "",
    val resumeUrl: String = "",
    val isNeedToShowPermission: Boolean = false,
    val isNeedToShowSelect: Boolean = false,
    val pendingPermissionRequest: Array<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditProfileState

        if (isNeedToShowPermission != other.isNeedToShowPermission) return false
        if (isNeedToShowSelect != other.isNeedToShowSelect) return false
        if (photoUri != other.photoUri) return false
        if (name != other.name) return false
        if (resumeUrl != other.resumeUrl) return false
        if (!pendingPermissionRequest.contentEquals(other.pendingPermissionRequest)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isNeedToShowPermission.hashCode()
        result = 31 * result + isNeedToShowSelect.hashCode()
        result = 31 * result + photoUri.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + resumeUrl.hashCode()
        result = 31 * result + (pendingPermissionRequest?.contentHashCode() ?: 0)
        return result
    }
}