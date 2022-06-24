package io.encedeus.skyhook.container;

import lombok.Data;

import java.util.List;

@Data public class Container {

    private String containerId;
    private String containerName;
    private String containerImage;
    private String containerImageVersion;
    private Integer cpuCoresLimit;
    private Integer memoryLimit;
    private Integer diskLimit;
    private List<String> environmentVariables;
    private String portBindings;
    private String command;

}
