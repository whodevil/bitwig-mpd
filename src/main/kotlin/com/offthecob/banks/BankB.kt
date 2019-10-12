package com.offthecob.banks

import com.offthecob.TrackHandler
import javax.inject.Inject

class BankB @Inject constructor(private val trackHandler: TrackHandler): MpdBank {
    override fun isBank(note: Int): Boolean {
        return (52 <= note) && (note <= 67)
    }

    override fun handle(note: Int) {
        when(note){
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
}