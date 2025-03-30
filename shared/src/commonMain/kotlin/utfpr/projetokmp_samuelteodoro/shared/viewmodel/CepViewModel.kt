package utfpr.projetokmp_samuelteodoro.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import utfpr.projetokmp_samuelteodoro.shared.model.CepInfo
import utfpr.projetokmp_samuelteodoro.shared.network.CepApi

class CepViewModel {
    private val cepApi = CepApi()
    
    private val _uiState = MutableStateFlow(CepUiState())
    val uiState: StateFlow<CepUiState> = _uiState.asStateFlow()
    
    fun onCepChanged(cep: String) {
        _uiState.value = _uiState.value.copy(
            cep = cep,
            isSearchEnabled = cep.length == 8 && cep.all { it.isDigit() }
        )
    }
    
    suspend fun buscarCep() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            isInputEnabled = false,
            error = null
        )
        
        cepApi.buscarCep(_uiState.value.cep)
            .onSuccess { cepInfo ->
                _uiState.value = _uiState.value.copy(
                    cepInfo = cepInfo,
                    isLoading = false,
                    isInputEnabled = true
                )
            }
            .onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message ?: "Erro ao buscar CEP",
                    isLoading = false,
                    isInputEnabled = true
                )
            }
    }
}

data class CepUiState(
    val cep: String = "",
    val cepInfo: CepInfo? = null,
    val isLoading: Boolean = false,
    val isInputEnabled: Boolean = true,
    val isSearchEnabled: Boolean = false,
    val error: String? = null
) 