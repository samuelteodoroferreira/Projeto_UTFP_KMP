package network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.CepInfo

/**
 * Cliente HTTP para a API ViaCEP
 * @author Samuel Teodoro Ferreira
 */
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
            
            if (cepInfo.erro) {
                Result.failure(Exception("CEP não encontrado"))
            } else {
                Result.success(cepInfo)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 