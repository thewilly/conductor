package io.github.thewilly.conductor.client.types

/**
 *
 */
data class KeyPair(val publicKey: String, val privateKey: String)

/**
 *
 */
data class SimCard(val imsi: String, val challenge: String)

/**
 *
 */
data class SDCard(val route: String) {
    var memmoryMap: Map<String, String>? = null;
}