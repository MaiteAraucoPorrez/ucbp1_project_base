package org.ucb.appp1.feature.catalog.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.ucb.appp1.core.config.Secrets
import org.ucb.appp1.core.util.safeCall
import org.ucb.appp1.feature.catalog.data.datasource.CatalogRemoteDataSource
import org.ucb.appp1.feature.catalog.data.dto.CatalogDto
import org.ucb.appp1.feature.catalog.data.mapper.toModel
import org.ucb.appp1.feature.catalog.domain.model.MovieModel

private const val DISCOVER_URL = "https://api.themoviedb.org/3/discover/movie"

class CatalogService(
    private val client: HttpClient
) : CatalogRemoteDataSource {

    override suspend fun fetchData(): Result<List<MovieModel>> = safeCall {
        // Películas ordenadas por popularidad. La API key sale de local.properties (tmdb.apiKey).
        val response: CatalogDto = client.get(DISCOVER_URL) {
            parameter("sort_by", "popularity.desc")
            parameter("api_key", Secrets.TMDB_API_KEY)
        }.body()
        response.results.map { it.toModel() }
    }
}
