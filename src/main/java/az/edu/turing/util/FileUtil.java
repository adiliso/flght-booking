package az.edu.turing.util;

import az.edu.turing.exception.NotFoundException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil<T> {

    private final Path path;

    public FileUtil(String path) {
        this.path = Paths.get(path);
    }

    public void writeObject(List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path.toFile().toPath()))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> readObject() {
        if (!Files.exists(path)) {
            throw new NotFoundException("File not found");
        }

        List<T> list = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path.toFile().toPath()))) {
            list = (List<T>) ois.readObject();
        } catch (EOFException e) {
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
