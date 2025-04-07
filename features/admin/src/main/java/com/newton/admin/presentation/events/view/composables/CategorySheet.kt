package com.newton.admin.presentation.events.view.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.common_ui.ui.ColumnWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySheet(onEvent: (AddEventEvents)->Unit,viewModel:AddEventViewModel) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(AddEventEvents.Sheet(shown = false)) }
    ) {
        ColumnWrapper(modifier = Modifier.heightIn(max = 640.dp)) {
            Text(
                "Select Category",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            SelectCategoriesContentView(
                onEvent = onEvent,
                categories = viewModel.categories
            )
            Spacer(Modifier.size(24.dp))
        }
    }
}