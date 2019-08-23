package io.github.thewilly.conductor.client.types

import java.net.InetAddress
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class HotSpot(val SIM: SimCard) {

    /**
     * Frequency in which the hotspot is working in MHz
     */
    var frequency: Float = 446.00625f

    /**
     * Continuous Tone-Coded Squelch System in Hz, 0.0 means it is off.
     */
    var ctcss: Float = 67.0f

    /**
     * Bandwidth of the channel under use, 12.5 KHz means analog and 6.25 KHz means digital radio.
     */
    var bandwidth: Float = 12.5f

    /**
     * The public ip of the device.
     */
    val ip: String
        get() {
            val whatismyip = URL("http://checkip.amazonaws.com")
            val ip = BufferedReader(InputStreamReader(
                    whatismyip.openStream()))

            return ip.readLine()
        }

    /**
     * The port where the services of the devices are working.
     */
    var port: Int = 8081
}