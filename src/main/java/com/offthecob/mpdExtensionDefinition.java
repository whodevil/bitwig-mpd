package com.offthecob;

import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class mpdExtensionDefinition extends ControllerExtensionDefinition {
    private static final UUID DRIVER_ID = UUID.fromString("f9382f14-c30e-4c1a-9ee9-f79de8fa449a");

    public mpdExtensionDefinition() {
    }

    @Override
    public String getName() {
        return "mpd";
    }

    @Override
    public String getAuthor() {
        return "whodevil";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public UUID getId() {
        return DRIVER_ID;
    }

    @Override
    public String getHardwareVendor() {
        return "offthecob";
    }

    @Override
    public String getHardwareModel() {
        return "mpd";
    }

    @Override
    public int getRequiredAPIVersion() {
        return 9;
    }

    @Override
    public int getNumMidiInPorts() {
        return 1;
    }

    @Override
    public int getNumMidiOutPorts() {
        return 1;
    }

    @Override
    public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType) {
        if (platformType == PlatformType.WINDOWS) {
            // TODO: Set the correct names of the ports for auto detection on Windows platform here
            // and uncomment this when port names are correct.
            // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
        } else if (platformType == PlatformType.MAC) {
            // TODO: Set the correct names of the ports for auto detection on Windows platform here
            // and uncomment this when port names are correct.
            // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
        } else if (platformType == PlatformType.LINUX) {
            // TODO: Set the correct names of the ports for auto detection on Windows platform here
            // and uncomment this when port names are correct.
            // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
        }
    }

    @Override
    public mpdExtension createInstance(final ControllerHost host) {
        return new mpdExtension(this, host);
    }
}
