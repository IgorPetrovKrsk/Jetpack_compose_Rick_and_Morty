package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.CharacterList

@JsonClass(generateAdapter = true)
data class CharacterListDto(
    @Json(name = "results") override val characters: List<CharacterDto>
): CharacterList