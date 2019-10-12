package com.offthecob.cc

import com.google.inject.Inject
import com.offthecob.*

class DeviceCc @Inject constructor(private val trackHandler: TrackHandler) : ControlChange {
    override fun cc(note: Int, value: Int) {
        when(note){
            KNOB_1 -> trackHandler.deviceKnob(0, value)
            KNOB_2 -> trackHandler.deviceKnob(1, value)
            KNOB_3 -> trackHandler.deviceKnob(2, value)
            KNOB_4 -> trackHandler.deviceKnob(3, value)
            KNOB_5 -> trackHandler.deviceKnob(4, value)
            KNOB_6 -> trackHandler.deviceKnob(5, value)
            KNOB_7 -> trackHandler.deviceKnob(6, value)
            KNOB_8 -> trackHandler.deviceKnob(7, value)

            SLIDER_1 -> trackHandler.deviceSlider(0, value)
            SLIDER_2 -> trackHandler.deviceSlider(1, value)
            SLIDER_3 -> trackHandler.deviceSlider(2, value)
            SLIDER_4 -> trackHandler.deviceSlider(3, value)
            SLIDER_5 -> trackHandler.deviceSlider(4, value)
            SLIDER_6 -> trackHandler.deviceSlider(5, value)
        }
    }
}