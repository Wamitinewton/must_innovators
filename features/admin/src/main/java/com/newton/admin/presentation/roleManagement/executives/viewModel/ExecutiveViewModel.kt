package com.newton.admin.presentation.roleManagement.executives.viewModel

import androidx.lifecycle.*
import com.newton.admin.data.mappers.*
import com.newton.admin.data.mappers.UserDataMappers.toDomainList
import com.newton.admin.presentation.roleManagement.executives.events.*
import com.newton.admin.presentation.roleManagement.executives.states.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*
import kotlin.collections.set

@HiltViewModel
class ExecutiveViewModel
@Inject
constructor(
    private val repository: AdminRepository
) : ViewModel() {
    private val _execState = MutableStateFlow(ExecutiveState())
    val execState: StateFlow<ExecutiveState> = _execState.asStateFlow()
    private val _userState = MutableStateFlow(ExecutiveUsersState())
    val userState: StateFlow<ExecutiveUsersState> = _userState.asStateFlow()

    fun handleEvents(event: ExecutiveEvents) {
        when (event) {
            is ExecutiveEvents.BioChanged -> _execState.update { it.copy(bio = event.bio) }
            is ExecutiveEvents.IsSearching -> _execState.update { it.copy(isSearching = event.searching) }
            ExecutiveEvents.LoadUsers -> loadUsers()
            is ExecutiveEvents.PositionChanged -> _execState.update { it.copy(position = event.position) }
            is ExecutiveEvents.SelectedUserChange -> _execState.update { it.copy(selectedUser = event.user) }
            is ExecutiveEvents.ShowBottomSheet -> _execState.update { it.copy(showBottomSheet = event.shown) }
            is ExecutiveEvents.ShowPositionDropdown -> _execState.update { it.copy(showPosition = event.shown) }
            ExecutiveEvents.AddExecutive -> addExecutive()
            is ExecutiveEvents.Expanded -> _execState.update { it.copy(expanded = event.expanded) }
            is ExecutiveEvents.SearchQuery -> _userState.update { it.copy(searchQuery = event.query) }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            repository.getAllUsers(true).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let { error ->
                            _userState.update { it.copy(getUsersError = error) }
                        }
                    }

                    is Resource.Loading -> {
                        _userState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        val userData = result.data?.toDomainList()
                        userData?.let { users ->
                            _userState.update {
                                it.copy(
                                    isLoading = false,
                                    getUsersError = null,
                                    users = users
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (_execState.value.bio.isBlank()) {
            errors["bio"] = "Users bio is required"
        }
        if (_execState.value.position == null) {
            errors["position"] = "Users position cannot be empty"
        }
        if (_execState.value.selectedUser == null) {
            errors["user"] = "Select the user to add as an Executive"
        }
        _execState.value = _execState.value.copy(errors = errors)
        return errors.isEmpty()
    }

    private fun toDefault() {
        _execState.value = ExecutiveState()
        _userState.value = ExecutiveUsersState()
    }

    private fun addExecutive() {
        if (validate()) {
            val executive =
                ExecutiveRequest(
                    userId = _execState.value.selectedUser!!.id,
                    position = _execState.value.position!!,
                    bio = _execState.value.bio
                )
            viewModelScope.launch {
                repository.updateExecutive(executive).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            result.message?.let {
                                _execState.value = _execState.value.copy(errorMessage = it)
                            }
                        }

                        is Resource.Loading -> {
                            _execState.value = _execState.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            toDefault()
                        }
                    }
                }
            }
        }
    }

    fun getSearchedUser(): List<User> {
        val query = _userState.value.searchQuery.lowercase()
        return _userState.value.users.filter { user ->
            val matchesSearch =
                query.isEmpty() ||
                    user.name.lowercase().contains(query) ||
                    user.email.lowercase().contains(query)
            matchesSearch
        }
    }
}
