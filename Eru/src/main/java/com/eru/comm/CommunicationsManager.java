package com.eru.comm;

import com.eru.comm.member.Communicator;
import com.eru.comm.member.Director;
import com.eru.comm.member.ModbusDeviceCommunicator;
import com.eru.entities.Device;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mtrujillo on 22/05/17.
 */
@Log4j
public class CommunicationsManager {

    private static final CommunicationsManager instance = new CommunicationsManager();
    public static CommunicationsManager getInstance(){
        return instance;
    }
    private final Director director                     = new Director();
    private final Map<Device, Communicator> communicators      = new HashMap<>();

    private CommunicationsManager(){
    }

    private void startDirector() {
        for (Communicator c : communicators.values()) {
            director.getCommunicators().add(c);
        }

        Thread directorThread = new Thread(director, "COMMUNICATIONS_MANAGER");
        directorThread.setDaemon(true);
        directorThread.start();
    }

    private void stopDirector() {
        director.stop();
        director.getCommunicators().clear();
    }

    public void startUpdating(Device device) {
        log.info("Starting to update " + device);
        if (!communicators.isEmpty()) stopDirector();
        communicators.put(device, new ModbusDeviceCommunicator(device));
        startDirector();
    }

    public void stopUpdating(Device device) {
        log.info("Stopping to update " + device);
        stopDirector();
        communicators.remove(device);
        if (!communicators.isEmpty()) startDirector();
    }
}
