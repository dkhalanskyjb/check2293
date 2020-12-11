package com.example.base64

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.Url
import io.ktor.utils.io.core.use
import kotlin.js.JsName
import kotlin.jvm.JvmName

interface Base64Encoder {
    @JsName("encode")
    fun encode(src: ByteArray): ByteArray

    @JsName("encodeToString")
    fun encodeToString(src: ByteArray): String {
        return encode(src).let { encoded ->
            buildString(encoded.size) {
                encoded.forEach { append(it.toChar()) }
            }
        }
    }

    suspend fun downloadAndEncodeToString(url: String): String {
        return HttpClient().use {
            it.get<HttpResponse>(url = Url(url)).readText()
        }
    }
}

class Base64Decoder {
    @JsName("decodeFromString")
    suspend fun decodeFromString(encoded: String): String {
        return HttpClient().use {
            it.get<HttpResponse>(
                url = Url("https://httpbin.org/base64/$encoded")
            ).readText()
        }
    }
}

expect object Base64Factory {
    fun createEncoder(): Base64Encoder
}

@JsName("createDecoder")
fun createDecoder(): Base64Decoder = Base64Decoder()