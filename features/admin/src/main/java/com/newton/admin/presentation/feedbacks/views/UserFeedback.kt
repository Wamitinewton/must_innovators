package com.newton.admin.presentation.feedbacks.views




//
//// Data Models
//data class User(
//    val id: String,
//    val name: String,
//    val email: String,
//    val phone: String,
//    val profilePictureUrl: String
//)
//
//
//// ViewModel
//class FeedbackViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.Loading)
//    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()
//
//    private val _user = MutableStateFlow<User?>(null)
//    val user: StateFlow<User?> = _user.asStateFlow()
//
//    fun loadFeedback(feedbackId: String) {
//        viewModelScope.launch {
//            _uiState.value = FeedbackUiState.Loading
//
//            // Simulate network delay
//            delay(1500)
//
//            // In a real app, we would fetch from API or database
//            val mockUser = User(
//                id = "user123",
//                name = "Alex Johnson",
//                email = "alex.johnson@example.com",
//                phone = "+1 (555) 123-4567",
//                profilePictureUrl = "https://example.com/profile.jpg"
//            )
//
//            val mockFeedback = Feedback(
//                id = feedbackId,
//                userId = mockUser.id,
//                content = "The app is great, but I'm having trouble with the notification settings. Sometimes they don't seem to update immediately after I change them.",
//                status = FeedbackStatus.REVIEWED,
//                submissionTimestamp = System.currentTimeMillis() - 86400000 // 1 day ago
//            )
//
//            _user.value = mockUser
//            _uiState.value = FeedbackUiState.Success(mockFeedback)
//        }
//    }
//}
//
//// UI States
//sealed class FeedbackUiState {
//    object Loading : FeedbackUiState()
//    data class Success(val feedback: Feedback) : FeedbackUiState()
//    data class Error(val message: String) : FeedbackUiState()
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FeedbackScreen(
//    feedbackId: String,
//    viewModel: FeedbackViewModel = viewModel(),
//    navigateBack: () -> Unit
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    val user by viewModel.user.collectAsState()
//
//    // Load feedback when the screen is first composed
//    LaunchedEffect(feedbackId) {
//        viewModel.loadFeedback(feedbackId)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Feedback Details") },
//                navigationIcon = {
//                    IconButton(onClick = navigateBack) {
//                        Icon(
//                            painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
//                            contentDescription = "Back"
//                        )
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .background(MaterialTheme.colorScheme.background)
//        ) {
//            when (uiState) {
//                is FeedbackUiState.Loading -> {
//                    LoadingState()
//                }
//                is FeedbackUiState.Success -> {
//                    val feedback = (uiState as FeedbackUiState.Success).feedback
//                    FeedbackContent(feedback = feedback, user = user)
//                }
//                is FeedbackUiState.Error -> {
//                    ErrorState(message = (uiState as FeedbackUiState.Error).message)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun LoadingState() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator(
//            modifier = Modifier.semantics { contentDescription = "Loading" }
//        )
//    }
//}
//
//@Composable
//fun ErrorState(message: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            painter = painterResource(id = android.R.drawable.ic_dialog_alert),
//            contentDescription = "Error",
//            tint = MaterialTheme.colorScheme.error,
//            modifier = Modifier.size(48.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Something went wrong",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.error
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = message,
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}
//
//@Composable
//fun FeedbackContent(feedback: Feedback, user: User?) {
//    var isVisible by remember { mutableStateOf(false) }
//
//    LaunchedEffect(feedback) {
//        isVisible = true
//    }
//
//    AnimatedVisibility(
//        visible = isVisible,
//        enter = fadeIn(),
//        exit = fadeOut()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // User Information Section
//            user?.let { UserInfoSection(it) }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Feedback Status Section
//            FeedbackStatusSection(feedback)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Feedback Content Section
//            FeedbackSubmissionSection(feedback)
//        }
//    }
//}
//
//@Composable
//fun UserInfoSection(user: User) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight(),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Profile Picture
//            Box(
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.surfaceVariant)
//            ) {
//                val painter = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        .data(user.profilePictureUrl)
//                        .crossfade(true)
//                        .build()
//                )
//
//                Image(
//                    painter = painter,
//                    contentDescription = "Profile Picture of ${user.name}",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//
//                // Fallback if image fails to load
////                if (painter.state == AsyncImagePainter.State.Error) {
////                    Box(
////                        modifier = Modifier
////                            .fillMaxSize()
////                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
////                        contentAlignment = Alignment.Center
////                    ) {
////                        Text(
////                            text = user.name.first().toString(),
////                            style = MaterialTheme.typography.headlineLarge,
////                            color = MaterialTheme.colorScheme.primary
////                        )
////                    }
////                }
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // User Details
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = user.name,
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        fontWeight = FontWeight.Bold
//                    ),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Email,
//                        contentDescription = "Email",
//                        tint = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.size(16.dp)
//                    )
//
//                    Spacer(modifier = Modifier.width(4.dp))
//
//                    Text(
//                        text = user.email,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Phone,
//                        contentDescription = "Phone",
//                        tint = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.size(16.dp)
//                    )
//
//                    Spacer(modifier = Modifier.width(4.dp))
//
//                    Text(
//                        text = user.phone,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun FeedbackStatusSection(feedback: Feedback) {
//    val statusColor = when (feedback.status) {
//        FeedbackStatus.PENDING -> MaterialTheme.colorScheme.tertiary
//        FeedbackStatus.REVIEWED -> MaterialTheme.colorScheme.secondary
//        FeedbackStatus.RESOLVED -> MaterialTheme.colorScheme.primary
//    }
//
//    val statusText = when (feedback.status) {
//        FeedbackStatus.PENDING -> "Pending"
//        FeedbackStatus.REVIEWED -> "Reviewed"
//        FeedbackStatus.RESOLVED -> "Resolved"
//    }
//
//    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault()) }
//    val formattedDate = remember(feedback.submissionTimestamp) {
//        dateFormat.format(Date(feedback.submissionTimestamp))
//    }
//
//    val progressValue = when (feedback.status) {
//        FeedbackStatus.PENDING -> 0.33f
//        FeedbackStatus.REVIEWED -> 0.66f
//        FeedbackStatus.RESOLVED -> 1f
//    }
//
//    val animatedProgress by animateFloatAsState(targetValue = progressValue)
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Status",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//
//                Surface(
//                    shape = RoundedCornerShape(16.dp),
//                    color = statusColor.copy(alpha = 0.1f)
//                ) {
//                    Row(
//                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(8.dp)
//                                .clip(CircleShape)
//                                .background(statusColor)
//                        )
//
//                        Spacer(modifier = Modifier.width(4.dp))
//
//                        Text(
//                            text = statusText,
//                            style = MaterialTheme.typography.labelMedium,
//                            color = statusColor
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            LinearProgressIndicator(
//                progress = { animatedProgress },
//                modifier = Modifier.fillMaxWidth(),
//                color = statusColor,
//                trackColor = MaterialTheme.colorScheme.surfaceVariant
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Default.AccessTime,
//                    contentDescription = "Submission Time",
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.size(16.dp)
//                )
//
//                Spacer(modifier = Modifier.width(4.dp))
//
//                Text(
//                    text = "Submitted on $formattedDate",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun FeedbackSubmissionSection(feedback: Feedback) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Your Feedback",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.onSurface
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Surface(
//                shape = RoundedCornerShape(8.dp),
//                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
//            ) {
//                Text(
//                    text = feedback.content,
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun FeedbackScreenPreview() {
//    MaterialTheme {
//        FeedbackScreen(
//            feedbackId = "feedback123",
//            navigateBack = {}
//        )
//    }
//}
