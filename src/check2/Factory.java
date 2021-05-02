package check2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Factory implements CSV {
    private final ArrayList<Integer> history = new ArrayList<>();
    abstract int getBiggestId();
    abstract void setBiggestId(int id);

    @Override
    public int loadFromFile(String fileName, int offset) throws IOException {
        Reader reader = Reader.getInstance();
        String[] headers = reader.readLine(fileName, offset);
        for (String s: headers) {
            offset += s.length() + 1;
        }
        offset++; // for the new line character
        String[] body = reader.readLine(fileName, offset);
        setBiggestId(Integer.parseInt(body[0]));

        for (String s: body) {
            offset += s.length() + 1;
        }

        return offset + 1;
    }

    @Override
    public void saveInFile(String fileName) throws IOException {
        Writer writer = Writer.getInstance();
        writer.write(fileName, "biggestId,\n" + getBiggestId() + ",\n");
    }

    protected void addToHistory(Integer id) {
        history.add(id);
    }

    protected ArrayList<Integer> getHistory() {
        return history;
    }
}
