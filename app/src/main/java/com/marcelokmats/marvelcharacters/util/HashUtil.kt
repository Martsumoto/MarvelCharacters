package com.marcelokmats.marvelcharacters.util

import java.math.BigInteger
import java.security.MessageDigest

fun getMarvelApiKey() = PUBLIC_KEY

fun getMarvelApiHash(timestamp: String): String {
    val digest = MessageDigest.getInstance("MD5")
    val key = timestamp + PRIVATE_KEY + PUBLIC_KEY

    val number = BigInteger(1, digest.digest(key.toByteArray()))
    var md5 = number.toString(16)

    while(md5.length < 32) {
        md5 =  "0"+md5
    }

    return md5
}