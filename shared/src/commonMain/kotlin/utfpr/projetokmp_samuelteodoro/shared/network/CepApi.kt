package utfpr.projetokmp_samuelteodoro.shared.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utfpr.projetokmp_samuelteodoro.shared.model.CepInfo

class CepApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun buscarCep(cep: String): Result<CepInfo> {
        return try {
            val response = httpClient.get("https://viacep.com.br/ws/$cep/json/")
            val cepInfo = response.body<CepInfo>()
            
            when {
                cepInfo.erro -> Result.failure(Exception("CEP não encontrado"))
                cepInfo.cep.isEmpty() -> Result.failure(Exception("CEP inválido"))
                else -> Result.success(cepInfo)
            }
        } catch (e: Exception) {
            when {
                e.message?.contains("404") == true -> Result.failure(Exception("CEP não encontrado"))
                e.message?.contains("400") == true -> Result.failure(Exception("CEP inválido"))
                e.message?.contains("Illegal") == true -> Result.failure(Exception("CEP inválido"))
                e.message?.contains("JSON") == true -> Result.failure(Exception("CEP inválido"))
                else -> Result.failure(Exception("Erro ao buscar CEP"))
            }
        }
    }
} 