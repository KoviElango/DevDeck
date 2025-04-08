package com.example.devdeck.util

fun updateRecentSearches(
    currentList: List<String>,
    newEntry: String,
    maxSize: Int = 5
): List<String> {
    val filtered = currentList.filterNot { it == newEntry }
    return (listOf(newEntry) + filtered).take(maxSize)
}
