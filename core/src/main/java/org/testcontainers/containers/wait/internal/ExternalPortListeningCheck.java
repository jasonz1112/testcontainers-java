package org.testcontainers.containers.wait.internal;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Mechanism for testing that a socket is listening when run from the test host.
 */
@RequiredArgsConstructor
public class ExternalPortListeningCheck implements Callable<Boolean> {
    private final String address;
    private final List<Integer> externalLivenessCheckPorts;

    @Override
    public Boolean call() {
        for (Integer externalPort : externalLivenessCheckPorts) {
            try {
                new Socket(address, externalPort).close();
            } catch (IOException e) {
                throw new IllegalStateException("Socket not listening yet: " + externalPort);
            }
        }
        return true;
    }
}