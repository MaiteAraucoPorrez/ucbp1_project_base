package org.ucb.appp1.userinformation.data.repository

import org.ucb.appp1.userinformation.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.userinformation.data.mapper.toDomain
import org.ucb.appp1.userinformation.domain.model.UserInfoModel
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

class GithubRepositoryImpl(
    private val dataSource: GithubRemoteDataSource
) : GithubRepository {
    // runCatching: antes, si getUser() fallaba (usuario no existe, sin
    // internet), la excepción no se atrapaba y la app se caía en vez
    // de mostrar el estado de Error.
    override suspend fun findByAlias(alias: String): Result<UserInfoModel> = runCatching {
        dataSource.getUser(alias).toDomain(alias)
    }
}
