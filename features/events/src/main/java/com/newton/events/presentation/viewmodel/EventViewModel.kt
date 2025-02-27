package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import com.newton.events.domain.repository.EventRepository
import com.newton.events.presentation.events.SearchEvent
import com.newton.events.presentation.states.SearchEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SearchEventState())
    val viewState = _viewState.asStateFlow()

    private val _searchResults = MutableStateFlow<Resource<List<EventsData>>?>(null)
    val searchResults = _searchResults.asStateFlow()

    // Cached paging data for regular event listing
    val pagedEvents: Flow<PagingData<EventsData>> = eventRepository
        .getPagedEvents()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _viewState
        .map { it.query }
        .debounce(300)
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    init {
        // Observe debounced query changes and trigger search
        viewModelScope.launch {
            debouncedQuery
                .filterNotNull()
                .collect { query ->
                    if (query.isNotBlank()) {
                        performSearch(query)
                    } else {
                        _searchResults.value = null
                    }
                }
        }
    }

    fun handleEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.ClearHistory -> clearHistory()
            SearchEvent.ClearSearch -> clearSearch()
            is SearchEvent.Search -> updateSearch(event.query)
            is SearchEvent.SuggestionSelected -> handleSuggestionSelected(event.suggestion)
        }
    }

    private fun clearHistory() {
        _viewState.update {
            it.copy(
                searchHistory = emptyList(),
                suggestions = emptyList()
            )
        }
    }

    private fun clearSearch() {
        _viewState.update {
            it.copy(
                query = "",
                suggestions = emptyList()
            )
        }
        _searchResults.value = null
    }

    private fun updateSearch(query: String) {
        _viewState.update {
            it.copy(
                query = query,
                searchHistory = if (query.isNotBlank()) {
                    (listOf(query) + it.searchHistory)
                        .distinct()
                        .take(10)
                } else {
                    it.searchHistory
                }
            )
        }
        updateSuggestions(query)
    }

    private fun handleSuggestionSelected(suggestion: String) {
        handleEvent(SearchEvent.Search(suggestion))
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            eventRepository.searchEvents(query).collect { result ->
                _searchResults.value = result
            }
        }
    }

    private fun updateSuggestions(query: String) {
        val suggestions = viewState.value.searchHistory
            .asSequence()
            .filter { it.contains(query, ignoreCase = true) }
            .take(10)
            .toList()

        _viewState.update { it.copy(suggestions = suggestions) }
    }
}