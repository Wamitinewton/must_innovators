package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*

@Composable
fun ContactInfo(
    modifier: Modifier = Modifier,
    partnersState: AddPartnersState,
    onEvent: (AddPartnersEvent) -> Unit
) {
    CustomCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Contact Information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = partnersState.website,
                onValueChange = { onEvent.invoke(AddPartnersEvent.WebsiteChange(it)) },
                label = { Text("Website URL") },
                placeholder = { Text("e.g. cloud.google.com") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null
                    )
                },
                supportingText = {
                    partnersState.errors["webUrl"]?.let { Text(it) }
                },
                singleLine = true,
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = partnersState.contactEmail,
                onValueChange = { onEvent.invoke(AddPartnersEvent.ContactEmailChange(it)) },
                label = { Text("Contact Email") },
                placeholder = { Text("e.g. partnerships@google.com") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                supportingText = {
                    partnersState.errors["email"]?.let { Text(it) }
                },
                singleLine = true,
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = partnersState.contactPerson,
                onValueChange = { onEvent.invoke(AddPartnersEvent.ContactPersonChange(it)) },
                label = { Text("Contact Person") },
                placeholder = { Text("e.g. Alex Brown, Developer Advocate") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                singleLine = true,
                supportingText = {
                    partnersState.errors["person"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Social Media",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = partnersState.socialLinkedIn,
                onValueChange = { onEvent.invoke(AddPartnersEvent.LinkedInChange(it)) },
                label = { Text("LinkedIn") },
                placeholder = { Text("LinkedIn profile or page URL") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Box(
                        modifier =
                        Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0077B5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("in", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                singleLine = true,
                supportingText = {
                    partnersState.errors["linkedin"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = partnersState.socialTwitter,
                onValueChange = { onEvent.invoke(AddPartnersEvent.TwitterChange(it)) },
                label = { Text("Twitter/X") },
                placeholder = { Text("Twitter/X profile URL") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Box(
                        modifier =
                        Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1DA1F2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("X", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                singleLine = true,
                supportingText = {
                    partnersState.errors["twitter"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    }
}
