package io.encedeus.skyhook.docker;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DockerClient {

    @Bean
    public com.github.dockerjava.api.DockerClient getInstance() {
        DefaultDockerClientConfig defaultDockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://localhost:2375").build();
        return DockerClientBuilder.getInstance(defaultDockerClientConfig).build();
    }
}
