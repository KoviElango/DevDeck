package com.example.devdeck.viewmodel

class RecentSearchManager(
) {
    private val recentSearches = mutableListOf<String>()
    fun getSearches(): List<String> = recentSearches
}
