package io.encedeus.skyhook.container;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.AttachContainerCmd;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.InvocationBuilder;
import com.github.dockerjava.core.exec.AttachContainerCmdExec;
import com.github.dockerjava.core.exec.LogContainerCmdExec;
import com.google.common.io.CharSource;
import io.encedeus.skyhook.docker.DockerClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ContainerService {

    private final io.encedeus.skyhook.docker.DockerClient dockerClient;

    @Autowired
    public ContainerService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public List<Container> listAllContainers() {
        return dockerClient.getInstance().listContainersCmd().withShowAll(true).exec();
    }

    public void createContainer(io.encedeus.skyhook.container.Container container) {
        /*dockerClient.getInstance().createContainerCmd(container.getContainerImage() + ":" + container.getContainerImageVersion() != null ? container.getContainerImageVersion() : "")
                .withCmd(container.getCommand() != null ? container.getCommand() : "")
                //.withName(container.getName())
                .withEnv(container.getEnvironmentVariables() != null ? container.getEnvironmentVariables() : Collections.emptyList())
                .withExposedPorts(ExposedPort.parse(container.getPortBindings()))
                .exec();*/
        dockerClient.getInstance().createContainerCmd(container.getContainerName())
                .withImage(container.getContainerImage())
                .withEnv(container.getEnvironmentVariables())
                .withExposedPorts(ExposedPort.parse(container.getPortBindings()))
                .withCmd(container.getCommand())
                .exec();
    }

    public List<String> getContainerLogs(String id) {
        final List<String> logs = new ArrayList<>();

        LogContainerCmd logContainerCmd = dockerClient.getInstance().logContainerCmd(id)
                .withStdOut(true)
                .withStdErr(true)
                .withTimestamps(true);
        try {
            logContainerCmd.exec(new ResultCallback.Adapter<>() {
                @Override
                public void onNext(Frame object) {
                    logs.add(object.toString());
                }
            }).awaitCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public void deleteContainer(String id) {
        dockerClient.getInstance().removeContainerCmd(id).exec();
    }

    public void startContainer(String id) {
        dockerClient.getInstance().startContainerCmd(id).exec();
    }

    public void stopContainer(String id) {
        dockerClient.getInstance().stopContainerCmd(id).exec();
    }

    public void killContainer(String id) {
        dockerClient.getInstance().killContainerCmd(id).exec();
    }

    public void executeContainerCommand(String id, String command) {
        try {
            dockerClient.getInstance().execCreateCmd()
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
