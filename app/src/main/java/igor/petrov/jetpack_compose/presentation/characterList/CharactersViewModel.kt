package igor.petrov.jetpack_compose.presentation.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import igor.petrov.jetpack_compose.adapter.CharacterListPagingSource
import igor.petrov.jetpack_compose.domain.GetPagedCharactersUseCase
import igor.petrov.jetpack_compose.entity.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getPagedCharactersUseCase: GetPagedCharactersUseCase) :
    ViewModel() {

    private val characterListScope = CoroutineScope(Dispatchers.Main)

    val pagedCharacterList: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 25),
        initialKey = null,
        pagingSourceFactory = { CharacterListPagingSource(getPagedCharactersUseCase) }
    ).flow.cachedIn(viewModelScope)
}

