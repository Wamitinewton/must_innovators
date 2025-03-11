package com.newton.feedback.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFeedbackViewModel @Inject constructor(): ViewModel() {
    private val _bugDescription = MutableStateFlow("")
    val bugDescription = _bugDescription.asStateFlow()

    private val _bugImages = MutableStateFlow<List<Uri>>(emptyList())
    val bugImages = _bugImages.asStateFlow()

    private val _generalFeedback = MutableStateFlow("")
    val generalFeedback = _generalFeedback.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating = _rating.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting = _isSubmitting.asStateFlow()

    private val _isSubmitSuccess = MutableStateFlow(false)
    val isSubmitSuccess = _isSubmitSuccess.asStateFlow()

    fun updateBugDescription(description: String) {
        _bugDescription.value = description
    }

    fun addBugImage(uri: Uri) {
        if (_bugImages.value.size < 3) {
            _bugImages.value += uri
        }
    }

    fun removeBugImage(uri: Uri) {
        _bugImages.value = _bugImages.value.filter { it != uri }
    }

    fun updateGeneralFeedback(feedback: String) {
        _generalFeedback.value = feedback
    }

    fun updateRating(newRating: Int) {
        _rating.value = newRating
    }

    fun reorderImages(fromIndex: Int, toIndex: Int) {
        _bugImages.update { currentImages ->
            currentImages.toMutableList().apply {
                val item = removeAt(fromIndex)
                add(toIndex, item)
            }
        }
    }

    fun submitBugReport() {
        viewModelScope.launch {
            _isSubmitting.value = true
            delay(2000)
            _isSubmitting.value = false
            _isSubmitSuccess.value = true
            delay(3000)
            _isSubmitSuccess.value = false
            _bugDescription.value = ""
            _bugImages.value = emptyList()
        }
    }

    fun submitGeneralFeedback() {
        viewModelScope.launch {
            _isSubmitting.value = true
            delay(2000)
            _isSubmitting.value = false
            _isSubmitSuccess.value = true
            delay(3000)
            _isSubmitSuccess.value = false
            _generalFeedback.value = ""
            _rating.value = 0
        }
    }
}