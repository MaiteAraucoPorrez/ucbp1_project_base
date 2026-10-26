package org.ucb.appp1.userinformation.domain.usecase

import org.ucb.appp1.userinformation.domain.model.UserInfoModel
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

class FindAliasUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(alias: String): Result<UserInfoModel> {
        val cleanAlias = alias.trim()
        if (cleanAlias.isBlank()) {
            return Result.failure(IllegalArgumentException("Ingresa un usuario de GitHub"))
        }
        return repository.findByAlias(cleanAlias)
    }
}
