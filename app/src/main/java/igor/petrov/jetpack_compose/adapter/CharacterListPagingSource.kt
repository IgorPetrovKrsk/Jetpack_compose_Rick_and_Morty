package igor.petrov.jetpack_compose.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import igor.petrov.jetpack_compose.App
import igor.petrov.jetpack_compose.R
import igor.petrov.jetpack_compose.domain.GetPagedCharactersUseCase

class CharacterListPagingSource(private val getPagedCharactersUseCase: GetPagedCharactersUseCase) :
    PagingSource<Int, igor.petrov.jetpack_compose.entity.Character>() {

    override fun getRefreshKey(state: PagingState<Int, igor.petrov.jetpack_compose.entity.Character>): Int = App.applicationContext().getString(R.string.first_page).toInt()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, igor.petrov.jetpack_compose.entity.Character> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            getPagedCharactersUseCase.getPagedCharacterList(page)
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