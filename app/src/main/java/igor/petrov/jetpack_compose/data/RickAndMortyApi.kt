package igor.petrov.jetpack_compose.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyApi {
    @GET("character")
    suspend fun getPagedCharactersList(
        @Query("page") page: Int = 1
    ): Response<CharacterListDto>

    @GET("location")
    suspend fun getPagedLocationList(
        @Query("page") page: Int = 1
    ): Response<LocationListDto>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ):Response<CharacterDto>

    @GET()
    suspend fun getEpisode(
        @Url episodeUrl: String
    ):Response<EpisodeDto>
}