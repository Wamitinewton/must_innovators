import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.domain.models.AddCommunityRequest
import com.newton.admin.domain.models.Session
import com.newton.admin.domain.models.Socials
import com.newton.admin.presentation.community.view.composable.CommunityHeader
import com.newton.admin.presentation.community.view.composable.CommunitySection
import com.newton.admin.presentation.community.view.composable.ContactField
import com.newton.admin.presentation.community.view.composable.DetailRow
import com.newton.admin.presentation.community.view.composable.LeadershipField
import com.newton.admin.presentation.community.view.composable.SessionDialog
import com.newton.admin.presentation.community.view.composable.SessionItem
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel
import com.newton.common_ui.ui.LoadingDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCommunityScreen(
    onBackPressed: () -> Unit,
    onSavePressed: (AddCommunityRequest) -> Unit,
    viewModel:CommunityViewModel
) {
    var tools by remember { mutableStateOf<String>("Kotlin , Swift , Flutter ,React Native") }
    val communityState by viewModel.communityState.collectAsState()
    // Sample default community data
    val defaultCommunity = AddCommunityRequest(
        co_lead = "Jane Smith",
        community_lead = "John Doe",
        description = "A community focused on mobile development and design patterns.",
        email = "community@example.com",
        founding_date = "2023-01-15",
        is_recruiting = true,
        name = "Mobile Dev Hub",
        phone_number = "+1 (555) 123-4567",
        secretary = "Alex Johnson",
        sessions = listOf(
            Session(
                day = "FRIDAY",
                start_time = "09:00",
                end_time = "11:00",
                meeting_type = "VIRTUAL",
                location = "Zoom",
            ),
            Session(
                day = "FRIDAY",
                start_time = "09:00",
                end_time = "11:00",
                meeting_type = "PHYSICAL",
                location = "Zoom",
            ),
        ),
        tech_stack = tools.split(","),
        social_media = listOf(
            Socials(
                platform = "Github",
                url = "github.com/mobiledevhub"
            ),
            Socials(
                platform = "Github",
                url = "twitter.com/mobiledevhub"
            ),
            Socials(
                platform = "Github",
                url = "linkedin.com/company/mobiledevhub"
            ),
        ),
    )
    var communityData by remember { mutableStateOf(defaultCommunity) }
    var isEditing by remember { mutableStateOf(false) }
    var showAddSessionDialog by remember { mutableStateOf(false) }
    var sessionToEdit by remember { mutableStateOf<Session?>(null) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Edit Community" else "Community Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            if (isEditing) Icons.Default.Close else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Cancel" else "Edit"
                        )
                    }

                    AnimatedVisibility(
                        visible = isEditing,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        IconButton(
                            onClick = {
                                onSavePressed(communityData)
                                isEditing = false
                            }
                        ) {
                            Icon(Icons.Default.Save, "Save")
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            if (isEditing) {
                FloatingActionButton(
                    onClick = {
                        sessionToEdit = null
                        showAddSessionDialog = true
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Session",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Community header with name and recruitment status
                CommunityHeader(
                    communityName = communityData.name,
                    isRecruiting = communityData.is_recruiting?:false,
                    isEditing = isEditing,
                    onNameChange = { communityData = communityData.copy(name = it) },
                    onRecruitingChange = { communityData = communityData.copy(is_recruiting = it) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Community description
                CommunitySection(
                    title = "About",
                    icon = Icons.Default.Description
                ) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = communityData.description,
                            onValueChange = { communityData = communityData.copy(description = it) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Description") },
                            maxLines = 5
                        )
                    } else {
                        Text(
                            text = communityData.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Leadership section
                CommunitySection(
                    title = "Leadership",
                    icon = Icons.Default.Group
                ) {
                    LeadershipField(
                        label = "Community Lead",
                        value = communityData.community_lead,
                        isEditing = isEditing,
                        onValueChange = { communityData = communityData.copy(co_lead = it) }
                    )

                    LeadershipField(
                        label = "Co-Lead",
                        value = communityData.co_lead,
                        isEditing = isEditing,
                        onValueChange = { communityData = communityData.copy(co_lead = it) }
                    )

                    LeadershipField(
                        label = "Secretary",
                        value = communityData.secretary,
                        isEditing = isEditing,
                        onValueChange = { communityData = communityData.copy(secretary = it) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contact information
                CommunitySection(
                    title = "Contact Information",
                    icon = Icons.Default.Call
                ) {
                    ContactField(
                        label = "Email",
                        value = communityData.email,
                        icon = Icons.Default.Email,
                        isEditing = isEditing,
                        keyboardType = KeyboardType.Email,
                        onValueChange = { communityData = communityData.copy(email = it) }
                    )

                    ContactField(
                        label = "Phone",
                        value = communityData.phone_number,
                        icon = Icons.Default.Phone,
                        isEditing = isEditing,
                        keyboardType = KeyboardType.Phone,
                        onValueChange = { communityData = communityData.copy(phone_number = it) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sessions section - NEW
                CommunitySection(
                    title = "Meeting Sessions",
                    icon = Icons.Default.Schedule
                ) {
                    if (communityData.sessions.isEmpty()) {
                        Text(
                            text = "No scheduled sessions",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    } else {
                        communityData.sessions.forEachIndexed { index, session ->
                            SessionItem(
                                session = session,
                                isEditing = isEditing,
                                onEditClick = {
                                    sessionToEdit = session
                                    showAddSessionDialog = true
                                },
                                onDeleteClick = {
                                    val newSessions = communityData.sessions.toMutableList()
                                    newSessions.removeAt(index)
                                    communityData = communityData.copy(sessions = newSessions)
                                }
                            )

                            if (index < communityData.sessions.size - 1) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tech stack
                CommunitySection(
                    title = "Tech Stack",
                    icon = Icons.Default.Settings
                ) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = tools,
                            onValueChange = { tools = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Tech Stack") }
                        )
                    } else {
                        Text(
                            text = communityData.tech_stack.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Community details
                CommunitySection(
                    title = "Community Details",
                    icon = Icons.Default.LocationOn
                ) {
                    DetailRow(
                        label = "Founding Date",
                        value = communityData.founding_date,
                        icon = Icons.Default.CalendarMonth,
                        isEditing = isEditing,
                        onValueChange = { communityData = communityData.copy(founding_date = it) }
                    )

//                    DetailRow(
//                        label = "Total Members",
//                        value = communityData.total_members.toString(),
//                        icon = Icons.Default.Person,
//                        isEditing = isEditing,
//                        keyboardType = KeyboardType.Number,
//                        onValueChange = {
//                            val newValue = it.toIntOrNull() ?: 0
//                            communityData = communityData.copy(total_members = newValue)
//                        }
//                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Social links
//                CommunitySection(
//                    title = "Social Media",
//                    icon = Icons.Default.LocationOn
//                ) {
//                    SocialField(
//                        label = "GitHub",
//                        value = communityData.socials.github,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(github = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "Twitter",
//                        value = communityData.socials.twitter,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(twitter = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "LinkedIn",
//                        value = communityData.socials.linkedin,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(linkedin = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "Discord",
//                        value = communityData.socials.discord,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(discord = it)
//                            )
//                        }
//                    )
//                }

                Spacer(modifier = Modifier.height(32.dp))

                // Save button (visible only in edit mode)
                AnimatedVisibility(
                    visible = isEditing,
                    enter = fadeIn() + expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Button(
                        onClick = {
                            onSavePressed(communityData)
                            isEditing = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Save Changes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (communityState.isLoading) {
            LoadingDialog()
        }
        // Session Dialog
        if (showAddSessionDialog) {
            SessionDialog(
                session = sessionToEdit,
                onDismiss = { showAddSessionDialog = false },
                onSave = { session ->
                    val newSessions = communityData.sessions.toMutableList()
                    if (sessionToEdit == null) {
                        // Add new session
                        newSessions.add(session)
                    } else {
                        // Update existing session
                        val index = newSessions.indexOf(sessionToEdit)
                        if (index != -1) {
                            newSessions[index] = session
                        }
                    }
                    communityData = communityData.copy(sessions = newSessions)
                    showAddSessionDialog = false
                    sessionToEdit = null
                }
            )
        }
    }
}
