package igor.petrov.jetpack_compose.data

sealed class LoadingState {
    data object Ready : LoadingState()
    data object Loading : LoadingState()
    data class Error(val exception: Exception) : LoadingState()
}