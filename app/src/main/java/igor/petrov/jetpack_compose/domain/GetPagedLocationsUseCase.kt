package igor.petrov.jetpack_compose.domain

import igor.petrov.jetpack_compose.data.CharactersListRepository
import igor.petrov.jetpack_compose.data.LocationsListRepository
import igor.petrov.jetpack_compose.entity.Character
import igor.petrov.jetpack_compose.entity.Location
import igor.petrov.jetpack_compose.entity.LocationList
import javax.inject.Inject


class GetPagedLocationsUseCase @Inject constructor(
    private val locationsListRepository: LocationsListRepository
) {
   suspend fun getPagedLocationList(page: Int): List<Location> {
        return locationsListRepository.getLocationList(page).body()?.locations?: emptyList<Location>()
    }
}
