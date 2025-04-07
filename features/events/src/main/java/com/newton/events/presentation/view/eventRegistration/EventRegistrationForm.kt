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
package com.newton.events.presentation.view.eventRegistration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*

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
    phoneNumberError: String? = null
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
