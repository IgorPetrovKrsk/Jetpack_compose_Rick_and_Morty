package igor.petrov.jetpack_compose.data

import retrofit2.Response
import javax.inject.Inject

class EpisodeRepository @Inject constructor() {

    suspend fun getEpisode(episodeUrl:String): Response<EpisodeDto> {
        return RetrofitInstance.rickAndMortyApi.getEpisode(episodeUrl)
    }

}

