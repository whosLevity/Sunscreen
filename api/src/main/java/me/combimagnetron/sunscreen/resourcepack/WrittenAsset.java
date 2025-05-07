package me.combimagnetron.sunscreen.resourcepack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

public interface WrittenAsset {

    Path path();

    static WrittenFile file(Path path, CodeBlock content) {
        return WrittenFile.of(path, content);
    }

    static WrittenFolder folder(Path path, Collection<WrittenAsset> files) {
        return WrittenFolder.of(path, files);
    }

    interface WrittenFile extends WrittenAsset {

        CodeBlock content();

        String name();

        static WrittenFile of(Path path, CodeBlock content) {
            return new Impl(path, content);
        }

        record Impl(Path path, CodeBlock content, String name) implements WrittenFile {

            public Impl(Path path, CodeBlock content) {
                this(path, content, path.getFileName().toString());
            }

            public Impl(Path path, CodeBlock content, String name) {
                this.path = path;
                this.content = content;
                this.name = name;
                write();
            }

            void write() {
                try (FileWriter writer = new FileWriter(path.toFile())) {
                    for (Object string : content.content()) {
                        //writer.write(string);
                    }
                    writer.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    interface WrittenFolder extends WrittenAsset {

        Collection<WrittenAsset> files();

        String name();

        static WrittenFolder of(Path path, Collection<WrittenAsset> files) {
            return new Impl(path, files);
        }

        record Impl(Path path, Collection<WrittenAsset> files, String name) implements WrittenFolder {

            public Impl(Path path, Collection<WrittenAsset> files) {
                this(path, files, path.getFileName().toString());
            }

            public Impl(Path path, Collection<WrittenAsset> files, String name) {
                this.path = path;
                this.files = files;
                this.name = name;
                try {
                    write();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            void write() throws IOException {
                File folder = path.toFile();
                if (!folder.exists()) {
                    boolean result = folder.mkdirs();
                    if (!result) {
                        throw new RuntimeException("Failed to create directory: " + folder.getAbsolutePath());
                    }
                }
                for (WrittenAsset file : files) {
                    Files.copy(file.path(), path.resolve(file.path().getFileName()));
                }
            }

        }

    }

}
