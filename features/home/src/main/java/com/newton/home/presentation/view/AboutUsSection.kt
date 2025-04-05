package com.newton.home.presentation.view

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import coil3.compose.*

@Composable
fun AboutUsSection(
    configuration: Configuration,
    onClick: () -> Unit
) {
    Card(
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.width((configuration.screenWidthDp * .4).dp)
                ) {
                    AsyncImage(
                        model = com.newton.commonUi.R.drawable.innovation,
                        contentDescription = "Innovators Image",
                        contentScale = ContentScale.Crop,
                        modifier =
                        Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Club Bio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        text = "Click here to view our communities and the club background",
                        maxLines = 7
                    )
                }
            }
        }
    }
}
