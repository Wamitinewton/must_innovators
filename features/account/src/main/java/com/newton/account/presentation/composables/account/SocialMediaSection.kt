package com.newton.account.presentation.composables.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.core.domain.models.authModels.*

@Composable
fun SocialMediaSection(socialMedia: SocialMedia?) {
    if (socialMedia == null || (socialMedia.github == null && socialMedia.linkedin == null && socialMedia.twitter == null)) return

    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier =
            Modifier
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                socialMedia.github?.let { github ->
                    SocialButton(
                        url = github,
                        iconUrl = "https://github.com/favicon.ico",
                        label = "GitHub",
                        backgroundColor = Color(0xFF333333)
                    )
                }

                socialMedia.linkedin?.let { linkedin ->
                    SocialButton(
                        url = linkedin,
                        iconUrl = "https://www.linkedin.com/favicon.ico",
                        label = "LinkedIn",
                        backgroundColor = Color(0xFF0077B5)
                    )
                }

                socialMedia.twitter?.let { twitter ->
                    SocialButton(
                        url = twitter,
                        iconUrl = "https://twitter.com/favicon.ico",
                        label = "Twitter",
                        backgroundColor = Color(0xFF1DA1F2)
                    )
                }
            }
        }
    }
}

@Composable
fun SocialButton(
    url: String,
    iconUrl: String,
    label: String,
    backgroundColor: Color
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    ElevatedButton(
        onClick = { uriHandler.openUri(url) },
        shape = RoundedCornerShape(8.dp),
        colors =
        ButtonDefaults.elevatedButtonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        AsyncImage(
            model =
            ImageRequest.Builder(context)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = "$label icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
