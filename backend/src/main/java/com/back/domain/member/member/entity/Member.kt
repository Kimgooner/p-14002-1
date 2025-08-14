package com.back.domain.member.member.entity

import com.back.global.jpa.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

@Entity
@NoArgsConstructor
class Member : BaseEntity {
    @Column(unique = true)
    var username: String?
        private set
    var password: String? = null
        private set
    var name: String?
        private set
    set
    @Column(unique = true)
    var apiKey: String? = null
        private set
    var profileImgUrl: String? = null
        private set

    constructor(id: Int, username: String?, nickname: String?) {
        setId(id)
        this.username = username
        this.name = nickname
    }

    constructor(username: String?, password: String?, nickname: String?, profileImgUrl: String?) {
        this.username = username
        this.password = password
        this.name = nickname
        this.profileImgUrl = profileImgUrl
        this.apiKey = UUID.randomUUID().toString()
    }

    fun modifyApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    val isAdmin: Boolean
        get() {
            if ("system" == username) return true
            if ("admin" == username) return true

            return false
        }

    val authorities: MutableCollection<out GrantedAuthority?>
        get() = this.authoritiesAsStringList
            .stream()
            .map<SimpleGrantedAuthority?> { role: String? -> SimpleGrantedAuthority(role) }
            .toList()

    private val authoritiesAsStringList: MutableList<String?>
        get() {
            val authorities: MutableList<String?> = ArrayList<String?>()

            if (this.isAdmin) authorities.add("ROLE_ADMIN")

            return authorities
        }

    fun modify(nickname: String?, profileImgUrl: String?) {
        this.name = nickname
        this.profileImgUrl = profileImgUrl
    }

    val profileImgUrlOrDefault: String
        get() {
            if (profileImgUrl == null) return "https://placehold.co/600x600?text=U_U"

            return profileImgUrl!!
        }
}
