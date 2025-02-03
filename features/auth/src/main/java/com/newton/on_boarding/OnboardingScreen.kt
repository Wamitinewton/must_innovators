package com.newton.on_boarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.newton.core.navigation.NavigationRoutes
import com.newton.meruinnovators.ui.theme.ThemeUtils
import com.newton.meruinnovators.ui.theme.ThemeUtils.themed

@Composable
fun OnboardingScreen(
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = com.newton.common_ui.R.drawable.innovation,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ThemeUtils.AppColors.Background.themed().copy(alpha = 0.2f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(ThemeUtils.AppColors.Background.themed())
                .padding(17.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Join the MUST Innovation community",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 18.sp
                    ),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Take your first step towards innovation today!\nConnect with fellow innovators, access resources, and collaborate on groundbreaking projects in our vibrant community.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                 Row(
                     modifier = Modifier
                         .fillMaxWidth(),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.Center
                 ) {
                     OutlinedButton(
                         onClick = {
                             navHostController.navigate(NavigationRoutes.SignupRoute.routes)
                         },
                         modifier = Modifier.height(50.dp),
                     ){
                         Text("Register")
                     }
                     Spacer(modifier = Modifier.width(20.dp))
                     OutlinedButton(
                         onClick = {},
                         modifier = Modifier.height(50.dp),
                     ){
                         Text("Login")
                     }
                 }
            }
        }
    }
}

