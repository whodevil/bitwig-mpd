package com.offthecob

import com.bitwig.extension.controller.api.CursorTrack
import com.bitwig.extension.controller.api.Track
import com.bitwig.extension.controller.api.TrackBank

class TrackHandler(private val trackBank: TrackBank, private val effectTrackBank: TrackBank, private val cursorTrack: CursorTrack) {

    init {
        initTracks()
        initEffectTracks()
        cursorTrack.mute().markInterested()
        cursorTrack.solo().markInterested()
        cursorTrack.arm().markInterested()
        cursorTrack.makeVisibleInArranger()
        cursorTrack.makeVisibleInMixer()
    }

    private fun initEffectTracks() {
        var i = 0
        while (i < effectTrackBank.sizeOfBank) {
            val effectTrack = effectTrackBank.getItemAt(i)
            val volume = effectTrack.volume()
            volume.markInterested()
            volume.setIndication(true)
            i++
        }
    }

    private fun initTracks() {
        var i = 0
        while (i < trackBank.sizeOfBank) {
            markTrackInterested(trackBank.getItemAt(i))
            i++
        }
    }

    private fun markTrackInterested(track: Track) {
        val volume = track.volume()
        volume.markInterested()
        volume.setIndication(true)
        markTrackSends(track)
    }

    private fun markTrackSends(track: Track) {
        var j = 0
        val sendBank = track.sendBank()
        while (j < sendBank.sizeOfBank) {
            val send = sendBank.getItemAt(j)
            send.markInterested()
            send.setIndication(true)
            j++
        }
    }

    fun select(i: Int) {
        trackBank.getItemAt(i).selectInEditor()
    }

    fun bankUp() {
        trackBank.scrollBackwards()
    }

    fun bankDown() {
        trackBank.scrollForwards()
    }

    fun toggleArmed() {
        cursorTrack.arm().toggle()
    }

    fun toggleSolo() {
        cursorTrack.solo().toggle()
    }

    fun toggleMute() {
        cursorTrack.mute().toggle()
    }

    fun trackSend(trackNumber: Int, sendNumber: Int, sendValue: Int) {
        val send = trackBank.getItemAt(trackNumber).sendBank().getItemAt(sendNumber)
        send.set(sendValue, 128)
    }

    fun volume(trackNumber: Int, value: Int) {
        trackBank.getItemAt(trackNumber).volume().set(value, 128)
    }

    fun effectTrackVolume(sendNumber: Int, value: Int) {
        effectTrackBank.getItemAt(sendNumber).volume().set(value, 128)
    }

    fun effectBankUp() {
        effectTrackBank.scrollBackwards()
    }

    fun effectBankDown() {
        effectTrackBank.scrollForwards()
    }

    fun trackSendBankUp() {
        var i = 0
        while (i < trackBank.sizeOfBank) {
            val track = trackBank.getItemAt(i)
            track.sendBank().scrollBackwards()
            i++
        }
    }

    fun trackSendBankDown() {
        var i = 0
        while (i < trackBank.sizeOfBank) {
            val track = trackBank.getItemAt(i)
            track.sendBank().scrollForwards()
            i++
        }
    }

    fun launchOrRecord(trackNumber: Int, scene: Int) {
        trackBank.getItemAt(trackNumber).clipLauncherSlotBank().select(scene)
    }
}
