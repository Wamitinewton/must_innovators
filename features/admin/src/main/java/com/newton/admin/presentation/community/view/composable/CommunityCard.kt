package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.community.states.UpdateCommunityState
import com.newton.common_ui.ui.CustomCard
import com.newton.core.domain.models.about_us.Community


@Composable
fun CommunityCard(
    community:Community,
    onSelectedCommunity:(Community)->Unit
) {
    CustomCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{
                onSelectedCommunity(community)
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Gradient Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF3A7BD5),
                                Color(0xFF00D2FF)
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

            // Card Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Tech Stack
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    LabelText(text = "Tech Stack")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TechStackItem(community.techStack)
                    }
                }

                // Email
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    LabelText(text = "Email")
                    DataText(text = community.email)
                }

                // Description
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
        color = Color(0xFF94A3B8),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.7.sp,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun DataText(text: String) {
    Text(
        text = text,
        color = Color(0xFF334155),
        fontSize = 16.sp
    )
}

@Composable
fun TechStackItem(techList: List<String>) {
    techList.forEach { tech ->
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFEEF2FF))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = tech,
                color = Color(0xFF4F46E5),
                fontSize = 14.sp
            )
        }
    }
}
