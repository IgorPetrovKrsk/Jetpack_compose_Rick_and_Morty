package igor.petrov.jetpack_compose.domain

import igor.petrov.jetpack_compose.data.CharacterDto
import igor.petrov.jetpack_compose.data.CharactersListRepository
import igor.petrov.jetpack_compose.entity.Character
import javax.inject.Inject


class GetSingleCharactersUseCase @Inject constructor(
    private val charactersListRepository: CharactersListRepository
) {
   suspend fun getSingleCharacter(characterId: Int): Character {
        return charactersListRepository.getSingleCharacter(characterId).body()?: CharacterDto()
    }
}
