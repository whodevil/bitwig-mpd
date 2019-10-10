package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.controller.api.Application
import com.bitwig.extension.controller.api.ControllerHost
import com.bitwig.extension.controller.api.Transport

class MidiHandler(
        private val host: ControllerHost,
        private val application: Application,
        private val trackHandler: TrackHandler,
        private val navigation: Navigation,
        private val transport: Transport) {

    fun handleNote(note: Int) {
        host.println("noteon! ${note}")
        when (note) {
            36 -> trackHandler.bankUp()
            37 -> trackHandler.bankDown()
            38 -> application.arrowKeyUp()
            39 -> application.arrowKeyDown()

            40 -> trackHandler.select(0)
            41 -> trackHandler.select(1)
            42 -> trackHandler.select(2)
            43 -> trackHandler.select(3)

            44 -> trackHandler.effectBankUp()
            45 -> trackHandler.effectBankDown()

            46 -> trackHandler.trackSendBankUp()
            47 -> trackHandler.trackSendBankDown()

            48 -> trackHandler.toggleArmed()
            49 -> trackHandler.toggleSolo()
            50 -> trackHandler.toggleMute()
            51 -> host.println("TBD")
        }
    }

    fun handleMessage(msg: ShortMidiMessage) {
        when {
            msg.isNoteOn -> handleNote(msg.data1)
            msg.isControlChange -> handleControlChange(msg.data1, msg.data2)
            msg.isNoteOff -> host.println("noteoff!")
        }
    }

    private fun handleControlChange(cc: Int, value: Int) {
        host.println("cc: ${cc} ${value}")
        when (cc) {
            16 -> trackHandler.volume(0, value)
            17 -> trackHandler.volume(1, value)
            18 -> trackHandler.volume(2, value)
            19 -> trackHandler.volume(3, value)

            20 -> trackHandler.effectTrackVolume(0, value)
            21 -> trackHandler.effectTrackVolume(1, value)

            30 -> trackHandler.trackSend(0, 0, value)
            31 -> trackHandler.trackSend(0, 1, value)
            32 -> trackHandler.trackSend(1, 0, value)
            33 -> trackHandler.trackSend(1, 1, value)
            34 -> trackHandler.trackSend(2, 0, value)
            35 -> trackHandler.trackSend(2, 1, value)
            36 -> trackHandler.trackSend(3, 0, value)
            37 -> trackHandler.trackSend(3, 1, value)
            64 -> trackHandler.launchOrRecord(1, 1)
        }
    }

    fun handleSysexMessage(data: String) {
        host.println("sysex: ${data}")
        // MMC Transport Controls:
        when (data) {
            MPD_BANK_B -> navigation.toggleClipLauncher()
            REWIND -> transport.rewind()
            FAST_FORWARD -> transport.fastForward()
            STOP -> transport.stop()
            PLAY -> transport.play()
            RECORD -> transport.record()
        }
    }
}
