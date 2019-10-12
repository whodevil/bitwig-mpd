package com.offthecob.banks

import com.offthecob.TrackHandler
import javax.inject.Inject

class BankC @Inject constructor(private val trackHandler: TrackHandler) : MpdBank {
    override fun handle(note: Int) {
        when(note){
            68 -> trackHandler.advanceKnobDevicePage()
            69 -> trackHandler.advanceSliderDevicePage()

            72 -> trackHandler.advanceKnobCursorDevice()
            73 -> trackHandler.advanceSliderCursorDevice()

            80 -> trackHandler.enableKnobDeviceToggle()
            81 -> trackHandler.enableSliderDeviceToggle()
        }
    }

    override fun isBank(note: Int): Boolean {
        return (68 <= note) && (note <= 83)
    }
}