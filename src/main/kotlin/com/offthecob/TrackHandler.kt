package com.offthecob

import com.bitwig.extension.controller.api.*

class TrackHandler(
        private val trackBank: TrackBank,
        private val effectTrackBank: TrackBank,
        private val cursorTrack: CursorTrack,
        private val cursorDevice: PinnableCursorDevice,
        private val remoteControlsPage: CursorRemoteControlsPage) {

    init {
        initTracks()
        initEffectTracks()
        initCursorTrack()
        initRemoteControlsPage()
        cursorDevice.isEnabled.markInterested()
        cursorDevice.isWindowOpen.markInterested()
    }

    private fun initRemoteControlsPage() {
        var i = 0
        while(i < remoteControlsPage.parameterCount) {
            val parameter = remoteControlsPage.getParameter(i)
            parameter.markInterested()
            parameter.setIndication(true)
            i++
        }
    }

    private fun initCursorTrack() {
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
        markClips(track.clipLauncherSlotBank())
        markTrackSends(track)
    }

    private fun markClips(clipBank: ClipLauncherSlotBank) {
        clipBank.setIndication(true)
        var i = 0
        while(i<clipBank.sizeOfBank) {
            val clip = clipBank.getItemAt(i)
            clip.hasContent().markInterested()
            clip.isPlaying.markInterested()
            clip.isPlaybackQueued.markInterested()
            clip.isRecording.markInterested()
            clip.isStopQueued.markInterested()
            clip.isRecordingQueued.markInterested()
            i++
        }
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

    fun scrollForward() {
        trackBank.sceneBank().scrollForwards()
    }

    fun scrollBackward() {
        trackBank.sceneBank().scrollBackwards()
    }

    fun playOrRecordClip(trackNumber: Int, clipIndex: Int) {
        val track = trackBank.getItemAt(trackNumber)
        val clip = track.clipLauncherSlotBank().getItemAt(clipIndex)

        if(!clip.hasContent().get()) {
            track.arm().set(true)
            clip.launch()
        } else if (clip.isRecording.get()||!clip.isPlaying.get()) {
            clip.launch()
        } else {
            track.stop()
        }
    }

    fun deviceKnob(parameterNumber: Int, value: Int) {
        remoteControlsPage.getParameter(parameterNumber).set(value, 128)
    }

    fun advanceDevicePage() {
        remoteControlsPage.selectNextPage(true)
    }
}
