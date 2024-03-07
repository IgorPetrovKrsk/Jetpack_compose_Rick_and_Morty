package igor.petrov.jetpack_compose.domain

import igor.petrov.jetpack_compose.data.CharacterDto
import igor.petrov.jetpack_compose.data.CharactersListRepository
import igor.petrov.jetpack_compose.data.EpisodeDto
import igor.petrov.jetpack_compose.data.EpisodeRepository
import igor.petrov.jetpack_compose.entity.Character
import igor.petrov.jetpack_compose.entity.Episode
import javax.inject.Inject


class GetEpisodeUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
   suspend fun getEpisode(episodeUrl: String): EpisodeDto {
        return episodeRepository.getEpisode(episodeUrl).body()?: EpisodeDto()
    }
}
