package ru.urfu.glebova.characters.data.badge

class Badge {
    private var hasActiveFilters: Boolean = false

    fun setFiltersActive(active: Boolean) {
        hasActiveFilters = active
    }

    fun hasActiveFilters(): Boolean = hasActiveFilters
}