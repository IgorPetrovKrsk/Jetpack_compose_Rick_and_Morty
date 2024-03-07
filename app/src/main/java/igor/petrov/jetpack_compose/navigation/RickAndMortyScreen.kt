package igor.petrov.jetpack_compose.navigation

import androidx.annotation.StringRes
import igor.petrov.jetpack_compose.R

enum class RickAndMortyScreen (@StringRes val title:Int) {
    CharacterList(title = R.string.text_character_list),
    LocationList(title = R.string.text_location_list),
    SingleCharacter(title = R.string.text_character)
}