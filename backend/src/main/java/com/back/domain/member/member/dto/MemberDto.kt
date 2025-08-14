package com.back.domain.member.member.dto

import com.back.domain.member.member.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import java.time.LocalDateTime

@Getter
open class MemberDto(member: Member) {
    private val id: Int
    private val createDate: LocalDateTime
    private val modifyDate: LocalDateTime
    private val name: String

    @JsonProperty("isAdmin")
    private val admin: Boolean
    private val profileImageUrl: String

    init {
        id = member.id
        createDate = member.createDate
        modifyDate = member.modifyDate
        name = member.name
        admin = member.isAdmin
        profileImageUrl = member.profileImgUrlOrDefault
    }
}
