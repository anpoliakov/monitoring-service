package by.anpoliakov.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для загрузки настроек приложения
 */
public class PropertiesLoader {
    private final static String NAME_PROPERTIES_FILE = "application.properties";

    /**
     * Метод предоставляющий значение по ключу из properties файла
     *
     * @param keyProperty - ключ по которому из файла достаём значение
     * @return String - значение полученное по переданному ключу
     */
    public static String getProperty(String keyProperty) {
        String valueProperty = null;
        ClassLoader classLoader = PropertiesLoader.class.getClassLoader();

        try {
            InputStream resourceAsStream = classLoader.getResourceAsStream(NAME_PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            valueProperty = properties.getProperty(keyProperty);

        } catch (NullPointerException e){
            System.err.println("File properties [resources/" + NAME_PROPERTIES_FILE + "] not exist!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return valueProperty;
    }
}
