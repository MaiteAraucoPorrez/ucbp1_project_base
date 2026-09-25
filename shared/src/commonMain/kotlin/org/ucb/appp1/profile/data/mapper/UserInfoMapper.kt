package org.ucb.appp1.profile.data.mapper

import org.ucb.appp1.profile.data.dto.UserInfoDto
import org.ucb.appp1.profile.domain.model.UserInfoModel

fun UserInfoDto.toDomain(): UserInfoModel = UserInfoModel(
    email = email ?: "",
    company = "",
    avatarUrl = avatarUrl ?: "",
    alias = ""
)
