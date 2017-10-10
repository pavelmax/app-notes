package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Loader {

    /**
     * Метод, который загружает FXML-file
     * @param path
     * @return
     * @throws IOException
     */
    public static FXMLLoader loader(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Loader.class.getResource(path));
        return fxmlLoader;
    }
}
