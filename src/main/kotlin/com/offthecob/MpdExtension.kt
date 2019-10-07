package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback
import com.bitwig.extension.controller.ControllerExtension
import com.bitwig.extension.controller.api.ControllerHost
import com.bitwig.extension.controller.api.Transport


class MpdExtension(definition: MpdExtensionDefinition, host: ControllerHost) : ControllerExtension(definition, host) {

    private var mTransport: Transport? = null

    override fun init() {
        mTransport = host.createTransport()
        val trackHandler = TrackHandler(
                host.createTrackBank(4, 2, 4),
                host.createEffectTrackBank(2, 2),
                host.createCursorTrack("MPD_TRACK_ID", "Cursor Track", 0, 0, true))
        val midiHandler = MidiHandler(host, host.createApplication(), trackHandler)


        host.getMidiInPort(0).setMidiCallback(object : ShortMidiMessageReceivedCallback {
            override fun midiReceived(msg: ShortMidiMessage?) {
                if (msg != null) {
                    midiHandler.handleMessage(msg)
                }
            }
        })

        host.getMidiInPort(0).setSysexCallback { data: String -> onSysex0(data) }
        host.showPopupNotification("mpd Initialized")
    }

    override fun exit() {
        host.showPopupNotification("mpd Exited")
    }

    override fun flush() {
        // TODO Send any updates you need here.
    }

    private fun onSysex0(data: String) {

        host.println("sysex: ${data}")
        // MMC Transport Controls:
        when (data) {
            "f07f7f0605f7" -> mTransport!!.rewind()
            "f07f7f0604f7" -> mTransport!!.fastForward()
            "f07f7f0601f7" -> mTransport!!.stop()
            "f07f7f0602f7" -> mTransport!!.play()
            "f07f7f0606f7" -> mTransport!!.record()
        }
    }
}
