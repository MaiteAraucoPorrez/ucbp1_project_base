package org.ucb.appp1.userinformation.data.mapper

import org.ucb.appp1.userinformation.data.dto.UserInfoDto
import org.ucb.appp1.userinformation.domain.model.UserInfoModel

fun UserInfoDto.toDomain(alias: String): UserInfoModel = UserInfoModel(
    email = email ?: "",
    company = company ?: "",
    avatarUrl = avatarUrl ?: "",
    alias = alias
)
