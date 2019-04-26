package com.marcelokmats.marvelcharacters.util

import android.content.res.Resources
import com.marcelokmats.marvelcharacters.R
import java.math.BigInteger
import java.security.MessageDigest

fun getMarvelApiHash(resources: Resources): String {
    val digest = MessageDigest.getInstance("MD5")
    val key = "1" + resources.getString(R.string.marvel_private_key) +
            resources.getString(R.string.marvel_public_key)

    val number = BigInteger(1, digest.digest(key.toByteArray()))
    var md5 = number.toString(16)

    while(md5.length < 32) {
        md5 =  "0"+md5
    }

    return md5
}