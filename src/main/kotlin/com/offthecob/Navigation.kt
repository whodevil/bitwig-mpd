package com.offthecob

import com.bitwig.extension.controller.api.Arranger

class Navigation(private val arranger: Arranger) {

    init {
        arranger.isClipLauncherVisible().markInterested()
    }

    fun toggleClipLauncher() {
        arranger.isClipLauncherVisible().toggle()
    }
}
