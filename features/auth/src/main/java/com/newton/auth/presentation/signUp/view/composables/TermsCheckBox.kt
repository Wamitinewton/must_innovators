package com.newton.auth.presentation.signUp.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsCheckboxRow(
     isChecked: Boolean,
     onTermsClicked:()->Unit,
     onPolicyClicked:()->Unit,
     onCheckedClicked:()->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.dp,
                    color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(4.dp)
                )
                .background(
                    color = if (isChecked) MaterialTheme.colorScheme.primaryContainer else Color.White,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(onClick = onCheckedClicked),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = buildAnnotatedString {
                append("I agree to the ")

                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    Box(
                        modifier = Modifier.clickable(onClick = onTermsClicked)
                    ){
                        append("Terms of Service")
                    }

                }

                append(" and acknowledge the ")

                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    Box(modifier = Modifier.clickable(onClick = onPolicyClicked)){
                        append("Privacy Policy")
                    }
                }
            },
            fontSize = 14.sp,
            color = Color(0xFF616161)
        )
    }
}