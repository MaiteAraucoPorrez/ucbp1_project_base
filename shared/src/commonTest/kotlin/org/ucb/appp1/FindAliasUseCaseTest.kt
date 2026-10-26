package org.ucb.appp1

import kotlinx.coroutines.test.runTest
import org.ucb.appp1.userinformation.domain.model.UserInfoModel
import org.ucb.appp1.userinformation.domain.repository.GithubRepository
import org.ucb.appp1.userinformation.domain.usecase.FindAliasUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FindAliasUseCaseTest {

    private class FakeGithubRepository : GithubRepository {
        var lastAlias: String? = null

        override suspend fun findByAlias(alias: String): Result<UserInfoModel> {
            lastAlias = alias
            return Result.success(UserInfoModel(email = "", company = "", avatarUrl = "", alias = alias))
        }
    }

    @Test
    fun testBlankAliasFailsWithoutCallingRepository() = runTest {
        val repository = FakeGithubRepository()
        val useCase = FindAliasUseCase(repository)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertNull(repository.lastAlias)
    }

    @Test
    fun testAliasIsTrimmedBeforeSearching() = runTest {
        val repository = FakeGithubRepository()
        val useCase = FindAliasUseCase(repository)

        val result = useCase("  octocat ")

        assertTrue(result.isSuccess)
        assertEquals("octocat", repository.lastAlias)
    }
}
