package io.encedeus.skyhook.container;

import com.github.dockerjava.api.model.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/container")
public class ContainerController {

    private final ContainerService containerService;

    @Autowired
    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping
    private List<Container> getAllContainers() {
        return containerService.listAllContainers();
    }

    @PostMapping(path = "create")
    private void createContainer(@RequestBody io.encedeus.skyhook.container.Container container) {
        containerService.createContainer(container);
    }

    @GetMapping(path = "logs/{containerId}")
    private List<String> getContainerLogs(@PathVariable("containerId") String id) {
        return containerService.getContainerLogs(id);
    }

    @DeleteMapping(path = "delete/{containerId}")
    private void deleteContainer(@PathVariable("containerId") String id) {
        containerService.deleteContainer(id);
    }

    @PutMapping(path = "start/{containerId}")
    private void startContainer(@PathVariable("containerId") String id) {
        containerService.startContainer(id);
    }

    @PutMapping(path = "kill/{containerId}")
    private void killContainer(@PathVariable("containerId") String id) {
        containerService.killContainer(id);
    }

    @PutMapping(path = "stop/{containerId}")
    private void stopContainer(@PathVariable("containerId") String id) {
        containerService.stopContainer(id);
    }

    @PutMapping(path = "execCmd/{containerId}")
    private void executeCommand(@PathVariable("containerId") String id, @RequestParam String cmd) {
        containerService.executeContainerCommand(id, cmd);
    }

}
