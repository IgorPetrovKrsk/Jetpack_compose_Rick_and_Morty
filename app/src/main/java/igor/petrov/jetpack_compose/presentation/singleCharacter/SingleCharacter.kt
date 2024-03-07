package igor.petrov.jetpack_compose.presentation.singleCharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import igor.petrov.jetpack_compose.R
import igor.petrov.jetpack_compose.data.LoadingState
import igor.petrov.jetpack_compose.presentation.ErrorItem
import igor.petrov.jetpack_compose.presentation.LoadingView

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleCharacter(characterId: Int) {
    val singleCharacterViewModel: SingleCharacterViewModel = hiltViewModel<SingleCharacterViewModel>()
    if (singleCharacterViewModel.characterId == 0) {
        singleCharacterViewModel.characterId = characterId
        singleCharacterViewModel.getSingleCharacter()
    }
    val character by singleCharacterViewModel.singleCharacter.collectAsState()
    val firstEpisodeName by singleCharacterViewModel.firstEpisodeName.collectAsState()
    val isEpisodesListExpanded by singleCharacterViewModel.isEpisodesListExpanded.collectAsState()
    val characterEpisodesList by singleCharacterViewModel.characterEpisodes.collectAsState()

    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        AsyncImage(model = ImageRequest
            .Builder(LocalContext.current)
            .data(character.image)
            .crossfade(true)
            .crossfade(1000)
            .build(), contentDescription = null, modifier = Modifier
            .fillMaxSize()
            , error = painterResource(id = R.drawable.no_internet), contentScale = ContentScale.FillWidth)
        Text(text = character.name ?: stringResource(id = R.string.text_unknown), fontSize = 32.sp, textAlign = TextAlign.Start, modifier = Modifier.padding(start = 16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 1f),
                        Color.Black.copy(alpha = 0.3f),
                        Color.Black.copy(alpha = 0f),
                        Color.Black.copy(alpha = 0f),
                    )
                )
            ))
        Text(text = stringResource(id = R.string.text_status), color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Row(modifier = Modifier
            .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = when (character.status) {
                "Alive" -> R.drawable.ic_dot_green
                "Dead" -> R.drawable.ic_dot_red
                "unknown" -> R.drawable.ic_dot_grey
                else -> R.drawable.ic_dot_grey
            }), contentDescription = null, tint = Color.Unspecified)
            Text(text = "  ${character.status ?: ""} ")
        }
        Text(text = stringResource(id = R.string.text_species_and_gender), color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Text(text = "${character.species} (${character.gender})")

        Text(text = stringResource(R.string.text_last_known_location), color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Text(text = character.characterLocation?.name ?: "")

        Text(text = stringResource(id = R.string.text_first_seen), color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Text(text = firstEpisodeName)

        Row(modifier = Modifier
            .clickable {
                singleCharacterViewModel.isEpisodesListExpanded.value = !singleCharacterViewModel.isEpisodesListExpanded.value
                if (characterEpisodesList.isEmpty()) {
                    singleCharacterViewModel.getEpisodes()
                }
            }
            .padding(top = 8.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(text = stringResource(id = R.string.text_episodes))
            Icon(painter = painterResource(id = if (isEpisodesListExpanded) R.drawable.ic_collapse else R.drawable.ic_expand), contentDescription = null)
        }
        if (isEpisodesListExpanded) {
            characterEpisodesList.forEach {
                Column(modifier = Modifier
                    .padding(top = 4.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    , verticalArrangement = Arrangement.SpaceBetween
                    , horizontalAlignment = Alignment.Start) {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top){
                        Text(text = it.name?.take(36)?: stringResource(id = R.string.text_loading), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = it.episode?: "", maxLines = 1, textAlign = TextAlign.End)
                    }
                    Text(text = it.airDate?:"")

                }

            }
        }
    }

    val loadingState by singleCharacterViewModel.loadingState.collectAsState()

    when (loadingState) {
        is LoadingState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is LoadingState.Ready -> {
        }

        is LoadingState.Error -> {
            ErrorItem((loadingState as LoadingState.Error).exception.localizedMessage ?: "Unknown error", onClickRetry = { singleCharacterViewModel.getSingleCharacter() })
        }
    }
}