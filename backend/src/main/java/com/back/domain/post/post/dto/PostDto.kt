package com.back.domain.post.post.dto

import com.back.domain.post.post.entity.Post
import lombok.Getter
import java.time.LocalDateTime

open class PostDto(post: Post) {
    private val id: Int
    private val createDate: LocalDateTime
    private val modifyDate: LocalDateTime
    private val authorId: Int
    private val authorName: String
    private val title: String

    init {
        id = post.id
        createDate = post.createDate
        modifyDate = post.modifyDate
        authorId = post.author.id
        authorName = post.author.name
        title = post.title
    }
}