package igor.petrov.jetpack_compose.presentation.singleCharacter

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import igor.petrov.jetpack_compose.data.CharacterDto
import igor.petrov.jetpack_compose.data.EpisodeDto
import igor.petrov.jetpack_compose.data.LoadingState
import igor.petrov.jetpack_compose.domain.GetEpisodeUseCase
import igor.petrov.jetpack_compose.domain.GetSingleCharactersUseCase
import igor.petrov.jetpack_compose.entity.Character
import igor.petrov.jetpack_compose.entity.Episode

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel @Inject constructor(
    private val getSingleCharactersUseCase: GetSingleCharactersUseCase, private val getEpisodeUseCase: GetEpisodeUseCase) :
    ViewModel() {

    private val _characterEpisodes = MutableStateFlow<MutableList<Episode>>(mutableListOf())
    val characterEpisodes = _characterEpisodes.asStateFlow()

    private val _firstEpisodeName = MutableStateFlow<String>("Loading...")
    val firstEpisodeName = _firstEpisodeName.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val loadingState = _loadingState.asStateFlow()

    private val _singleCharacter = MutableStateFlow<Character>(CharacterDto())
    var singleCharacter: StateFlow<Character> = _singleCharacter.asStateFlow()

    var characterId: Int = 0
    var isEpisodesListExpanded = MutableStateFlow<Boolean>(false)

    private val dBScope = CoroutineScope(Dispatchers.IO)

    fun getSingleCharacter() {
        _loadingState.value = LoadingState.Loading
        dBScope.launch {
            try {
                _singleCharacter.value = getSingleCharactersUseCase.getSingleCharacter(characterId)
                _loadingState.value = LoadingState.Ready
                dBScope.launch {
                    _firstEpisodeName.value = getEpisodeUseCase.getEpisode(_singleCharacter.value.episode?.get(0) ?: "").name ?: "Error..."
                }
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e)
            }
        }
    }

    fun getEpisodes() {
        _loadingState.value = LoadingState.Loading
        dBScope.launch {
            try {
                _characterEpisodes.value.add(EpisodeDto()) //to show loading process
                val tempEpisodeList = mutableListOf<Episode>()
                val jobsList = mutableListOf<Job>()
                _singleCharacter.value.episode?.forEach {
                    jobsList.add(dBScope.launch {
                        tempEpisodeList.add(getEpisodeUseCase.getEpisode(it))
                    })
                }
                jobsList.joinAll()
                tempEpisodeList.sortBy {it.id}
                _characterEpisodes.value = tempEpisodeList
                _loadingState.value = LoadingState.Ready
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e)
            }
        }
    }

}

