package com.newton.events.presentation.view.event_registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.DropdownSelector
import com.newton.common_ui.ui.MultilineInputField
import com.newton.common_ui.ui.ReadOnlyTextField
import com.newton.common_ui.ui.SectionHeader
import com.newton.common_ui.ui.ValidatedTextField

@Composable
fun EventRegistrationForm(
    educationLevels: List<String>,
    expectations: String,
    expectationsError: String? = null,
    onEducationLevelChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onExpectationsChanged: (String) -> Unit,
    firstName: String,
    lastName: String,
    email: String,
    course: String,
    educationLevel: String,
    phoneNumber: String,
    phoneNumberError: String? = null,
) {
    Column {

        ReadOnlyTextField(
            value = "$firstName $lastName",
            label = "Full Name",
            leadingIcon = Icons.Default.Person3,
            contentDescription = "Name"
        )

        ReadOnlyTextField(
            value = email,
            label = "Email Address",
            leadingIcon = Icons.Default.Person3,
            contentDescription = "Email"
        )

        ReadOnlyTextField(
            value = course,
            label = "Course",
            leadingIcon = Icons.Default.School,
            contentDescription = "Course"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Education Details")

        DropdownSelector(
            selectedValue = educationLevel,
            options = educationLevels,
            onSelectionChanged = onEducationLevelChanged,
            label = "Education Level",
            leadingIcon = Icons.Default.School,
            contentDescription = "Education Level",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        ValidatedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChanged,
            label = "Phone Number",
            leadingIcon = Icons.Default.Phone,
            contentDescription = "Phone",
            errorMessage = phoneNumberError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        MultilineInputField(
            value = expectations,
            onValueChange = onExpectationsChanged,
            label = "Expectations",
            leadingIcon = Icons.Default.Feedback,
            contentDescription = "Expectations",
            errorMessage = expectationsError,
            placeholder = "What do you hope to learn from this event?",
            modifier = Modifier.padding(vertical = 8.dp)
        )

    }
}