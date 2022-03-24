package de.desktop.application.kretzschmar_desktop.data;


import java.io.*;

/**
 * Manage the serialization of the application with generic data types so the entire application work with that.
 */
public class Serializer {
    /**
     * Serialize the generic type to the destinated location that is choosed by the path.
     * @param object The object, that should be serialized.
     * @param path The path where the object should be serialized.
     * @param <T> The generic data type that should be handled.
     */
    public static <T> void createSerializationFile(T object, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize a generic data type from the searched file.
     * Important is, that there can happen runtime errors by calling a type, that is not saved in to the file!
     * @param path The path where the file is destinated.
     * @param <T> The generic that should be saved in the file.
     * @return A new instance of the data type that was stored in the file.
     */
    public static <T> T openSerializationFile(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        T object = (T)ois.readObject();
        return object;
    }
}
