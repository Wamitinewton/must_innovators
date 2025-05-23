/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.feedback.presentation.viewmodel

import android.net.*
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class UserFeedbackViewModel
@Inject
constructor() : ViewModel() {
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

    fun reorderImages(
        fromIndex: Int,
        toIndex: Int
    ) {
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
