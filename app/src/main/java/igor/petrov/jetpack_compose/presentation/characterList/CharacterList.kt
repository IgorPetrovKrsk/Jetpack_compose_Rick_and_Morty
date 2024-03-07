package igor.petrov.jetpack_compose.presentation.characterList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import igor.petrov.jetpack_compose.R
import igor.petrov.jetpack_compose.data.CharacterDto
import igor.petrov.jetpack_compose.entity.Character
import igor.petrov.jetpack_compose.navigation.RickAndMortyScreen
import igor.petrov.jetpack_compose.presentation.ErrorItem
import igor.petrov.jetpack_compose.presentation.LoadingItem
import igor.petrov.jetpack_compose.presentation.LoadingView
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterList(navController: NavHostController) {
    val charactersViewModel = hiltViewModel<CharactersViewModel>()
    val characterFlow: Flow<PagingData<Character>> = charactersViewModel.pagedCharacterList
    val lazyCharacters: LazyPagingItems<Character> = characterFlow.collectAsLazyPagingItems()

    PaddingValues(top = 64.dp)
    LazyColumn {
        items(lazyCharacters.itemCount) { index ->
            CharacterItem(lazyCharacters[index]!!, navController)
        }

        lazyCharacters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = lazyCharacters.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(error.error.localizedMessage ?: "Unknown error", onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyCharacters.loadState.append as LoadState.Error
                    item {
                        ErrorItem(error.error.localizedMessage ?: "Unknown error", onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CharacterItem(character: Character, navController: NavHostController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(RickAndMortyScreen.SingleCharacter.name + "/${character.id}/${character.name}")
            }, horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(character.image)
            .crossfade(true)
            .crossfade(1000)
            .build(), contentDescription = null, modifier = Modifier
            .size(200.dp, 200.dp), error = painterResource(id = R.drawable.no_internet))
        Column(modifier = Modifier
            .padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = character.name ?: stringResource(id = R.string.text_unknown), fontSize = 20.sp, textAlign = TextAlign.Start)
            Row(modifier = Modifier.padding(top = 30.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = when (character.status) {
                    "Alive" -> R.drawable.ic_dot_green
                    "Dead" -> R.drawable.ic_dot_red
                    "unknown" -> R.drawable.ic_dot_grey
                    else -> R.drawable.ic_dot_grey
                }), contentDescription = null, tint = Color.Unspecified)
                Text(text = "  ${character.status ?: ""} - ${character.species}")
            }

            Text(text = context.getString(R.string.text_last_known_location), color = Color.Gray)
            Text(text = character.characterLocation?.name ?: "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    CharacterItem(character = CharacterDto(0, "Rick", "Alive", "Human", "1", "", null, null, "https://rickandmortyapi.com/api/character/avatar/1.jpeg", null, null, null), rememberNavController())
}