package io.github.thewilly.conductor.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.GeneralSecurityException
import java.io.IOException
import java.security.KeyFactory
import java.util.*
import java.security.spec.X509EncodedKeySpec
import java.security.interfaces.RSAPublicKey

import org.apache.commons.codec.binary.Base64;


@SpringBootApplication
open class StartUp

    val publicKey: String = ""

    fun main(args: Array<String>) {
        SpringApplication.run(StartUp::class.java, *args)
    }


@Throws(IOException::class, GeneralSecurityException::class)
fun getPrivateKeyFromString(key:String): RSAPrivateKey {
    val encoded = Base64.decodeBase64(key.toByteArray())
    val kf = KeyFactory.getInstance("RSA")
    val keySpec = PKCS8EncodedKeySpec(encoded)
    val privKey = kf.generatePrivate(keySpec) as RSAPrivateKey
    return privKey
}

@Throws(IOException::class, GeneralSecurityException::class)
fun getPublicKeyFromString(key:String):RSAPublicKey {
    val encoded = Base64.decodeBase64(key.toByteArray())
    val kf = KeyFactory.getInstance("RSA")
    val pubKey = kf.generatePublic(X509EncodedKeySpec(encoded)) as RSAPublicKey
    return pubKey
}
