package org.ucb.appp1.profile.data.datasource

import org.ucb.appp1.profile.data.dto.UserInfoDto

interface GithubRemoteDataSource {
    suspend fun getUser(nickname: String): UserInfoDto
}
