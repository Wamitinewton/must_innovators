package com.newton.events.presentation.view.event_registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.SectionHeader
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.events.presentation.events.EventRsvpUiEvent
import com.newton.events.presentation.states.EventRegistrationState

@Composable
fun EventRegistrationContent(
    event: EventsData,
    eventRegistrationState: EventRegistrationState,
    educationLevels: List<String>,
    onEvent: (EventRsvpUiEvent) -> Unit
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
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
                    style = MaterialTheme.typography.titleLarge,/**/
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