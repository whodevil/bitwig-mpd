package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.controller.api.Application
import com.bitwig.extension.controller.api.ControllerHost
import com.bitwig.extension.controller.api.Transport

enum class Mode {
    VolumeSend,
    Device
}

class MidiHandler(
        private val host: ControllerHost,
        private val application: Application,
        private val trackHandler: TrackHandler,
        private val navigation: Navigation,
        private val transport: Transport) {

    var mode: Mode = Mode.VolumeSend

    fun handleMessage(msg: ShortMidiMessage) {
        when {
            msg.isNoteOn -> handleNote(msg.data1)
            msg.isControlChange -> handleControlChange(msg.data1, msg.data2)
        }
    }

    private fun handleNote(note: Int) {
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

            // Bank C
            68 -> trackHandler.advanceDevicePage()
        }
    }

    private fun handleControlChange(cc: Int, value: Int) {
        host.println("cc: ${cc} ${value}")
        when(mode) {
            Mode.VolumeSend -> volumeTrackSends(cc, value)
            Mode.Device -> deviceControlChange(cc, value)
        }
    }

    private fun deviceControlChange(cc: Int, value: Int) {
        when (cc) {
            KNOB_1 -> trackHandler.deviceKnob(0, value)
            KNOB_2 -> trackHandler.deviceKnob(1, value)
            KNOB_3 -> trackHandler.deviceKnob(2, value)
            KNOB_4 -> trackHandler.deviceKnob(3, value)
            KNOB_5 -> trackHandler.deviceKnob(4, value)
            KNOB_6 -> trackHandler.deviceKnob(5, value)
            KNOB_7 -> trackHandler.deviceKnob(6, value)
            KNOB_8 -> trackHandler.deviceKnob(7, value)

            SLIDER_1 -> trackHandler.deviceKnob(8, value)
            SLIDER_2 -> trackHandler.deviceKnob(9, value)
            SLIDER_3 -> trackHandler.deviceKnob(10, value)
            SLIDER_4 -> trackHandler.deviceKnob(11, value)
            SLIDER_5 -> trackHandler.deviceKnob(12, value)
            SLIDER_6 -> trackHandler.deviceKnob(13, value)
        }
    }

    private fun volumeTrackSends(cc: Int, value: Int) {
        when (cc) {
            KNOB_1 -> trackHandler.trackSend(0, 0, value)
            KNOB_2 -> trackHandler.trackSend(0, 1, value)
            KNOB_3 -> trackHandler.trackSend(1, 0, value)
            KNOB_4 -> trackHandler.trackSend(1, 1, value)
            KNOB_5 -> trackHandler.trackSend(2, 0, value)
            KNOB_6 -> trackHandler.trackSend(2, 1, value)
            KNOB_7 -> trackHandler.trackSend(3, 0, value)
            KNOB_8 -> trackHandler.trackSend(3, 1, value)

            SLIDER_1 -> trackHandler.volume(0, value)
            SLIDER_2 -> trackHandler.volume(1, value)
            SLIDER_3 -> trackHandler.volume(2, value)
            SLIDER_4 -> trackHandler.volume(3, value)
            SLIDER_5 -> trackHandler.effectTrackVolume(0, value)
            SLIDER_6 -> trackHandler.effectTrackVolume(1, value)
        }
    }

    fun handleSysexMessage(data: String) {
        host.println("sysex: ${data}")
        // MMC Transport Controls:
        when (data) {
            MPD_BAND_A -> mode = Mode.VolumeSend
            MPD_BANK_B -> {
                mode = Mode.VolumeSend
                navigation.toggleClipLauncher()
            }
            MPD_BANK_C -> mode = Mode.Device
            REWIND -> transport.rewind()
            FAST_FORWARD -> transport.fastForward()
            STOP -> transport.stop()
            PLAY -> transport.play()
            RECORD -> transport.record()
        }
    }
}
