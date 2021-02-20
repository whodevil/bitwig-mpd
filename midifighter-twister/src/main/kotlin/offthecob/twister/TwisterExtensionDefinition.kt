package offthecob.twister

import com.bitwig.extension.controller.api.ControllerHost
import com.google.inject.Guice
import offthecob.common.*
import java.util.*

class TwisterExtensionDefinition : CommonExtensionDefinition(
        "twister",
        "whodevil",
        UUID.fromString("d55aa17f-5317-4bb0-a8fe-65b81c41a168"),
        HardwareDefinition("DJ TechTools", "twister", "Midi Fighter Twister")
) {
    override fun createInstance(host: ControllerHost): BitwigExtension {
        return object : BitwigExtension(this, host) {
            override fun fetchHandler(host: ControllerHost): MidiHandler {
                val injector = Guice.createInjector(CommonModule(host))
                return injector.getInstance(TwisterMidiHandler::class.java)
            }
        }
    }
}
