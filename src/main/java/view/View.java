package view;

import java.util.List;

public interface View {
    void write(String massage);

    void write(String[] headers, Object[][] data);

    void write(String headerString, List<String> data1d);

    String read();
}
