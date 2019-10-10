package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback
import com.bitwig.extension.controller.ControllerExtension
import com.bitwig.extension.controller.api.ControllerHost


class MpdExtension(definition: MpdExtensionDefinition, host: ControllerHost) : ControllerExtension(definition, host) {

    override fun init() {
        val navigation = Navigation(host.createArranger())
        val trackHandler = TrackHandler(
                host.createTrackBank(4, 2, 4),
                host.createEffectTrackBank(2, 2),
                host.createCursorTrack("MPD_TRACK_ID", "Cursor Track", 0, 0, true))
        val midiHandler = MidiHandler(host, host.createApplication(), trackHandler, navigation, host.createTransport())

        host.getMidiInPort(0).setMidiCallback(object : ShortMidiMessageReceivedCallback {
            override fun midiReceived(msg: ShortMidiMessage?) {
                if (msg != null) {
                    midiHandler.handleMessage(msg)
                }
            }
        })

        host.getMidiInPort(0).setSysexCallback { data: String -> midiHandler.handleSysexMessage(data) }
        host.showPopupNotification("mpd Initialized")
    }

    override fun exit() {
        host.showPopupNotification("mpd Exited")
    }

    override fun flush() {
        // TODO Send any updates you need here.
    }
}
