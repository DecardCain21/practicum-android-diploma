package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.Region

data class RegionState(
    val input: Input,
    val data: Data
) {

    sealed interface Input {
        data object Empty : Input
        data class Text(val value: String) : Input
    }

    sealed interface Data {
        data object NoInternet : RegionState.Data
        data object Empty : RegionState.Data
        data object Loading : RegionState.Data
        data object Error : RegionState.Data
        data class Data(val regions: List<Region>) :
            RegionState.Data
    }
}
