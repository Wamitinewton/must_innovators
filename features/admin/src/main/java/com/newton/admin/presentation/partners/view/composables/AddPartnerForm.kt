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
package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*

@Composable
fun AddPartnerForm(partnersState: AddPartnersState, onEvent: (AddPartnersEvent) -> Unit) {
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
