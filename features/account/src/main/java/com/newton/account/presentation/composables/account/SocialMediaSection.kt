package com.newton.account.presentation.composables.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.newton.core.domain.models.auth_models.SocialMedia

@Composable
fun SocialMediaSection(socialMedia: SocialMedia?) {
    if (socialMedia == null || (socialMedia.github == null && socialMedia.linkedin == null && socialMedia.twitter == null)) return


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
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
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
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