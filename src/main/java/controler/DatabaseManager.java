package controler;

/**
 * Created by Viktor on 11.08.2017.
 */
public interface DatabaseManager {
    String[] getListTable();

    void clear(String tableName);

    void create(DataSet input);

    DataSet[] getTableData(String tableName);

    void update(String tableName, int id, DataSet newValue);
}
