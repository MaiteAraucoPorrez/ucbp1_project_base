package org.ucb.appp1.profile.domain.repository

import org.ucb.appp1.profile.domain.model.UserInfoModel

interface GithubRepository {
    suspend fun findByAlias(alias: String): Result<UserInfoModel>
}
