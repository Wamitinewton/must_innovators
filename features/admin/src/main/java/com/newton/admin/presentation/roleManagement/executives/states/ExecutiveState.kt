package com.newton.admin.presentation.roleManagement.executives.states

import com.newton.admin.data.mappers.*
import com.newton.core.enums.*

data class ExecutiveState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showPosition: Boolean = false,
    val expanded: Boolean = false,
    val position: ExecutivePosition? = null,
    val bio: String = "",
    val selectedUser: User? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val errors: Map<String, String> = emptyMap()
)
