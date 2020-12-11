package com.example.base64

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JsBase64Encoder
}

object JsBase64Encoder : Base64Encoder {
    override fun encode(src: ByteArray): ByteArray {
        val buffer = js("Buffer").from(src)
        val string = buffer.toString("base64") as String
        return ByteArray(string.length) { string[it].toByte() }
    }
}

@JsName("decodeFromStringPromise")
fun decodeFromString(str: String): Promise<String> {
    return GlobalScope.promise { createDecoder().decodeFromString(str) }
}