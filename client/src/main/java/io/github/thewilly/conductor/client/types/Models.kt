package io.github.thewilly.conductor.client.types

/**
 *
 */
data class KeyPair(val publicKey: String, val privateKey: String)

/**
 *
 */
data class SimCard(val imsi: String, val keyPair: KeyPair)

/**
 *
 */
data class SDCard(val route: String) {
    var memoryMap: Map<String, String>? = null;
}