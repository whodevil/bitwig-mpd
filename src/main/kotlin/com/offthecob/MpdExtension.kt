package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback
import com.bitwig.extension.controller.ControllerExtension
import com.bitwig.extension.controller.api.ControllerHost
import com.bitwig.extension.controller.api.Transport


class MpdExtension(definition: MpdExtensionDefinition, host: ControllerHost) : ControllerExtension(definition, host) {

    private var mTransport: Transport? = null

    override fun init() {
        val host = host

        mTransport = host.createTransport()
        host.getMidiInPort(0).setMidiCallback(object: ShortMidiMessageReceivedCallback{
            override fun midiReceived(msg: ShortMidiMessage?) {
                if (msg != null) {
                    onMidi0(msg)
                }
            }
        })
        host.getMidiInPort(0).setSysexCallback { data: String -> onSysex0(data) }

        // TODO: Perform your driver initialization here.
        // For now just show a popup notification for verification that it is running.
        host.showPopupNotification("mpd Initialized")
    }

    override fun exit() {
        // TODO: Perform any cleanup once the driver exits
        // For now just show a popup notification for verification that it is no longer running.
        host.showPopupNotification("mpd Exited")
    }

    override fun flush() {
        // TODO Send any updates you need here.
    }

    /**
     * Called when we receive short MIDI message on port 0.
     */
    private fun onMidi0(msg: ShortMidiMessage) {
        val host = host
        when {
            msg.isNoteOn -> host.println("noteon!")
            msg.isControlChange -> host.println("cc!")
            msg.isNoteOff -> host.println("noteoff!")
        }
    }

    /**
     * Called when we receive sysex MIDI message on port 0.
     */
    private fun onSysex0(data: String) {
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
