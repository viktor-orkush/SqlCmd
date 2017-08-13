package model;

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
    int freeIndex = 0;

    public void put(String name, Object value) {
        boolean updated = false;
        for (int i = 0; i < freeIndex; i++) {
            if(data[i].getName().equals(name)){
                data[i].value = value;
                updated = true;
            }
        }
        if(!updated){
            data[freeIndex++] = new Data(name, value);
        }
    }

    public Object get(String name) {
        for (int i = 0; i < freeIndex; i++) {
            if(data[i].getName().equals(name)){
                return data[i].getValue();
            }
        }
        return 0;
    }

    public void updateFrome(DataSet newValue) {
        for (int i = 0; i < newValue.freeIndex; i++) {
            Data data = newValue.data[i];
            this.put(data.name, data.value);
        }
    }

    public String[] getNames() {
        String[] result = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public Object[] getValues() {
        Object[] result = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(getNames()) + " " + Arrays.toString(getValues());
    }
}
