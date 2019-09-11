package io.github.thewilly.conductor.client.hardware

import java.io.IOException
import java.util.Properties

class RaspberryPi : RaspberryPiOperations {

    override var imsi: String
        get() = this.configuration.getProperty("imsi")
        set(value) {this.configuration.setProperty("imsi", value)}

    override val configuration = Properties()

    override fun shutdown(): Boolean? {
        try {
            Runtime.getRuntime().exec("shutdown -s -t 0")
            System.exit(0)
            return true
        } catch (ex: IOException) {
            println("Error while shutting down device: " + ex.message)
            return false
        }

    }

    override fun reboot(): Boolean? {
        try {
            Runtime.getRuntime().exec("reboot")
            System.exit(0)
            return true
        } catch (ex: IOException) {
            println("Error while rebooting device: " + ex.message)
            return false
        }

    }

    override fun computeAuthResponse(rand: String): String {
        // Very simple challenge ( non-secure!!! )
        return rand + this.imsi
    }
}