package igor.petrov.jetpack_compose.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com/api/"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val rickAndMortyApi: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
}