package com.example.lab4.ui

import androidx.lifecycle.ViewModel
import com.example.lab4.data.MyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

//    fun SetValue(guess: String) {
//        _uiState.update { currentState ->
//            currentState.copy(guess = guess)
//        }
//    }

    fun SetCategoryList(list: List<Category>) {
        _uiState.update {
            it.copy(categoryList = list)
        }
    }

    fun SetCurrentCategory(currentCategory: Category) {
        _uiState.update {
            it.copy(currentCategory = currentCategory)
        }
    }

    fun SetCurrentRecommendation(currentRecommendation: Recommendation) {
        _uiState.update {
            it.copy(currentRecommendation = currentRecommendation)
        }
    }

    fun resetOrder() {
        _uiState.update {
            it.copy(
                currentCategory = null,
                currentRecommendation = null
            )
        }
    }
}