package igor.petrov.jetpack_compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import igor.petrov.jetpack_compose.entity.Origin

@JsonClass(generateAdapter = true)
class OriginDto (
    @Json(name = "name") override val name: String?,
    @Json(name = "url") override val url: String?
): Origin