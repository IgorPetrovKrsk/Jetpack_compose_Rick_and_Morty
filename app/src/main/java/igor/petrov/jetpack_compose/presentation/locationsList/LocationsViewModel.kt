package igor.petrov.jetpack_compose.presentation.locationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import igor.petrov.jetpack_compose.adapter.LocationListPagingSource
import igor.petrov.jetpack_compose.domain.GetPagedLocationsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(private val getPagedLocationsUseCase: GetPagedLocationsUseCase) :
    ViewModel() {

    val pagedLocationsList: Flow<PagingData<igor.petrov.jetpack_compose.entity.Location>> = Pager(
        config = PagingConfig(pageSize = 25),
        initialKey = null,
        pagingSourceFactory = { LocationListPagingSource(getPagedLocationsUseCase) }
    ).flow.cachedIn(viewModelScope)
}

