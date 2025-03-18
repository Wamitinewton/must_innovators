package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.utils.Resource
import com.newton.events.presentation.events.SearchUiEvent
import com.newton.events.presentation.states.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState = _uiState.asStateFlow()

    val pagedEvents: Flow<PagingData<EventsData>> = eventRepository
        .getPagedEvents()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Minimum characters required for search
    private val minSearchLength = 3

    private val searchDebounceMs = 500L

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults = _searchQuery
        .debounce(searchDebounceMs)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.length >= minSearchLength) {
                _uiState.value = SearchUiState.Loading
                eventRepository.searchEvents(query)
            } else {
                if (query.isEmpty()) {
                    _uiState.value = SearchUiState.Initial
                }
                flowOf(Resource.Success(emptyList()))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading(false)
        )


    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.Search -> {
                onSearchQueryChanged(event.query)
            }
            is SearchUiEvent.ClearSearch -> {
                clearSearch()
            }
            is SearchUiEvent.RetrySearch -> {
                retrySearch()
            }
            is SearchUiEvent.ExecuteSearch -> {
                executeSearch()
            }
        }
    }


    private fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        // Reset view state if query is cleared
        if (query.isEmpty()) {
            _uiState.value = SearchUiState.Initial
        }
    }


    private fun executeSearch() {
        if (_searchQuery.value.length < minSearchLength) {
            _uiState.value = SearchUiState.Error("Please enter at least $minSearchLength characters to search")
            return
        }

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            eventRepository.searchEvents(_searchQuery.value).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        val events = result.data ?: emptyList()
                        if (events.isNotEmpty()) {
                            _uiState.value = SearchUiState.Success(events)
                        } else {
                            _uiState.value = SearchUiState.Empty
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = SearchUiState.Error(result.message ?: "An unknown error occurred")
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = SearchUiState.Loading
                        }
                    }
                }
            }
        }
    }

    private fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState.Initial
    }


    private fun retrySearch() {
        if (_searchQuery.value.isNotEmpty()) {
            executeSearch()
        }
    }
}