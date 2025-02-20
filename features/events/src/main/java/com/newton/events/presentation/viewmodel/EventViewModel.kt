package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.PagingCache
import com.newton.events.domain.models.ScoredEventData
import com.newton.events.domain.repository.EventRepository
import com.newton.events.presentation.events.SearchEvent
import com.newton.events.presentation.states.SearchEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import info.debatty.java.stringsimilarity.JaroWinkler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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

    /**
     * Jaro-Winkler similarity calculator
     * This is more efficient that levenshtein
     */
    private val jaroWinkler = JaroWinkler()

    private val _searchInitiated = MutableStateFlow(false)


    private val searchCache = PagingCache<String, List<ScoredEventData>>(maxSize = 100)

    fun handleEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.ClearHistory -> {
                _viewState.update {
                    it.copy(
                        searchHistory = emptyList(),
                        suggestions = emptyList()
                    )
                }
            }
            SearchEvent.ClearSearch -> {
                _viewState.update {
                    it.copy(
                        query = "",
                        suggestions = emptyList(),
                    )
                }
            }
            is SearchEvent.Search -> {
                _viewState.update {
                    it.copy(
                        query = event.query,
                        searchHistory = if (event.query.isNotBlank()) {
                            (listOf(event.query) + it.searchHistory)
                                .distinct()
                                .take(10)
                        } else {
                            it.searchHistory
                        }
                    )
                }
                _searchInitiated.value = event.query.isNotBlank()
                updateSuggestion(event.query)
            }
            is SearchEvent.SuggestionSelected -> {
                handleEvent(SearchEvent.Search(event.suggestion))
            }
        }
    }

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

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchEvents: Flow<PagingData<EventsData>> =
                debouncedQuery.flatMapLatest { query ->
                    if (query.isEmpty()) {
                       eventRepository.getPagedEvents()
                    } else {
                        searchCache.get(query)?.let { cachedResults ->
                            flow { emit(PagingData.from(cachedResults.map { it.eventsData })) }
                        } ?: performSearch(query)
                    }
                }



    private fun performSearch(query: String): Flow<PagingData<EventsData>> {
        val threshold = when {
            query.length <= 2 -> 0.7
            query.length <= 4 -> 0.5
            else -> 0.3
        }
        return eventRepository.getPagedEvents()
            .map { pagingData ->
                val scoredList = mutableListOf<ScoredEventData>()
                val filteredPagingEvents = pagingData.filter { event ->
                    val score = calculateSimilarity(event, query)
                    if (score >= threshold) {
                        scoredList.add(ScoredEventData(event, score))
                        true
                    } else {
                        false
                    }
                }
                searchCache.put(query, scoredList)
                filteredPagingEvents
            }
    }

    private fun calculateSimilarity(eventsData: EventsData, query: String): Double {
        val normalizedKey = query.lowercase()
        return maxOf(
            jaroWinkler.similarity(eventsData.name.lowercase(), normalizedKey) * 1.0,
            jaroWinkler.similarity(eventsData.description.lowercase(), normalizedKey) * 0.7,
            jaroWinkler.similarity(eventsData.location.lowercase(), normalizedKey) * 0.5
        )
    }

    private fun updateSuggestion(query: String) {
        viewModelScope.launch {
            val suggestions = viewState.value.searchHistory
                .asSequence()
                .filter { it.contains(query, ignoreCase = true) }
                .take(10)
                .toList()

            _viewState.update { it.copy(suggestions = suggestions) }
        }
    }
}

