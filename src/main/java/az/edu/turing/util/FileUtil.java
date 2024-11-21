package az.edu.turing.util;

import az.edu.turing.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil<T> {

    private final Path path;
    private final ObjectMapper mapper;

    public FileUtil(String path) {
        this.path = Paths.get(path);
        this.mapper = new ObjectMapper();
    }

    public void writeObject(List<T> list) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
//            writer.write(mapper.writeValueAsString(list));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
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
//        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
//            list = mapper.readValue(reader, new TypeReference<List<T>>() {
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
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
