package com.example.readyplay.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readyplay.domain.model.ExternalID
import com.example.readyplay.domain.usecase.GetExternalIdUsecase
import com.example.readyplay.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class QRModalViewModel(
    private val usecase: GetExternalIdUsecase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<QRModalUiState> =
        MutableStateFlow(QRModalUiState.Loading)
    val uiState: StateFlow<QRModalUiState> get() = _uiState.asStateFlow()

//    init {
//      getExternalID("nnm")
//    }

    fun getExternalID(id: Int) =
        usecase.invoke(id.toString())
            .onEach { mapUiState(it) }.launchIn(viewModelScope)

    private fun mapUiState(result: NetworkResult<ExternalID>) {
        when (result) {
            is NetworkResult.Error -> {
                _uiState.update {
                    QRModalUiState.Error(
                        message = result.message.toString(),
                    )
                }
            }

            is NetworkResult.Loading ->
                _uiState.update { QRModalUiState.Loading }

            is NetworkResult.Success ->
                _uiState.update {
                    QRModalUiState.Content(
                        data = result.data,
                    )
                }
        }
    }
}

sealed class QRModalUiState {
    object Loading : QRModalUiState()

    data class Content(val data: ExternalID? = null) : QRModalUiState()

    data class Error(val message: String) : QRModalUiState()
}
