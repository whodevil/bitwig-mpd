package offthecob.twister

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.controller.api.ControllerHost
import com.google.inject.Inject
import offthecob.common.MidiHandler
import offthecob.common.NoteData

class TwisterMidiHandler @Inject constructor(private val host: ControllerHost): MidiHandler {
    override fun handleMessage(msg: ShortMidiMessage) {
        msg.statusByte
        host.println("short midi ${msg.channel} ${msg.data1} ${msg.data2}")
    }

    override fun handleSysexMessage(data: String) {
        host.println("sysex ${data}")
    }

    override fun noteInput(): Array<NoteData> {
        return arrayOf()
    }
}
