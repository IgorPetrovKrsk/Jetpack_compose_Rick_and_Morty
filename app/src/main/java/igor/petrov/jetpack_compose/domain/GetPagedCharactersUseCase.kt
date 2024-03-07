package igor.petrov.jetpack_compose.domain

import igor.petrov.jetpack_compose.data.CharactersListRepository
import igor.petrov.jetpack_compose.entity.Character
import javax.inject.Inject


class GetPagedCharactersUseCase @Inject constructor(
    private val charactersListRepository: CharactersListRepository
) {
   suspend fun getPagedCharacterList(page: Int): List<Character> {
        return charactersListRepository.getCharacterList(page).body()?.characters?: emptyList<Character>()
    }
}
