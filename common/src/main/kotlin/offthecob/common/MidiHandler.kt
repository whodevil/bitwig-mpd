package offthecob.common

import com.bitwig.extension.api.util.midi.ShortMidiMessage

interface MidiHandler {
    fun handleMessage(msg: ShortMidiMessage)
    fun handleSysexMessage(data: String)
    fun noteInput(): Array<NoteData>
}

data class NoteData(val name: String, val mask: Array<String>)