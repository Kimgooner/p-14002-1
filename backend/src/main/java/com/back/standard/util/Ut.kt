package com.back.standard.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import lombok.SneakyThrows
import java.io.BufferedReader
import java.io.InputStreamReader
import java.security.Key
import java.util.*

class Ut {
    object jwt {
        @JvmStatic
        fun toString(secret: String, expireSeconds: Int, body: Map<String, Any>): String {
            val claimsBuilder = Jwts.claims()

            for (entry in body.entries) {
                claimsBuilder.add(entry.key, entry.value)
            }

            val claims = claimsBuilder.build()

            val issuedAt = Date()
            val expiration = Date(issuedAt.getTime() + 1000L * expireSeconds)

            val secretKey: Key = Keys.hmacShaKeyFor(secret.toByteArray())

            val jwt = Jwts.builder()
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact()

            return jwt
        }

        @JvmStatic
        fun isValid(secret: String, jwtStr: String): Boolean {
            val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

            try {
                Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(jwtStr)
            } catch (e: Exception) {
                return false
            }

            return true
        }

        @JvmStatic
        fun payload(secret: String, jwtStr: String): Map<String, Any>? {
            val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

            try {
                return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(jwtStr)
                    .getPayload() as Map<String, Any>
            } catch (e: Exception) {
                return null
            }
        }
    }

    object json {
        lateinit var objectMapper: ObjectMapper

        @JvmStatic
        @JvmOverloads
        fun toString(obj: Any, defaultValue: String = ""): String {
            try {
                return objectMapper.writeValueAsString(obj)
            } catch (e: Exception) {
                return defaultValue
            }
        }
    }

    object cmd {
        @SneakyThrows
        fun run(vararg args: String) {
            val isWindows = System
                .getProperty("os.name")
                .lowercase(Locale.getDefault())
                .contains("win")

            val builder = ProcessBuilder(
                args
                    .map { it.replace("{{DOT_CMD}}", if (isWindows) ".cmd" else "") }
                    .toList()
            )

            // 에러 스트림도 출력 스트림과 함께 병합
            builder.redirectErrorStream(true)

            // 프로세스 시작
            val process = builder.start()

            BufferedReader(InputStreamReader(process.getInputStream())).use { reader ->
                var line: String
                while ((reader.readLine().also { line = it }) != null) {
                    println(line) // 결과 한 줄씩 출력
                }
            }
            // 종료 코드 확인
            val exitCode = process.waitFor()
            println("종료 코드: " + exitCode)
        }

        @JvmStatic
        fun runAsync(vararg args: String) {
            Thread(Runnable {
                run(*args)
            }).start()
        }
    }
}
