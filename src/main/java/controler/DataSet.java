package controler;

import java.util.Arrays;

/**
 * Created by Viktor on 08.08.2017.
 */
public class DataSet {

    class Data {
        String name;

        Object value;
        public Data(String name, Object value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

    }

    Data[] data = new Data[100]; //todo change magic number 100
    int index = 0;

    public void put(String name, Object value) {
        data[index++] = new Data(name, value);
    }

    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(getNames()) + " " + Arrays.toString(getValues());
    }
}
