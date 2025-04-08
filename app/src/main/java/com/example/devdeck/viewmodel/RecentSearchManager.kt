package com.example.devdeck.viewmodel

import androidx.annotation.RequiresApi

class RecentSearchManager(
    private val maxSize: Int = 5
) {
    private val recentList = mutableListOf<String>()

    @RequiresApi(35)
    fun add(username: String): List<String> {
        recentList.remove(username)
        recentList.add(0, username)
        if (recentList.size > maxSize) {
            recentList.removeLast()
        }
        return recentList.toList()
    }

    fun remove(username: String): List<String> {
        recentList.remove(username)
        return recentList.toList()
    }

    fun getAll(): List<String> = recentList.toList()
}