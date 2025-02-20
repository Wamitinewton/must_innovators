package com.newton.events.presentation.view.event_registration

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import com.newton.common_ui.ui.DropdownSelector
import com.newton.common_ui.ui.GlowingBorderAnimation
import com.newton.common_ui.ui.GlowingBorderPresets
import com.newton.common_ui.ui.LoadingOverlay
import com.newton.common_ui.ui.MultilineInputField
import com.newton.common_ui.ui.PrimaryButton
import com.newton.common_ui.ui.ReadOnlyTextField
import com.newton.common_ui.ui.SectionHeader
import com.newton.common_ui.ui.ValidatedTextField
import com.newton.core.data.dto.EventRegistrationResponse
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.events.presentation.states.EventDetailsState
import com.newton.events.presentation.states.RegistrationState
import com.newton.events.presentation.view.composables.RegistrationSuccessBottomSheet
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel
import com.newton.events.presentation.viewmodel.EventsSharedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRegistrationScreen(
    onClose: () -> Unit,
    eventsSharedViewModel: EventsSharedViewModel,
    userDataViewModel: GetUserDataViewModel = hiltViewModel(),
    eventRsvpViewmodel: EventRsvpViewmodel
) {
    val userDataState by userDataViewModel.getUserDataState.collectAsState()
    val eventUiState by eventsSharedViewModel.uiState.collectAsState()
    val event = (eventUiState as EventDetailsState.Success).event
    val registrationState by eventRsvpViewmodel.registrationState.collectAsState()
    val formState by eventRsvpViewmodel.formState.collectAsState()
    val validationState by eventRsvpViewmodel.validationState.collectAsState()

    val educationLevels = listOf("1", "2", "3", "4",)
    val context = LocalContext.current
    var expectations by remember { mutableStateOf("") }

    var showSuccessAnimation by remember { mutableStateOf(true) }
    var showSuccessSheet by remember { mutableStateOf(false) }
    var registrationResponse by remember { mutableStateOf<RegistrationResponse?>(null) }


    LaunchedEffect(userDataState.userData) {
        userDataState.userData?.let { user ->
            eventRsvpViewmodel.updateFirstName(user.first_name)
            eventRsvpViewmodel.updateLastName(user.last_name)
            eventRsvpViewmodel.updateEmail(user.email)
            eventRsvpViewmodel.updateCourse(user.course)
        }
    }

    LaunchedEffect(registrationState) {
        when(registrationState) {
            is RegistrationState.Error -> {
                Toast.makeText(
                    context,
                    (registrationState as RegistrationState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }
            is RegistrationState.Success -> {
                val response = (registrationState as RegistrationState.Success).data
                registrationResponse = response
//                showSuccessAnimation = true
            }
            else -> {}
        }
    }

 GlowingBorderAnimation(
     isActive = showSuccessAnimation,
     colors = GlowingBorderPresets.Success,
     onAnimationComplete = { showSuccessSheet = true }
 ) {
     Scaffold(
         topBar = {
             TopAppBar(
                 title = { Text("Event Registration") },
                 navigationIcon = {
                     IconButton(onClick = onClose) {
                         Icon(
                             imageVector = Icons.Default.Close,
                             contentDescription = "Close"
                         )
                     }
                 }
             )
         }
     ) { padding ->
         Box(modifier = Modifier.fillMaxSize()) {
             Column(
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(padding)
                     .padding(horizontal = 16.dp)
                     .verticalScroll(rememberScrollState())
             ) {
                 Card(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 16.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = MaterialTheme.colorScheme.primaryContainer
                     )
                 ) {
                     Column(
                         modifier = Modifier.padding(16.dp)
                     ) {
                         Text(
                             text = "Registering for:",
                             style = MaterialTheme.typography.labelLarge
                         )
                         Spacer(modifier = Modifier.height(4.dp))
                         Text(
                             text = event.name,
                             style = MaterialTheme.typography.titleLarge,
                             fontWeight = FontWeight.Bold
                         )
                     }
                 }

                 SectionHeader(title = "Personal Information")

                 ReadOnlyTextField(
                     value = "${formState.firstName} ${formState.lastName}",
                     label = "Full Name",
                     leadingIcon = Icons.Default.Person3,
                     contentDescription = "Name"
                 )

                 ReadOnlyTextField(
                     value = formState.email,
                     label = "Email Address",
                     leadingIcon = Icons.Default.Person3,
                     contentDescription = "Email"
                 )

                 ReadOnlyTextField(
                     value = formState.course,
                     label = "Course",
                     leadingIcon = Icons.Default.School,
                     contentDescription = "Email"
                 )

                 Spacer(modifier = Modifier.height(16.dp))

                 SectionHeader(title = "Education Details")

                 DropdownSelector(
                     selectedValue = formState.educationLevel,
                     options = educationLevels,
                     onSelectionChanged = { eventRsvpViewmodel.updateEducationLevel(it) },
                     label = "Education Level",
                     leadingIcon = Icons.Default.School,
                     contentDescription = "Education Level",
                     modifier = Modifier.padding(vertical = 8.dp)
                 )

                 ValidatedTextField(
                     value = formState.phoneNumber,
                     onValueChange = { eventRsvpViewmodel.updatePhoneNumber(it) },
                     label = "Phone Number",
                     leadingIcon = Icons.Default.Phone,
                     contentDescription = "Phone",
                     errorMessage = validationState.phoneNumberError,
                     keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                     modifier = Modifier.padding(vertical = 8.dp)
                 )

                 MultilineInputField(
                     value = expectations,
                     onValueChange = {
                         expectations = it
                         eventRsvpViewmodel.updateExpectations(it)
                     },
                     label = "Expectations",
                     leadingIcon = Icons.Default.Feedback,
                     contentDescription = "Expectations",
                     errorMessage = validationState.expectationsError,
                     placeholder = "What do you hope to learn from this event?",
                     modifier = Modifier.padding(vertical = 8.dp)
                 )

                 Spacer(modifier = Modifier.height(24.dp))

                 PrimaryButton(
                     text = "Register for Event",
//                     onClick = { eventRsvpViewmodel.registerForEvent(event.id) },
                     onClick =  { showSuccessAnimation = !showSuccessAnimation  },
                     modifier = Modifier.padding(vertical = 16.dp)
                 )

                 Spacer(modifier = Modifier.height(16.dp))
             }

             LoadingOverlay(
                 isLoading = registrationState is RegistrationState.Loading &&
                         (registrationState as? RegistrationState.Loading)?.isLoading == true,
                 loadingText = "Registering..."
             )
         }
     }
 }

    if (showSuccessSheet) {
        val testResponse = registrationResponse ?: RegistrationResponse(
            course = "bcs",
            educationalLevel = "1",
            email = "wamitinewton@gmail.com",
            event = 1,
            expectations = "We are expecting a lot of API learning",
            fullName = "Newton Wamiti",
            phoneNumber = "0792036343",
            registrationTimestamp =  "2025-01-15T10:00:00Z",
            ticketNumber = "TEST123456",
            uid = "random"
        )

        RegistrationSuccessBottomSheet(
            registrationResponse = testResponse,
            onRegisteredEvents = {},
            onDismiss = {
                showSuccessSheet = false
                // Reset the animation state for repeated testing
                showSuccessAnimation = false
            }
        )
    }
    }
