package io.github.thewilly.conductor.client.hardware

import java.util.Properties

/**
 *
 */
interface RaspberryPiOperations {

    /**
     * Gets the International Mobile Subscriber Identity as an String.
     *
     * @return the International Mobile Subscriber Identity
     */
    /**
     * Sets the IMSI to the desired one.
     *
     * @param Imsi to set.
     */
    var imsi: String

    /**
     * Gets the configuration properties of the raspberry pi.
     *
     * @return the configuration properties of the raspberry pi.
     */
    val configuration: Properties

    /**
     * Turns off the raspberry.
     *
     * @return true if the operation was carried out succesfully, false otherwise.
     */
    fun shutdown(): Boolean?

    /**
     * Reboots the raspberry.
     *
     * @return true if the operation was carried out succesfully, false otherwise.
     */
    fun reboot(): Boolean?

    /**
     * Once the raspberry send out its IMSI to the network for authentication the
     * network will send a random challenge to perform an operation and return the
     * result to the network again.
     *
     * @param rand challenge to compute.
     * @return the result after the computation over the rand number.
     */
    fun computeAuthResponse(rand: String): String
}
