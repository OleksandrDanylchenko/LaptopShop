package ua.alexd;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static java.util.Collections.singletonList;

@SpringBootApplication
@EnableAsync
public class Application {
    public Application(@NotNull FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
    }

    // TODO Make distributed services
    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}