package de.desktop.application.kretzschmar_desktop.data.user;

import de.desktop.application.kretzschmar_desktop.data.Serializer;
import de.desktop.application.kretzschmar_desktop.data.crypto.Dechiffer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Provides all methods to create, adjust or delete users from the system.
 * It uses the User class for the handle of the data.
 *
 * The data storage will be actually local on the computer.
 * This state of the art will be changed, until the backend of the application was moved to a server.
 *
 * Note:
 * The class will need to be changed, until the application is moved to the server.
 */
public class UserController {

    /**
     * Create a new user and add it to the file system.
     * The file will be created local on the workstation. The data will be stored in a xml file, that will be
     * encrypted and saved.
     * @param username the name of the user: the username, that he will use to log in
     * @param password the password of the user, that he will use to log in
     * @param rights The rights, that the user will have in the application
     */
    public static void createUser(final String username, final String password, final UserRights rights)
        throws UsernameTakenException {
        User _user = new User(username, password, rights); // the object, that we will working with
        if (!usernameAvailable(username)) {
            throw new UsernameTakenException(username);
        }
        //the encryption and serialization will happen in this method
        FileManager.saveUser(_user);
    }

    public static User getUser(final String username) throws UserNotFoundException {
        return FileManager.openUser(username);
    }

    //check if the username is already taken and returns depending on this true or false.
    private static boolean usernameAvailable(final String username) {
        for(File file : Objects.requireNonNull(new File(FileManager.USER_DATA_PATH).listFiles())) {
            if (file.getName().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * It will contain all the methods to do the file management of the user data.
     */
    static class FileManager {
        private static final String USER_DATA_PATH = "data/user";

        /**
         * Serialize the user to a binary file. It will also before this process encrypt all the data of the user.
         * @param user The user that is going to be serialized.
         */
        private static void saveUser(final User user) {
            //create the file and will encrypt all the data from the user.
            String path = USER_DATA_PATH + "/" + user.username + ".kretzs";
            String username = user.username;
            String password = user.password;
            String encryptedUsername = Dechiffer.encryptContent(username);
            String encryptedPassword = Dechiffer.encryptContent(password);

            user.username = encryptedUsername;
            user.password = encryptedPassword;

            Serializer.createSerializationFile(user, path);
        }

        /**
         * Get the searched user and deserialize it. It will also the deserialized data decrypt to make it readable again.
         * @param username The username of the searched user.
         * @return The searched user as a user instance.
         */
        private static User openUser(final String username) throws UserNotFoundException {
            String path = USER_DATA_PATH + "/" + username + ".kretzs";
            User user;
            try {
                //get the user from the file and decrypt all the contained data.
                user = Serializer.openSerializationFile(path);
                String decryptedUsername = Dechiffer.decryptContent(user.username);
                String decryptedPassword = Dechiffer.decryptContent(user.password);
                user.username = decryptedUsername;
                user.password = decryptedPassword;
                return user;
            } catch (IOException | ClassNotFoundException e) {
                if(e instanceof IOException) {
                    throw new UserNotFoundException("The username is not taken!", username, null);
                }
                e.printStackTrace();
            }
            return null;
        }
    }
}

