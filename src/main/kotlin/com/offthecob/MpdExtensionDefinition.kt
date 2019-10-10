package com.offthecob

import com.bitwig.extension.api.PlatformType
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList
import com.bitwig.extension.controller.ControllerExtensionDefinition
import com.bitwig.extension.controller.api.ControllerHost
import java.util.*


class MpdExtensionDefinition : ControllerExtensionDefinition() {

    override fun getName(): String {
        return "mpd"
    }

    override fun getAuthor(): String {
        return "whodevil"
    }

    override fun getVersion(): String {
        return "0.1"
    }

    override fun getId(): UUID {
        return DRIVER_ID
    }

    override fun getHardwareVendor(): String {
        return "Akai"
    }

    override fun getHardwareModel(): String {
        return "mpd24"
    }

    override fun getRequiredAPIVersion(): Int {
        return 9
    }

    override fun getNumMidiInPorts(): Int {
        return 1
    }

    override fun getNumMidiOutPorts(): Int {
        return 1
    }

    override fun listAutoDetectionMidiPortNames(list: AutoDetectionMidiPortNamesList, platformType: PlatformType) {
        when (platformType) {
            PlatformType.WINDOWS -> list.add(arrayOf("Akai MPD24"), arrayOf("Akai MPD24"))
            PlatformType.MAC -> {
                // TODO: Set the correct names of the ports for auto detection on Windows platform here
                // and uncomment this when port names are correct.
                // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
            }
            PlatformType.LINUX -> {
                // TODO: Set the correct names of the ports for auto detection on Windows platform here
                // and uncomment this when port names are correct.
                // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
            }
        }
    }

    override fun createInstance(host: ControllerHost): MpdExtension {
        return MpdExtension(this, host)
    }

    companion object {
        private val DRIVER_ID = UUID.fromString("f9382f14-c30e-4c1a-9ee9-f79de8fa449a")
    }
}
