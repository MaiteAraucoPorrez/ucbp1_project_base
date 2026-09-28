package org.ucb.appp1.feature.crossref.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.ucb.appp1.feature.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.feature.crossref.data.dto.CrossrefDto
import org.ucb.appp1.feature.crossref.data.mapper.toModel
import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

private const val CROSSREF_URL = "https://api.crossref.org/works?query=machine+learning&rows=3"

class CrossrefService(
    private val client: HttpClient
) : CrossrefRemoteDataSource {

    override suspend fun fetchData(): Result<List<CrossrefModel>> {
        return try {
            val response: CrossrefDto = client.get(CROSSREF_URL).body()
            Result.success(response.message.items.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
