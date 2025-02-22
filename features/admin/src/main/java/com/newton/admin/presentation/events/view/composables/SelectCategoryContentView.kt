package com.newton.admin.presentation.events.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.common_ui.ui.RowWrapper


@Composable
fun SelectCategoriesContentView(
    categories: List<String>,
    onEvent: (AddEventEvents) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories){category ->
            CategoryItemView(
                category,
                onEvent = { onEvent.invoke(AddEventEvents.ChangedCategory(category = category)) }
            )
        }
    }

}

@Composable
fun CategoryItemView(category: String, onEvent: () -> Unit) {
    RowWrapper(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground.copy(0.3f)).padding(vertical = 12.dp, horizontal = 16.dp),
        onClick = { onEvent.invoke() }) {
        Text(text = category, style = MaterialTheme.typography.titleMedium)
    }
}
