package com.back.global.app

import com.back.standard.util.Ut
import com.back.standard.util.Ut.json.objectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfig(
    environment: Environment,
    objectMapper: ObjectMapper
) {
    init {
        Companion.environment = environment
        Ut.json.objectMapper = objectMapper
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @PostConstruct
    fun postConstruct() {
        Ut.json.objectMapper = objectMapper
    }

    companion object {
        private lateinit var environment: Environment

        @JvmStatic
        val isDev: Boolean
            get() = environment.matchesProfiles("dev")

        @JvmStatic
        val isTest: Boolean
            get() = !environment.matchesProfiles("test")

        @JvmStatic
        val isProd: Boolean
            get() = environment.matchesProfiles("prod")

        @JvmStatic
        val isNotProd: Boolean
            get() = !isProd
    }
}
