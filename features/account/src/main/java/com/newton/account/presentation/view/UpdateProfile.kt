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
package com.newton.account.presentation.view

import android.webkit.URLUtil.isValidUrl
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.hapticfeedback.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.account.presentation.composables.updateProfile.*
import com.newton.account.presentation.viewmodel.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUpdateScreen(
    viewModel: UpdateAccountViewModel,
    onNavigateBack: () -> Unit
) {
    val profileFormState by viewModel.profileFormState.collectAsStateWithLifecycle()
    val updateProfileState by viewModel.profileState.collectAsStateWithLifecycle()
    val accountState by viewModel.accountState.collectAsState()

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val hapticFeedback = LocalHapticFeedback.current
    val firstNameFocusRequester = remember { FocusRequester() }

    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }
    var hasUnsavedChanges by rememberSaveable { mutableStateOf(false) }
    var showUnsavedChangesDialog by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(updateProfileState.isSuccess) {
        if (updateProfileState.isSuccess) {
            showSuccessDialog = true
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            delay(2000)
            showSuccessDialog = false
            viewModel.clearMessages()
            onNavigateBack()
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        firstNameFocusRequester.requestFocus()
    }

    if (showSuccessDialog) {
        SuccessDialog(
            name = "${updateProfileState.userData?.first_name ?: ""} ${updateProfileState.userData?.last_name ?: ""}",
            onDismiss = { showSuccessDialog = false }
        )
    }

    if (showUnsavedChangesDialog) {
        UnsavedChangesDialog(
            onDismiss = { showUnsavedChangesDialog = false },
            onConfirm = onNavigateBack
        )
    }

    LaunchedEffect(updateProfileState.error) {
        updateProfileState.error?.let { errors ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = errors,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    CustomScaffold(
        snackbarHostState = snackBarHostState,
        isLoading = updateProfileState.isLoading,
        topBar = {
            TopAppBar(
                title = { Text("Update Profile") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (hasUnsavedChanges) {
                            showUnsavedChangesDialog = true
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    SaveButton(
                        isSaving = updateProfileState.isLoading,
                        onClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            viewModel.updateProfile()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ProfileHeader(
                firstName = accountState.userData?.first_name,
                lastName = accountState.userData?.last_name
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(
                title = "Basic Information"
            ) {
                DefaultTextField(
                    value = profileFormState.firstName,
                    onValueChange = {
                        viewModel.updateFirstName(it)
                        hasUnsavedChanges = true
                    },
                    label = "First Name",
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(firstNameFocusRequester),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.lastName,
                    onValueChange = {
                        viewModel.updateLastName(it)
                        hasUnsavedChanges = true
                    },
                    label = "Last Name",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.email,
                    onValueChange = { },
                    label = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = false
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(title = "Academic Information") {
                DefaultTextField(
                    value = profileFormState.course,
                    onValueChange = {
                        viewModel.updateCourse(it)
                        hasUnsavedChanges = true
                    },
                    label = "Course",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.registrationNo,
                    onValueChange = {
                        viewModel.updateRegistrationNo(it)
                        hasUnsavedChanges = true
                    },
                    label = "Registration Number",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                YearOfStudyDropdown(
                    selectedYear = profileFormState.yearOfStudy,
                    onYearSelected = {
                        viewModel.updateYearOfStudy(it)
                        hasUnsavedChanges = true
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.graduationYear?.toString() ?: "",
                    onValueChange = {
                        val year = it.toIntOrNull()
                        viewModel.updateGraduationYear(year)
                        hasUnsavedChanges = true
                    },
                    label = "Graduation Year",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(title = "About You") {
                MultilineInputField(
                    value = profileFormState.bio,
                    onValueChange = {
                        viewModel.updateBio(it)
                        hasUnsavedChanges = true
                    },
                    label = "Bio"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(title = "Tech Stacks") {
                ChipsSection(
                    items = profileFormState.techStacks,
                    onAddItem = {
                        viewModel.addTechStack(it)
                        hasUnsavedChanges = true
                    },
                    onRemoveItem = {
                        viewModel.removeTechStack(it)
                        hasUnsavedChanges = true
                    },
                    placeholder = "Add tech stack"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(title = "Skills") {
                ChipsSection(
                    items = profileFormState.skills,
                    onAddItem = {
                        viewModel.addSkill(it)
                        hasUnsavedChanges = true
                    },
                    onRemoveItem = {
                        viewModel.removeSkill(it)
                        hasUnsavedChanges = true
                    },
                    placeholder = "Add skill"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormSection(title = "Social Media") {
                DefaultTextField(
                    value = profileFormState.github ?: "",
                    onValueChange = {
                        viewModel.updateGithub(it)
                        hasUnsavedChanges = true
                    },
                    label = "GitHub",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    isError = !profileFormState.github.isNullOrEmpty() && !isValidUrl(profileFormState.github),
                    validateUrl = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.linkedin ?: "",
                    onValueChange = {
                        viewModel.updateLinkedin(it)
                        hasUnsavedChanges = true
                    },
                    label = "LinkedIn",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    isError = !profileFormState.linkedin.isNullOrEmpty() && !isValidUrl(profileFormState.linkedin)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultTextField(
                    value = profileFormState.twitter ?: "",
                    onValueChange = {
                        viewModel.updateTwitter(it)
                        hasUnsavedChanges = true
                    },
                    label = "Twitter",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    isError = !profileFormState.twitter.isNullOrEmpty() && !isValidUrl(profileFormState.twitter)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            if (hasUnsavedChanges) {
                viewModel.clearMessages()
            }
        }
    }
}
