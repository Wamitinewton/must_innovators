package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.CustomButton

@Composable
fun AddPartnerForm(partnersState:AddPartnersState,onEvent: (AddPartnersEvent)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BasicInfo(
            partnersState = partnersState,
            onEvent = onEvent
        )
        LogoCard(
            partnersState = partnersState,
            onEvent = onEvent
        )
        ContactInfo(
            partnersState = partnersState,
            onEvent = onEvent
        )
        PartnershipDetails(
            partnersState = partnersState,
            onEvent = onEvent
        )
        CollaborationDetails(
            partnersState = partnersState,
            onEvent = onEvent
        )
        CustomButton(
            onClick = {
                onEvent.invoke(AddPartnersEvent.AddPartners)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Save"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Save Partner",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}