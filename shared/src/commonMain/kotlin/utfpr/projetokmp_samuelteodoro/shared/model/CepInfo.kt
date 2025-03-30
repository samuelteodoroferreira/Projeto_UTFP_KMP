package utfpr.projetokmp_samuelteodoro.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CepInfo(
    @SerialName("cep") val cep: String,
    @SerialName("logradouro") val logradouro: String,
    @SerialName("bairro") val bairro: String,
    @SerialName("localidade") val localidade: String,
    @SerialName("uf") val uf: String,
    @SerialName("erro") val erro: Boolean = false
) 