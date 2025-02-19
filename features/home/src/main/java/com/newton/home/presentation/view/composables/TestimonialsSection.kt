package com.newton.home.presentation.view.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.newton.home.domain.models.Testimonial

@Composable
fun TestimonialsSection(
    testimonials: List<Testimonial>,
    configuration: Configuration
) {
    val socials = listOf(
        "https://github.com/piexie3",
        "https://x.com/emmanuel_dev2",
        "https://linkedin.com/emmanuelbett"
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(testimonials) { testimonial ->
            Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)) {
                Column(
                    modifier = Modifier
                        .width((configuration.screenWidthDp * .9).dp)
                        .padding(16.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1494959764136-6be9eb3c261e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fHBlcnNvbnxlbnwwfHwwfHx8MA%3D%3D",
                            contentDescription = "Author Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .clip(CircleShape)
                        )
                        Column {
                            Text(text = testimonial.author, fontWeight = FontWeight.Bold)
                            Text(text = testimonial.role, fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = testimonial.content, maxLines = 4)
                    Spacer(modifier = Modifier.height(8.dp))

//                    Row(horizontalArrangement =  Arrangement.spacedBy(8.dp)) {
//                        socials.forEach { social ->
//                            val domain = URL(social).host
//                            val domainIconUrl = "$domain/favicon.ico"
//                            AsyncImage(
//                                model = domainIconUrl,
//                                contentDescription = "Icon Image",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .height(30.dp)
//                                    .width(30.dp)
//                                    .clip(CircleShape)
//                            )
//                        }
//                    }

                }
            }
        }
    }
}
