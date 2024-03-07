package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.LocationList

@JsonClass(generateAdapter = true)
data class LocationListDto(
    @Json(name = "results") override val locations: List<LocationDto>
):LocationList
