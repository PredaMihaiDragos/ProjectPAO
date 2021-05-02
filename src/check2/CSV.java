package check2;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CSV {
    int loadFromFile(String fileName, int offset) throws IOException;
    void saveInFile(String fileName) throws IOException;
}
