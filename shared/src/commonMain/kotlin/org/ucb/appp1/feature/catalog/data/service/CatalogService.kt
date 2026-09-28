package org.ucb.appp1.feature.catalog.data.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.ucb.appp1.feature.catalog.data.datasource.CatalogRemoteDataSource
import org.ucb.appp1.feature.catalog.data.dto.CatalogDto
import org.ucb.appp1.feature.catalog.data.mapper.toModel
import org.ucb.appp1.feature.catalog.domain.model.MovieModel

private const val API_KEY="https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=fa3e844ce31744388e07fa47c7c5d8c3"
class CatalogService(
    private val client: HttpClient
) : CatalogRemoteDataSource {

    override suspend fun fetchData(): Result<List<MovieModel>> {
        return try {
            // Se utiliza la URL completa con tu API Key y ordenamiento por popularidad
            val response: CatalogDto = client.get(API_KEY).body()
            Result.success(response.results.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}