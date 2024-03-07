package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.CharacterLocation

@JsonClass(generateAdapter = true)
data class CharacterLocationDto(
    @Json(name = "name") override val name: String?,
    @Json(name = "url") override val url: String?
): CharacterLocation