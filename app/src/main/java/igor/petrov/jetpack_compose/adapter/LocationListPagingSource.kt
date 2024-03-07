package igor.petrov.jetpack_compose.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import igor.petrov.jetpack_compose.App
import igor.petrov.jetpack_compose.R
import igor.petrov.jetpack_compose.domain.GetPagedLocationsUseCase
import igor.petrov.jetpack_compose.entity.Location

class LocationListPagingSource(private val getPagedLocationsUseCase: GetPagedLocationsUseCase) :
    PagingSource<Int, Location>() {

    override fun getRefreshKey(state: PagingState<Int, Location>): Int = App.applicationContext().getString(R.string.first_page).toInt()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            getPagedLocationsUseCase.getPagedLocationList(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}