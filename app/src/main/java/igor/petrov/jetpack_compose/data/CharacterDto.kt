package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.Character

@JsonClass(generateAdapter = true)
data class CharacterDto (
    @Json(name = "id") override val id: Int = 0,
    @Json(name = "name") override val name: String? = null,
    @Json(name = "status") override val status: String? = null,
    @Json(name = "species") override val species: String? = null,
    @Json(name = "type") override val type: String? = null,
    @Json(name = "gender") override val gender: String? = null,
    @Json(name = "origin") override val origin: OriginDto? = null,
    @Json(name = "location") override val characterLocation: CharacterLocationDto? = null,
    @Json(name = "image") override val image: String? = null,
    @Json(name = "episode") override val episode: List<String>? = null,
    @Json(name = "url") override val url: String? = null,
    @Json(name = "created") override val created: String? = null
): Character