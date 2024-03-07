package igor.petrov.jetpack_compose.data

import retrofit2.Response
import javax.inject.Inject

class LocationsListRepository @Inject constructor() {

    suspend fun getLocationList(page: Int): Response<LocationListDto> {
        return RetrofitInstance.rickAndMortyApi.getPagedLocationList(page)
    }

}

