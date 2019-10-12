package com.offthecob.cc

import com.google.inject.Inject
import com.offthecob.*

class VolumeTrackSendsCc @Inject constructor(private val trackHandler: TrackHandler): ControlChange {
    override fun cc(note: Int, value: Int) {
        when(note) {
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
}