package org.ucb.appp1

import kotlinx.coroutines.test.runTest
import org.ucb.appp1.userinformation.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.userinformation.data.dto.UserInfoDto
import org.ucb.appp1.userinformation.data.mapper.toDomain
import org.ucb.appp1.userinformation.data.repository.GithubRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GithubRepositoryTest {

    private class FakeGithubRemoteDataSource : GithubRemoteDataSource {
        override suspend fun getUser(nickname: String): UserInfoDto {
            return UserInfoDto(
                email = "octocat@github.com",
                avatarUrl = "https://github.com/images/error/octocat_happy.gif"
            )
        }
    }

    @Test
    fun testUserInfoDtoToDomain() {
        val dto = UserInfoDto(
            email = "user@example.com",
            avatarUrl = "https://example.com/avatar.png"
        )
        val domain = dto.toDomain(alias = "user")

        assertEquals("user@example.com", domain.email)
        assertEquals("https://example.com/avatar.png", domain.avatarUrl)
        assertEquals("user", domain.alias)
    }

    @Test
    fun testRepositoryFindByAlias() = runTest {
        val fakeDataSource = FakeGithubRemoteDataSource()
        val repository = GithubRepositoryImpl(fakeDataSource)

        val result = repository.findByAlias("octocat")

        assertTrue(result.isSuccess)
        val user = result.getOrNull()
        assertEquals("octocat@github.com", user?.email)
        assertEquals("https://github.com/images/error/octocat_happy.gif", user?.avatarUrl)
        assertEquals("octocat", user?.alias)
    }
}
