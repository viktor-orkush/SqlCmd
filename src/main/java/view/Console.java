package view;

import dnl.utils.text.table.TextTable;

import java.util.List;
import java.util.Scanner;

public class Console implements View {
    @Override
    public void write(String massage) {
        System.out.println(massage);
    }

    @Override
    public void write(String[] headers, Object[][] data) {
        printTable(headers, data);
    }

    @Override
    public void write(String headerString, List<String> data1d) {
        String[] headers = headerString.split(",");

        int columnLength = headers.length;
        int rowLength = data1d.size()/columnLength;

        Object data2d[][] = new Object[columnLength][rowLength];
        for(int i=0; i<columnLength;i++) {
            for (int j = 0; j < rowLength; j++) {
                data2d[i][j] = data1d.get((j * columnLength) + i);
            }
        }
        printTable(headers, data2d);
    }

    private void printTable(String[] headers, Object[][] data2d) {
        TextTable tt = new TextTable(headers, data2d);
        tt.setAddRowNumbering(true);
        tt.printTable(System.out,0);
    }

    @Override
    public String read() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
