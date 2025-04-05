package com.newton.events.presentation.view.eventRegistration

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import com.newton.events.presentation.events.*
import com.newton.events.presentation.states.*

@Composable
fun EventRegistrationContent(
    event: EventsData,
    eventRegistrationState: EventRegistrationState,
    educationLevels: List<String>,
    onEvent: (EventRsvpUiEvent) -> Unit
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Registering for:",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleLarge, //
                    fontWeight = FontWeight.Bold
                )
            }
        }
        SectionHeader(title = "Personal Information")

        EventRegistrationForm(
            educationLevels = educationLevels,
            expectations = eventRegistrationState.expectations,
            expectationsError = eventRegistrationState.expectationsError,
            onEducationLevelChanged = {
                onEvent(EventRsvpUiEvent.UpdateEducationLevel(it))
            },
            onPhoneNumberChanged = {
                onEvent(EventRsvpUiEvent.UpdatePhoneNumber(it))
            },
            onExpectationsChanged = {
                onEvent(EventRsvpUiEvent.UpdateExpectations(it))
            },
            firstName = eventRegistrationState.firstName,
            lastName = eventRegistrationState.lastName,
            email = eventRegistrationState.email,
            course = eventRegistrationState.course,
            educationLevel = eventRegistrationState.educationLevel,
            phoneNumber = eventRegistrationState.phoneNumber,
            phoneNumberError = eventRegistrationState.phoneNumberError
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            onClick = {
                onEvent(EventRsvpUiEvent.SubmitRegistration(event.id))
            },
            modifier = Modifier.padding(vertical = 16.dp),
            content = { Text("Register for Event") }
        )

        Spacer(modifier = Modifier.height(50.dp))
    }
}
