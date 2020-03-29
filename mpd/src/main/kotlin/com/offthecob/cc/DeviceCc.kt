package com.offthecob.cc

import com.bitwig.extension.controller.api.CursorRemoteControlsPage
import com.bitwig.extension.controller.api.PinnableCursorDevice
import com.google.inject.Inject
import com.offthecob.*

class DeviceCc @Inject constructor(private val trackHandler: TrackHandler) : ControlChange {
    override fun cc(note: Int, value: Int) {
        when(note){
            KNOB_1 -> trackHandler.knob(handleCc(0, value))
            KNOB_2 -> trackHandler.knob(handleCc(1, value))
            KNOB_3 -> trackHandler.knob(handleCc(2, value))
            KNOB_4 -> trackHandler.knob(handleCc(3, value))
            KNOB_5 -> trackHandler.knob(handleCc(4, value))
            KNOB_6 -> trackHandler.knob(handleCc(5, value))
            KNOB_7 -> trackHandler.knob(handleCc(6, value))
            KNOB_8 -> trackHandler.knob(handleCc(7, value))

            SLIDER_1 -> trackHandler.slider(handleCc(0, value))
            SLIDER_2 -> trackHandler.slider(handleCc(1, value))
            SLIDER_3 -> trackHandler.slider(handleCc(2, value))
            SLIDER_4 -> trackHandler.slider(handleCc(3, value))
            SLIDER_5 -> trackHandler.slider(handleCc(4, value))
            SLIDER_6 -> trackHandler.slider(handleCc(5, value))
        }
    }

    private fun handleCc(parameterNumber: Int, value: Int) : (sliderDevice: PinnableCursorDevice, cursorRemoteControl: CursorRemoteControlsPage)->Unit {
        return {_, cursorRemoteControl ->
            cursorRemoteControl.getParameter(parameterNumber).set(value, 128)
        }
    }
}