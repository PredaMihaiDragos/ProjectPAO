package check3;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Reader {
    private static Reader instance = null;

    private Reader() {
    }

    public String[] readLine(String fileName, int offset) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName,"r");
        file.seek(offset);
        String line = file.readLine();
        if(line == null) {
            return null;
        }
        return line.split(",");
    }

      // static method to create instance of Singleton class
    public static Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }

        return instance;
    }
}
