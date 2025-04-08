package com.example.devdeck.viewmodel

import androidx.annotation.RequiresApi

class RecentSearchManager(
    private val maxSize: Int = 5
) {
    private val recentSearches = mutableListOf<String>()

    fun getSearches(): List<String> = recentSearches

    @RequiresApi(35)
    fun addSearch(query: String): List<String> {
        if (query in recentSearches) recentSearches.remove(query)
        recentSearches.add(0, query)
        if (recentSearches.size > maxSize) recentSearches.removeLast()
        return recentSearches
    }

    fun removeSearch(query: String): List<String> {
        recentSearches.remove(query)
        return recentSearches
    }
}
