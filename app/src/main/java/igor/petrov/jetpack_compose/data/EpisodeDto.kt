package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.Episode

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    @Json(name = "id") override val id: Int = 0,
    @Json(name = "name") override val name: String? =null,
    @Json(name = "air_date") override val airDate: String? =null,
    @Json(name = "episode") override val episode: String? =null,
    @Json(name = "characters") override val characters: List<String>? =null,
    @Json(name = "url") override val url: String? =null,
    @Json(name = "created") override val created: String? =null
):Episode
