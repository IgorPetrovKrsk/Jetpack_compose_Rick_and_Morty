package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.Location

@JsonClass(generateAdapter = true)
data class LocationDto (
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "type") override val type: String?,
    @Json(name = "dimension") override val dimension: String?,
    @Json(name = "residents") override val residents: List<String>?,
    @Json(name = "url") override val url: String?,
    @Json(name = "created") override val created: String?
): Location