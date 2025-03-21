package com.newton.account.presentation.view

import android.webkit.URLUtil.isValidUrl
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.account.presentation.composables.update_profile.ChipsSection
import com.newton.account.presentation.composables.update_profile.FormSection
import com.newton.account.presentation.composables.update_profile.ProfileHeader
import com.newton.account.presentation.composables.update_profile.SaveButton
import com.newton.account.presentation.composables.update_profile.SuccessDialog
import com.newton.account.presentation.composables.update_profile.UnsavedChangesDialog
import com.newton.account.presentation.composables.update_profile.YearOfStudyDropdown
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.DefaultTextField
import com.newton.common_ui.ui.MultilineInputField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            onConfirm = onNavigateBack,
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

    DefaultScaffold (
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.97f)
                ),
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
                modifier = Modifier
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(firstNameFocusRequester),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
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
                        keyboardOptions = KeyboardOptions(
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
                        keyboardOptions = KeyboardOptions(
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
                        keyboardOptions = KeyboardOptions(
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
                        value = profileFormState.github,
                        onValueChange = {
                            viewModel.updateGithub(it)
                            hasUnsavedChanges = true
                        },
                        label = "GitHub",
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        ),
                        isError = profileFormState.github.isNotEmpty() && !isValidUrl(profileFormState.github),
                        validateUrl = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DefaultTextField(
                        value = profileFormState.linkedin,
                        onValueChange = {
                            viewModel.updateLinkedin(it)
                            hasUnsavedChanges = true
                        },
                        label = "LinkedIn",
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        ),
                        isError = profileFormState.linkedin.isNotEmpty() && !isValidUrl(profileFormState.linkedin),

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DefaultTextField(
                        value = profileFormState.twitter,
                        onValueChange = {
                            viewModel.updateTwitter(it)
                            hasUnsavedChanges = true
                        },
                        label = "Twitter",
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        ),
                        isError = profileFormState.twitter.isNotEmpty() && !isValidUrl(profileFormState.twitter),

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

