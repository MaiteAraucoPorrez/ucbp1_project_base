package org.ucb.appp1.profile.data.repository

import org.ucb.appp1.profile.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.profile.data.mapper.toDomain
import org.ucb.appp1.profile.domain.model.UserInfoModel
import org.ucb.appp1.profile.domain.repository.GithubRepository

class GithubRepositoryImpl(val dataSource: GithubRemoteDataSource) : GithubRepository {
    override suspend fun findByAlias(alias: String): Result<UserInfoModel> {
        return Result.success(dataSource.getUser(alias).toDomain())
    }
}
