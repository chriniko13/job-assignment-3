package com.yoti.assignment.sdkbackendtest;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Resources {

    public static String getResourceAsString(String resourceName) {
        try {
            URL resource = Resources.class.getClassLoader().getResource(resourceName);
            Path path = Paths.get(Objects.requireNonNull(resource).toURI());
            return String.join("", Files.readAllLines(path));
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

}
