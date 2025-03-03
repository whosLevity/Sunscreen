package me.combimagnetron.sunscreen.util;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

public interface FileProvider {

    File find(String path);

    static FileProvider resource() {
        return new ResourceFileProvider();
    }

    static FileProvider path() {
        return new PathFileProvider();
    }

    class ResourceFileProvider implements FileProvider {

        @Override
        public File find(String path) {
            File file;
            try {
                file = Files.createTempFile("sunscreen", ".tmp").toFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try(OutputStream out = new FileOutputStream(file)){
                IOUtils.copy(SunscreenLibrary.library().resource(path), out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        }

    }

    class PathFileProvider implements FileProvider {

        @Override
        public File find(String path) {
            return SunscreenLibrary.library().path().resolve(path).toFile();
        }

    }

}
