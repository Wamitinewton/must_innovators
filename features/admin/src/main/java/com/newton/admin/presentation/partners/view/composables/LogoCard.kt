package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.events.view.composables.CloseButton
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.CustomCard
import com.newton.common_ui.ui.CustomDynamicAsyncImage

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
            modifier = Modifier
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(vertical = 10.dp)
                    ) {
                        CustomDynamicAsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.small),
                            imageUrl = safeUri,
                            contentDescription = "Receipt Image",
                            contentScale = ContentScale.Crop
                        )
                        CloseButton(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp),
                            onDismiss = { onEvent.invoke(AddPartnersEvent.LogoChange(null)) }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
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
                    },
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