package co.feip.fefu2025.domain.model

data class PaginatedData<T>(
    val data: List<T>,
    val hasNextPage: Boolean
)