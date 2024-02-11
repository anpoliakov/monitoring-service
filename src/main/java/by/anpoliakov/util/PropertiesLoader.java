package by.anpoliakov.util;

import by.anpoliakov.infrastructure.constant.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для загрузки properties файлов
 */
public class PropertyLoader {
    /**
     * Метод предоставляющий значение по ключу из
     * properties файла
     *
     * @param keyProperty - ключ по которому из файла достаём property
     * @return строка - значение полученное по переданному ключу
     */
    public static String getProperty(String keyProperty) {
        String valueProperty = null;

        try (FileInputStream fis = new FileInputStream(Constants.PATH_TO_DB_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(fis);

            valueProperty = properties.getProperty(keyProperty);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return valueProperty;
    }
}
