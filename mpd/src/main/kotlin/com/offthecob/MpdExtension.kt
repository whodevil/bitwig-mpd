package com.offthecob

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback
import com.bitwig.extension.controller.ControllerExtension
import com.bitwig.extension.controller.api.ControllerHost
import com.google.inject.Guice


class MpdExtension(definition: MpdExtensionDefinition, host: ControllerHost) : ControllerExtension(definition, host) {

    override fun init() {
        val injector = Guice.createInjector(MpdModule(host))
        val midiHandler = injector.getInstance(MidiHandler::class.java)

        host.getMidiInPort(0).setMidiCallback(object : ShortMidiMessageReceivedCallback {
            override fun midiReceived(msg: ShortMidiMessage?) {
                if (msg != null) {
                    midiHandler.handleMessage(msg)
                }
            }
        })

        host.getMidiInPort(0).setSysexCallback { data: String -> midiHandler.handleSysexMessage(data) }
        host.showPopupNotification("mpd Initialized")
    }

    override fun exit() {
        host.showPopupNotification("mpd Exited")
    }

    override fun flush() {
        // TODO Send any updates you need here.
    }
}
