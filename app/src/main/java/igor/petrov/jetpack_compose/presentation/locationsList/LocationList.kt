package igor.petrov.jetpack_compose.presentation.locationsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import igor.petrov.jetpack_compose.R
import igor.petrov.jetpack_compose.entity.Location
import igor.petrov.jetpack_compose.presentation.ErrorItem
import igor.petrov.jetpack_compose.presentation.LoadingItem
import igor.petrov.jetpack_compose.presentation.LoadingView
import kotlinx.coroutines.flow.Flow

@Composable
fun LocationList() {
    val locationViewModel: LocationsViewModel = hiltViewModel()
    val locationFlow: Flow<PagingData<Location>> = locationViewModel.pagedLocationsList
    val lazyLocations: LazyPagingItems<Location> = locationFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyLocations.itemCount) { index ->
            LocationItem(lazyLocations[index]!!)
        }

        lazyLocations.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = lazyLocations.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(error.error.localizedMessage ?: "Unknown error", onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyLocations.loadState.append as LoadState.Error
                    item {
                        ErrorItem(error.error.localizedMessage ?: "Unknown error", onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: Location) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier
            .padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = location.name ?: context.getString(R.string.text_unknown), fontSize = 20.sp, textAlign = TextAlign.Start)
            Text(text = location.type ?: context.getString(R.string.text_unknown))
            Text(text = location.dimension ?: context.getString(R.string.text_unknown))
        }
    }
}