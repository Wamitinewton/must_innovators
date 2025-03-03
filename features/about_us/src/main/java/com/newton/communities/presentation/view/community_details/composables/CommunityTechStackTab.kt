package com.newton.communities.presentation.view.community_details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CommunityTechStackTab(techStack: List<String>) {
    val animatedTechItems = remember { mutableListOf<String>() }

    val languages = listOf("Java", "Kotlin", "Python", "JavaScript", "TypeScript", "Swift", "C++", "C#", "Go")
    val frameworks = listOf("Android", "React", "Angular", "Vue", "Flutter", "Spring", "Django", "Express", "TensorFlow")
    val tools = listOf("Git", "Docker", "Kubernetes", "AWS", "Azure", "Firebase", "MongoDB", "PostgreSQL")

    val categorizedTech = mapOf(
        "Languages" to techStack.filter { it in languages },
        "Frameworks" to techStack.filter { it in frameworks },
        "Tools & Platforms" to techStack.filter { it in tools },
        "Other" to techStack.filterNot { it in languages + frameworks + tools }
    ).filterValues { it.isNotEmpty() }

    LaunchedEffect(techStack) {
        animatedTechItems.clear()
        techStack.forEachIndexed { index, tech ->
            delay(100L * index)
            animatedTechItems.add(tech)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tech Stack",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                append("This community specializes in ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)) {
                    append("${techStack.size} technologies")
                }
                append(" across various domains.")
            },
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (categorizedTech.isEmpty()) {
            Text(
                text = "No technologies available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            categorizedTech.forEach { (category, technologies) ->
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(technologies.size) { index ->
                        val tech = technologies[index]
                        AnimatedVisibility(
                            visible = animatedTechItems.contains(tech),
                            enter = fadeIn() + expandVertically(),
                            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
                        ) {
                            TechStackChip(tech)
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Technology Focus Areas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val categories = categorizedTech.map { it.key to it.value.size }
                    val total = categories.sumOf { it.second }

                    categories.forEach { (category, count) ->
                        if (count > 0) {
                            val percentage = count.toFloat() / total.toFloat()
                            TechFocusItem(
                                category = category,
                                percentage = percentage,
                                modifier = Modifier.weight(percentage * 3f + 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TechFocusItem(category: String, percentage: Float, modifier: Modifier = Modifier) {
    val displayPercentage = (percentage * 100).toInt()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(4.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .drawBehind {
                    drawArc(
                        color = Color(0xff53B175),
                        startAngle = -90f,
                        sweepAngle = 360f * percentage,
                        useCenter = true
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$displayPercentage%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TechStackChip(technology: String) {
    val techColor = remember {
        when (technology) {
            "Android" -> Color(0xFF3DDC84)
            "Kotlin" -> Color(0xFF7F52FF)
            "Java" -> Color(0xFFf89820)
            "Python" -> Color(0xFF3776AB)
            "JavaScript" -> Color(0xFFF7DF1E)
            "React" -> Color(0xFF61DAFB)
            "Flutter" -> Color(0xFF02569B)
            "Firebase" -> Color(0xFFFFCA28)
            "AWS" -> Color(0xFFFF9900)
            else -> Color(0xff53B175)
        }
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = techColor.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, techColor.copy(alpha = 0.5f)),
        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(techColor, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = technology,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}