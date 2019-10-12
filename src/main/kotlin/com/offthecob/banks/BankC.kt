package com.offthecob.banks

import com.offthecob.TrackHandler
import javax.inject.Inject

class BankC @Inject constructor(private val trackHandler: TrackHandler) : MpdBank {
    override fun handle(note: Int) {
        when(note){
            /**
             * 80 81 82 83
             * 76 77 78 79
             * 72 73 74 75
             * 68 69 70 71
             */
            68 -> trackHandler.advanceKnobCursorDevice()
            69 -> trackHandler.advanceKnobDevicePage()
            70 -> trackHandler.enableKnobDeviceToggle()

            76 -> trackHandler.advanceSliderCursorDevice()
            77 -> trackHandler.advanceSliderDevicePage()
            78 -> trackHandler.enableSliderDeviceToggle()
        }
    }

    override fun isBank(note: Int): Boolean {
        return (68 <= note) && (note <= 83)
    }
}