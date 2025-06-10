package com.example.singleactivityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val type: String, val content: String)
class HomeViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val allItems = listOf(
        Item("PDF", "PDF Content 1"),
        Item("Slides", "Slides Content 1"),
        Item("Docs", "Docs Content 1"),
        Item("PDF", "PDF Content 2"),
        Item("Slides", "Slides Content 2"),
        Item("Docs", "Docs Content 2")
    )

    init {
        _items.value = allItems
    }

    fun filterItems(type: String) {
        _items.value = if (type == "All") allItems else allItems.filter { it.type == type }
    }
}
