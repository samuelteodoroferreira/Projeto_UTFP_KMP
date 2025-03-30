package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados para informações de CEP
 * @author Samuel Teodoro Ferreira
 */
@Serializable
data class CepInfo(
    @SerialName("cep") val cep: String,
    @SerialName("logradouro") val logradouro: String,
    @SerialName("bairro") val bairro: String,
    @SerialName("localidade") val localidade: String,
    @SerialName("uf") val uf: String,
    @SerialName("erro") val erro: Boolean = false
) 