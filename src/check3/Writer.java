package check3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private static Writer instance = null;


    private Writer() {
    }

    public void clear(String fileName) throws IOException {
        new FileWriter(fileName, false).close();
    }

    public void write(String fileName, String str) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.write(str);
        writer.close();
    }

    public Boolean isEmpty(String fileName) throws IOException {
        File f = new File(fileName);
        return f.length() == 0;
    }

    public static Writer getInstance() {
        if (instance == null) {
            instance = new Writer();
        }

        return instance;
    }
}
