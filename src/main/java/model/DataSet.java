package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;

            if (name != null ? !name.equals(data.name) : data.name != null) return false;
            return value != null ? value.equals(data.value) : data.value == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    List<Data> dataList = new LinkedList<>();

    public void put(String name, Object value) {
        boolean updated = false;
        for(Data data : dataList) {
            if (data.getName().equals(name)) {
                data.value = value;
                updated = true;
            }
        }
        if(!updated){
             dataList.add(new Data(name, value));
        }
    }

    public Object get(String name) {
        for (Data data : dataList) {
            if(data.getName().equals(name)){
                return data.getValue();
            }
        }
        return 0;
    }

    public void updateFrome(DataSet newValue) {
        for(Data data : newValue.dataList){
            this.put(data.name, data.value);
        }
    }

    public String[] getNames() {
        String[] result = new String[dataList.size()];
        int index = 0;
        for (Data data : dataList) {
            result[index++] = data.getName();
        }
        return result;
    }

    public Object[] getValues() {
        Object[] result = new Object[dataList.size()];
        int index = 0;
        for (Data data : dataList) {
            result[index++] = data.getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(getNames()) + " " + Arrays.toString(getValues());
    }
}
