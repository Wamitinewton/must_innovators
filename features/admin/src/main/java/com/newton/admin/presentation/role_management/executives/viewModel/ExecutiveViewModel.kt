package com.newton.admin.presentation.role_management.executives.viewModel

// ExecutiveViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.data.mappers.User
import com.newton.admin.presentation.role_management.executives.states.ExecutiveState
import com.newton.admin.presentation.role_management.executives.view.Community
import com.newton.admin.presentation.role_management.executives.view.Executive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ExecutiveViewModel : ViewModel() {
    private val _execState = MutableStateFlow(ExecutiveState())
    val execState: StateFlow<ExecutiveState> = _execState.asStateFlow()

    private val client = OkHttpClient()
    private val baseUrl = "https://7h3pspsq-8000.uks1.devtunnels.ms/"

    init {
        loadCommunities()
    }

    fun loadExecutive(id: Int) {
        viewModelScope.launch {
            _execState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }


            try {
                val request = Request.Builder()
                    .url("$baseUrl/executives/$id/")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody =
                        response.body?.string() ?: throw IOException("Empty response")
                    val jsonObject = JSONObject(responseBody)

                    _execState.update {
                        it.copy(
                            executiveState = Executive(
                                id = jsonObject.optInt("id"),
                                name = jsonObject.getString("name"),
                                position = jsonObject.getString("position"),
                                bio = jsonObject.getString("bio"),
                                email = jsonObject.getString("email"),
                                communityId = jsonObject.optInt("community").takeIf { it > 0 },
                                clubId = jsonObject.optInt("club").takeIf { it > 0 }
                            )

                        )
                    }

                    // Find the corresponding community
                    _execState.value.executiveState?.communityId?.let { communityId ->
                        _execState.update { it ->
                            it.copy(
                                selectedCommunity = _execState.value.communities.find { it.id == communityId }
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _execState.update {
                    it.copy(
                        errorMessage = "Failed to load executive: ${e.message}"
                    )
                }
            } finally {
                _execState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun searchExecutiveByEmail(email: String) {
        viewModelScope.launch {
            _execState.update {
                it.copy(
                    isSearching = true,
                    errorMessage = null
                )
            }


            try {
                val request = Request.Builder()
                    .url("$baseUrl/executives/search/?email=$email")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody =
                        response.body?.string() ?: throw IOException("Empty response")
                    val jsonArray = JSONArray(responseBody)

                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)

                        _execState.update {
                            it.copy(
                                executiveState = Executive(
                                    id = jsonObject.optInt("id"),
                                    name = jsonObject.getString("name"),
                                    position = jsonObject.getString("position"),
                                    bio = jsonObject.getString("bio"),
                                    email = jsonObject.getString("email"),
                                    communityId = jsonObject.optInt("community").takeIf { it > 0 },
                                    clubId = jsonObject.optInt("club").takeIf { it > 0 }
                                )
                            )
                        }


                        // Find the corresponding community
                        _execState.value.executiveState?.communityId?.let { communityId ->
                            _execState.update {
                                it.copy(
                                    selectedCommunity = _execState.value.communities.find { it.id == communityId }
                                )
                            }
                        }
                        _execState.update {
                            it.copy(
                                successMessage = "Executive found! Information loaded."
                            )
                        }
                    } else {
                        _execState.update {
                            it.copy(
                                successMessage = "No executive found with this email. Creating new."
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _execState.update {
                    it.copy(
                        errorMessage = "Failed to search for executive: ${e.message}"
                    )
                }
            } finally {
                _execState.update {
                    it.copy(
                        isSearching = false
                    )
                }

            }
        }
    }

    fun loadCommunities() {
        viewModelScope.launch {
            _execState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }


            try {
                val request = Request.Builder()
                    .url("$baseUrl/communities/")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody =
                        response.body?.string() ?: throw IOException("Empty response")
                    val jsonArray = JSONArray(responseBody)

                    val loadedCommunities = mutableListOf<Community>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        loadedCommunities.add(
                            Community(
                                id = jsonObject.getInt("id"),
                                name = jsonObject.getString("name")
                            )
                        )
                    }
                    _execState.update {
                        it.copy(
                            communities = loadedCommunities
                        )
                    }


                    if (_execState.value.communities.isNotEmpty() && _execState.value.selectedCommunity == null) {
                        _execState.update {
                            it.copy(
                                selectedCommunity = _execState.value.communities.first()
                            )
                        }

                    }
                }
            } catch (e: Exception) {
                _execState.update {
                    it.copy(
                        errorMessage = "Failed to load communities: ${e.message}"
                    )
                }
            } finally {
                _execState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadUsers(onUsersLoaded: (List<User>) -> Unit) {
        viewModelScope.launch {
            _execState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }


            try {
                val request = Request.Builder()
                    .url("$baseUrl/users/")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody =
                        response.body?.string() ?: throw IOException("Empty response")
                    val jsonArray = JSONArray(responseBody)

                    val users = mutableListOf<User>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        users.add(
                            User(
                                id = jsonObject.getInt("id"),
                                name = jsonObject.optString("name", "Unknown"),
                                email = jsonObject.getString("email"),
                                photo = ""
                            )
                        )
                    }

                    onUsersLoaded(users)
                }
            } catch (e: Exception) {
                _execState.update {
                    it.copy(
                        errorMessage = "Failed to load users: ${e.message}"
                    )
                }
            } finally {
                _execState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setSelectedCommunity(community: Community) {
        _execState.update {
            it.copy(
                selectedCommunity = community
            )
        }

    }

    fun setSelectedUser(user: User) {
        _execState.update {
            it.copy(
                selectedUser = user
            )
        }
        searchExecutiveByEmail(user.email)
    }

    fun saveExecutive(
        name: String,
        position: String,
        bio: String,
        email: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _execState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            if (_execState.value.selectedCommunity == null) {
                _execState.update {
                    it.copy(
                        errorMessage = "Please select a community",
                        isLoading = false
                    )
                }

                return@launch
            }

            try {
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", name)
                    .addFormDataPart("position", position)
                    .addFormDataPart("bio", bio)
                    .addFormDataPart("email", email)
                    .addFormDataPart(
                        "community",
                        _execState.value.selectedCommunity!!.id.toString()
                    )
                    .addFormDataPart("club", "2") // Default club ID from your sample request

                val url = if (_execState.value.executiveState?.id != null) {
                    "$baseUrl/executives/${_execState.value.executiveState!!.id}/"
                } else {
                    "$baseUrl/executives/"
                }

                val request = Request.Builder()
                    .url(url)
                    .method(
                        if (_execState.value.executiveState?.id != null) "PUT" else "POST",
                        builder.build()
                    )
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody =
                        response.body?.string() ?: throw IOException("Empty response")
                    val jsonObject = JSONObject(responseBody)

                    _execState.update {
                        it.copy(
                            executiveState = Executive(
                                id = jsonObject.optInt("id"),
                                name = jsonObject.getString("name"),
                                position = jsonObject.getString("position"),
                                bio = jsonObject.getString("bio"),
                                email = jsonObject.getString("email"),
                                communityId = jsonObject.optInt("community").takeIf { it > 0 },
                                clubId = jsonObject.optInt("club").takeIf { it > 0 }
                            ),
                            successMessage = "Executive saved successfully"
                        )
                    }
                    onSuccess()
                }
            } catch (e: Exception) {
                _execState.update {
                    it.copy(
                        errorMessage = "Failed to save executive: ${e.message}"
                    )
                }
            } finally {
                _execState.update {
                    it.copy(
                        isLoading = false
                    )
                }

            }
        }
    }

    fun clearMessages() {
        _execState.update {
            it.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }
}
