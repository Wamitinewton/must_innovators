package com.newton.events.presentation.view.composables

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.CopyableText
import com.newton.common_ui.ui.CustomButton
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.core.utils.formatDateTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationSuccessBottomSheet(
    registrationResponse: RegistrationResponse,
    onRegisteredEvents: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "glowTransition")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .alpha(glowAlpha)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "success",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(72.dp)
                )
            }

        }

        Text(
            text = "Registration successful!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You've successfully registered for the event",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        RegistrationInfoItem(
            label = "Ticket Number",
            value = registrationResponse.ticketNumber,
            highlightValue = true,
            isCopyable = true
        )

        RegistrationInfoItem(
            label = "Registration date",
            value = formatDateTime(registrationResponse.registrationTimestamp)
        )
        RegistrationInfoItem(
            label = "Name",
            value = registrationResponse.fullName
        )
        RegistrationInfoItem(
            label = "Email",
            value = registrationResponse.email,
            isCopyable = true
        )
        RegistrationInfoItem(
            label = "Course",
            value = registrationResponse.course
        )
        RegistrationInfoItem(
            label = "Education Level",
            value = "Year ${registrationResponse.educationalLevel}"
        )
        RegistrationInfoItem(
            label = "Phone",
            value = registrationResponse.phoneNumber,
            isCopyable = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
           content ={
               Text("View My Registered Events")
           },
            onClick = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                    onRegisteredEvents()
                }
            },
        )
        Spacer(modifier = Modifier.height(50.dp))

    }
}

@Composable
fun RegistrationInfoItem(
    label: String,
    value: String,
    highlightValue: Boolean = false,
    isCopyable: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.4f)
        )

        if (isCopyable) {
            CopyableText(
                text = value,
                highlightText = highlightValue,
                toastMessage = "$label copied to clipboard",
                modifier = Modifier.weight(0.6f)
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (highlightValue) FontWeight.Bold else FontWeight.Normal,
                color = if (highlightValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.6f)
            )
        }
    }
}