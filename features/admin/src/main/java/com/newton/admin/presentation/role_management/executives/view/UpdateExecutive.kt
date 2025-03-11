package com.newton.admin.presentation.role_management.executives.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.newton.admin.presentation.events.view.management.rememberScaffoldState
import com.newton.admin.presentation.role_management.executives.viewModel.ExecutiveViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Models
data class Executive(
    val id: Int? = null,
    val name: String,
    val position: String,
    val bio: String,
    val email: String,
    val communityId: Int? = null,
    val clubId: Int? = null
)

data class Community(
    val id: Int,
    val name: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val photo:String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExecutiveScreen(
    navController: NavController,
    executiveId: Int? = null,
    viewModel: ExecutiveViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val execState by viewModel.execState.collectAsState()

    // Form state
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("Lead") } // Default position
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Bottom sheet state
    val modalBottomSheetState = rememberModalBottomSheetState()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var showBottomSheet by remember { mutableStateOf(false) }

    // Load executive data if updating
    LaunchedEffect(executiveId) {
        executiveId?.let {
            viewModel.loadExecutive(it)
        }
    }

    // Update form values when executive data changes
    LaunchedEffect(execState.executiveState) {
        execState.executiveState?.let { executive ->
            name = executive.name
            position = executive.position
            bio = executive.bio
            email = executive.email
        }
    }
    LaunchedEffect(execState.errorMessage, execState.successMessage) {
        execState.errorMessage?.let { message ->
            scope.launch {
                scaffoldState.snackBarHostState.showSnackbar(message)
                viewModel.clearMessages()
            }
        }

        execState.successMessage?.let { message ->
            scope.launch {
                scaffoldState.snackBarHostState.showSnackbar(message)
                viewModel.clearMessages()
            }
        }
    }

    // Handle bottom sheet opening
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            viewModel.loadUsers { loadedUsers ->
                users = loadedUsers
                scope.launch {
                    modalBottomSheetState.show()
                }
            }
            showBottomSheet = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (executiveId != null) "Update Executive" else "Add Executive") },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(Icons.Default.PersonSearch, contentDescription = "Select User")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (execState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.weight(1f),
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                            trailingIcon = {
                                if (execState.isSearching) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    IconButton(
                                        onClick = {
                                            if (email.isNotEmpty()) {
                                                viewModel.searchExecutiveByEmail(email)
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Search, contentDescription = "Search")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                    }
                }

                item {
                    OutlinedTextField(
                        value = position,
                        onValueChange = { position = it },
                        label = { Text("Position") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text("Bio") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Description,
                                contentDescription = null
                            )
                        },
                        minLines = 3,
                        maxLines = 5
                    )
                }

                item {
                    Text(
                        text = "Community",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    CommunityDropdown(
                        communities = execState.communities,
                        selectedCommunity = execState.selectedCommunity,
                        onCommunitySelected = { viewModel.setSelectedCommunity(it) }
                    )
                }

                // Show selected user card if available
                execState.selectedUser?.let { user ->
                    item {
                        SelectedUserCard(user = user)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.saveExecutive(
                                name = name,
                                position = position,
                                bio = bio,
                                email = email,
                                onSuccess = {
                                    scope.launch {
                                        delay(1000) // Give time for the success message to show
                                        navController.popBackStack()
                                    }
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = name.isNotEmpty() && email.isNotEmpty() &&
                                position.isNotEmpty() && bio.isNotEmpty() &&
                                execState.selectedCommunity != null
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (executiveId != null) "UPDATE EXECUTIVE" else "ADD EXECUTIVE",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {},
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                sheetState = modalBottomSheetState,
            ) {
                UserSelectionBottomSheet(
                    users = users,
                    onUserSelected = { user ->
                        name = user.name
                        email = user.email
                        viewModel.setSelectedUser(user)
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun CommunityDropdown(
    communities: List<Community>,
    selectedCommunity: Community?,
    onCommunitySelected: (Community) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedCommunity?.name ?: "Select A commun",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Community")
                }
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            communities.forEach { community ->
                DropdownMenuItem(
                    onClick = {
                        onCommunitySelected(community)
                        expanded = false
                    },
                    text = {
                        Text(text = community.name)
                    },
                )
            }
        }
    }
}

@Composable
fun UserSelectionBottomSheet(
    users: List<User>,
    onUserSelected: (User) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select User",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onClick = { onUserSelected(user) }
                )
            }
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.take(1),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun SelectedUserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Selected User",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.take(1),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
//
//// Main Activity use case
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyAppTheme {
//                Surface(color = MaterialTheme.colors.background) {
//                    val navController = rememberNavController()
//
//                    NavHost(navController = navController, startDestination = "executiveUpdate") {
//                        composable("executiveUpdate") {
//                            ExecutiveUpdateScreen(navController = navController)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}