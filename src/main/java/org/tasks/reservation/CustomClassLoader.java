package org.tasks.reservation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {

    private final String directoryPath;

    public CustomClassLoader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            try {
                loadedClass = super.loadClass(name, false);
            } catch (ClassNotFoundException e) {
                loadedClass = load(name);
            }
        }
        if (resolve) {
            resolveClass(loadedClass);
        }
        return loadedClass;
    }

    private Class<?> load(String name) throws ClassNotFoundException {
        // Заменяем точки на слэши для корректного пути
        try {
            String filePath = directoryPath + name.replace('.', File.separatorChar) + ".class";
            System.out.println(filePath);


            // Читаем байты из файла
            byte[] classBytes;
            try (FileInputStream fis = new FileInputStream(filePath)) {
                classBytes = fis.readAllBytes();
            }

            // Определяем класс в JVM
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class " + name + "from Path" + directoryPath + e.getMessage());
        }
    }
}




