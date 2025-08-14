package com.back.domain.post.postComment.dto

import com.back.domain.post.postComment.entity.PostComment
import lombok.Getter
import java.time.LocalDateTime

@Getter
class PostCommentDto(postComment: PostComment) {
    private val id: Int
    private val createDate: LocalDateTime
    private val modifyDate: LocalDateTime
    private val authorId: Int
    private val authorName: String
    private val postId: Int
    private val content: String

    init {
        id = postComment.id
        createDate = postComment.createDate
        modifyDate = postComment.modifyDate
        authorId = postComment.author.id
        authorName = postComment.author.name
        postId = postComment.post.id
        content = postComment.content
    }
}