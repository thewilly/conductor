package io.github.thewilly.conductor.client.security

import io.github.thewilly.conductor.client.StartUp
import io.github.thewilly.conductor.client.types.MasterServer
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*
import javax.crypto.Cipher

class RegistrationProtocol(private val cipheredSecret: String) {
    private val result: String? = null

    fun init(): ResponseEntity<JSONObject> {
        val cipher = Cipher.getInstance("RSA")

        // Decrypt the received secret with the private key of the device.
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString(StartUp.HOT_SPOT.SIM.keyPair.privateKey))
        val receivedSecret = cipher.doFinal(cipheredSecret.toByteArray(Charsets.UTF_8))

        // Encrypt the secret with the public key of the server
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(MasterServer.publicKey))
        val cipherText = cipher.doFinal(receivedSecret)
        val encodedCipheredSecret = Base64.getEncoder().encodeToString(cipherText)

        // Send the encoded ciphered secret to the server to proceed with the autentication
        val payload = JSONObject()
        payload.put("secret",encodedCipheredSecret)

        return ResponseEntity<JSONObject>(payload, HttpStatus.ACCEPTED)
    }
}
