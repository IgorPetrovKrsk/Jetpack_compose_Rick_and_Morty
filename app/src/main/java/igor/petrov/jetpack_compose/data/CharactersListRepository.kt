package igor.petrov.jetpack_compose.data

import retrofit2.Response
import javax.inject.Inject

class CharactersListRepository @Inject constructor() {

    suspend fun getCharacterList(page: Int): Response<CharacterListDto> {
        return RetrofitInstance.rickAndMortyApi.getPagedCharactersList(page)
    }
    suspend fun getSingleCharacter(characterId: Int): Response<CharacterDto> {
        return RetrofitInstance.rickAndMortyApi.getSingleCharacter(characterId)
    }

}

