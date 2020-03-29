package offthecob.mpd

import com.bitwig.extension.controller.api.ControllerHost
import com.google.inject.Guice
import offthecob.common.*
import java.util.*

class MpdExtensionDefinition : CommonExtensionDefinition(
        "mpd",
        "whodevil",
        UUID.fromString("f9382f14-c30e-4c1a-9ee9-f79de8fa449a"),
        HardwareDefinition("Akai", "mpd24", "Akai MPD24")
) {
    override fun createInstance(host: ControllerHost): BitwigExtension {
        return object : BitwigExtension(this, host) {
            override fun fetchHandler(host: ControllerHost): MidiHandler {
                val injector = Guice.createInjector(CommonModule(host))
                return injector.getInstance(MpdMidiHandler::class.java)
            }
        }
    }
}
