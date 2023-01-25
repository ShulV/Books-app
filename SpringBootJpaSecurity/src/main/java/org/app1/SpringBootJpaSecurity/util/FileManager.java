package org.app1.SpringBootJpaSecurity.util;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileManager {
    void upload(byte[] bytes, String key) throws IOException;
    Resource download(String key) throws IOException;
    void delete(String key) throws IOException;
    String getDirectoryPath();
    void setDirectoryPath(String directoryPath);
}
