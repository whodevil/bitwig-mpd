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
            // Bank A
            36 -> trackHandler.scrollBackward()
            37 -> trackHandler.bankUp()
            38 -> trackHandler.bankDown()
            39 -> trackHandler.scrollForward()

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
            51 -> application.nextPanelLayout()

            // Bank B
            52 -> trackHandler.playOrRecordClip(3, 0)
            53 -> trackHandler.playOrRecordClip(3, 1)
            54 -> trackHandler.playOrRecordClip(3, 2)
            55 -> trackHandler.playOrRecordClip(3, 3)
            56 -> trackHandler.playOrRecordClip(2, 0)
            57 -> trackHandler.playOrRecordClip(2, 1)
            58 -> trackHandler.playOrRecordClip(2, 2)
            59 -> trackHandler.playOrRecordClip(2, 3)
            60 -> trackHandler.playOrRecordClip(1, 0)
            61 -> trackHandler.playOrRecordClip(1, 1)
            62 -> trackHandler.playOrRecordClip(1, 2)
            63 -> trackHandler.playOrRecordClip(1, 3)
            64 -> trackHandler.playOrRecordClip(0, 0)
            65 -> trackHandler.playOrRecordClip(0, 1)
            66 -> trackHandler.playOrRecordClip(0, 2)
            67 -> trackHandler.playOrRecordClip(0, 3)
        }
    }

    fun handleMessage(msg: ShortMidiMessage) {
        when {
            msg.isNoteOn -> handleNote(msg.data1)
            msg.isControlChange -> handleControlChange(msg.data1, msg.data2)
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
