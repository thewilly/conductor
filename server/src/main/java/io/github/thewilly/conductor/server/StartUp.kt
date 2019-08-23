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

    val privateKey: String = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMC1HR5qKz18E37TrhwqHJEY7G+oVkTpRX/5RksHWe+JPmvtsXtgPEozxLsP63hj8iXlzA8KWE6aldAWH3U+jFeyX9Uc18K3U5xb6lf5JjW6CqcqhTnczaxafrK+23SJ1YjCuwA3L4clQaf1b68VZRIu1gsqe0n8cq0k06ehaWzNAgMBAAECgYEAi5LiK0REWz0BtctFgNqzZBhELz8idLjsAJugYPlLF2Y1EuDOuohiQnAqXj5Skxj4qqA84uvgN9ZZCaTsVfPemuc9UKv7Vgpbc6nc6uhDWhXb8MgebORYsMv/h2oO07AGJEiihRa7hyltJovEVrjSW2307bN++lQfMS9OdULeOoECQQDwBz+Rt3W/MLcYUUeEAsyXtqXX9UQDKUxBoaK1EYrlBY+Y5y++Pz3xhHJD3QAclrmKB+ffzuX0dlknrFq8eAmtAkEAzYfH3FDQA/He/tTKQMeP7+rmDHXMO16Xe1YqSg2Tz75MTRQbwUkMUtyX0X7+3yDkv0Xx2CfOBKqAH9K0Y3GToQJBAIHKiZdT6vm9b+RYXyGFGfiXrUn/uA01kaSTsJXUrJR201VM/cYUEHy+r8L+iAbtgdqft8SP7kyoikEns9Dh3+kCQHjv859lAfHASeoDBfu2MbEHtFQSoJkyoMoXOo0WjipInJciRO6n8BN17/N62bgrn84Y9ySsz2IZfYi8MB7vvuECQQDdzrosAsEqaNHTo+784HUP/2lvTvU/1OVAhnLa5iS7x7zKw8zth7D2+z8EBdaxbJefl0HdEb0rcEPc1fxbRvbw"

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
