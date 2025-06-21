package co.feip.fefu2025.presentation.details

data class PaginationState<T>(
    val items: List<T>,
    val isLoadingNextPage: Boolean = false,
    val canLoadMore: Boolean = true
)