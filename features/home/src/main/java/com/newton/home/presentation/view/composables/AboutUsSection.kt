package com.newton.home.presentation.view.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.newton.home.domain.models.AboutUs

@Composable
fun AboutUsSection(
    aboutUs: AboutUs,
    configuration: Configuration,
    onClick:()->Unit
) {
    Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp), onClick = onClick) {
        Box(modifier = Modifier.padding(8.dp).height(200.dp)){
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.width((configuration.screenWidthDp*.4).dp)
                ){
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1736821481668-2cb07ceed73b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHw3NXx8fGVufDB8fHx8fA%3D%3D",
                        contentDescription = "Innovators Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = aboutUs.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = aboutUs.description, maxLines = 7)
                }
            }
        }

    }
}