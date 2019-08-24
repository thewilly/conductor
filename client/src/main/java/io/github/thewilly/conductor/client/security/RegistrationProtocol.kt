package io.github.thewilly.conductor.client.security

import io.github.thewilly.conductor.client.StartUp
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

class RegistrationProtocol() {
    private val result: String? = null

    fun init(): ResponseEntity<String> {

        val digest = MessageDigest.getInstance("SHA-256")
        val challengeResponse = digest.digest(StartUp.HOT_SPOT.SIM.challenge.toByteArray(Charset.defaultCharset()))

        val challengeResponseString = Base64.getEncoder().encodeToString(challengeResponse)

        val payload = JSONObject()
        payload.put("challenge-response", challengeResponseString)

        return ResponseEntity<String>(payload.toString(), HttpStatus.ACCEPTED)
    }
}
