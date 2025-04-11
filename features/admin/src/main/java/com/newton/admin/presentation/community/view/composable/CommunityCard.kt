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
package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.commonUi.ui.FlowRow
import com.newton.network.domain.models.aboutUs.*

@Composable
fun CommunityCard(
    community: Community,
    onSelectedCommunity: () -> Unit
) {
    CustomCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onSelectedCommunity()
                },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            brush =
                                Brush.horizontalGradient(
                                    colors =
                                        listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary
                                        )
                                )
                        )
                        .padding(20.dp)
            ) {
                Text(
                    text = community.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    LabelText(text = "Tech Stack")
                    TechStackItem(community.techStack)
                }

                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    LabelText(text = "Email")
                    DataText(text = community.email)
                }
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    LabelText(text = "Description")
                    Text(
                        text = community.description,
                        color = Color(0xFF334155),
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Founding Date
                Column {
                    LabelText(text = "Founded")
                    DataText(text = community.foundingDate)
                }
            }
        }
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        letterSpacing = 0.7.sp,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun DataText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
        fontSize = 16.sp
    )
}

@Composable
fun TechStackItem(techList: List<String>) {
    FlowRow(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
        techList.forEach { tech ->
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = tech,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
