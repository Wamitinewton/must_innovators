package com.newton.account.presentation.composables.account


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.auth_models.SocialMedia


@Composable
fun SocialMediaSection(socialMedia: SocialMedia?) {
    if (socialMedia == null || (socialMedia.github == null && socialMedia.linkedin == null && socialMedia.twitter == null)) return

    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Social Media",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Connect",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                socialMedia.github?.let { github ->
                    ElevatedButton(
                        onClick = { uriHandler.openUri(github) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF333333),
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Code,
                                contentDescription = "GitHub"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("GitHub")
                        }
                    }
                }

                socialMedia.linkedin?.let { linkedin ->
                    ElevatedButton(
                        onClick = { uriHandler.openUri(linkedin) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF0077B5),
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "LinkedIn"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("LinkedIn")
                        }
                    }
                }

                socialMedia.twitter?.let { twitter ->
                    ElevatedButton(
                        onClick = { uriHandler.openUri(twitter) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF1DA1F2),
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Chat,
                                contentDescription = "Twitter"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Twitter")
                        }
                    }
                }
            }
        }
    }
}
