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
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*

@Composable
fun LogoCard(
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Partner Logo",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (partnersState.partnershipLogo != null) {
                val uri = partnersState.partnershipLogo
                uri?.let { safeUri ->
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(vertical = 10.dp)
                    ) {
                        CustomDynamicAsyncImage(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.small),
                            imageUrl = safeUri,
                            contentDescription = "Receipt Image",
                            contentScale = ContentScale.Crop
                        )
                        CloseButton(
                            modifier =
                            Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp),
                            onDismiss = { onEvent.invoke(AddPartnersEvent.LogoChange(null)) }
                        )
                    }
                }
            } else {
                Box(
                    modifier =
                    Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Add Logo",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                CustomButton(
                    onClick = {
                        onEvent.invoke(AddPartnersEvent.PickImage)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "Upload"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Upload Logo")
                }
            }
            partnersState.errors["logo"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
