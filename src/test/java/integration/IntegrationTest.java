package integration;

import controller.Main;
import model.MyPropertiesForTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {
    MyPropertiesForTest prop = MyPropertiesForTest.instance();

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;

    @BeforeClass
    public static void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData()  {
        try {
            return new String (out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testExit(){
        //given
        clear();
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    private void clear() {
        try {
            in.reset();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectList(){
        //given
        clear();
        try {
            in.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        in.add("connect|" + prop.DB_NAME + "|" + prop.DB_USER_NAME +"|"+ prop.DB_PASSWORD);
        in.add("listTB");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n"+
                        "Введите команду или help для помощи\r\n" +
                        "[users]\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectListFind(){
        //given
        clear();
        in.add("connect|" + prop.DB_NAME + "|" + prop.DB_USER_NAME +"|"+ prop.DB_PASSWORD);
        in.add("clear|users");
        in.add("listTB");
        in.add("find|users");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n" +
                        "Введите команду или help для помощи\r\n"+
                        "Данные из таблици users успешго очищены!\r\n"+
                        "Введите команду или help для помощи\r\n" +
                        "   _______________\r\n" +
                        "   | Список таблиц|\r\n" +
                        "   |==============|\r\n" +
                        "1. | users        |\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "---------------------------\r\n"+
                        "id| name| password| \r\n"+
                        "---------------------------\r\n"+
                        "Введите команду или help для помощи\r\n"+
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testIllegalCommand(){
        //given
        clear();
        in.add("connect|" + prop.DB_NAME + "|" + prop.DB_USER_NAME +"|"+ prop.DB_PASSWORD);
        in.add("find");
        in.add("lists");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Несуществующая команда: find\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Несуществующая команда: lists\r\n"+
                        "Введите команду или help для помощи\r\n"+
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectWithError(){
        //given
        clear();
        in.add("connect|sqlcmdsdf|admin|admin");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Введите команду или help для помощи\r\n" +
                "Не удача по причине Не получается подключиться к базе: sqlcmdsdf?loggerLevel=OFF под юзером: admin\r\n" +
                "Повторите попытку\r\n" +
                "Введите команду или help для помощи\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testInsertCommand(){
        //given
        clear();

        in.add("connect|" + prop.DB_NAME + "|" + prop.DB_USER_NAME +"|"+ prop.DB_PASSWORD);
        in.add("clear|users");
        in.add("insert|users|id|1|name|victor|password|123");
        in.add("find|users");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Данные из таблици users успешго очищены!\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Данные успешно добавлены в таблтицу users\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "---------------------------\r\n" +
                        "id| name| password| \r\n" +
                        "---------------------------\r\n" +
                        "1| victor| 123| \r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testCreateDBCommand(){
        //given
        clear();

        in.add("connect|" + prop.GLOBAL_USER_NAME +"|"+ prop.GLOBAL_PASSWORD);
        in.add("createDB|" + prop.DB_NAME_FOR_TEST);
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет postgres\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "База данных testdb успешно создана \r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testCreateTBCommand(){
        //given
        clear();

        in.add("connect|" + prop.DB_NAME_FOR_TEST + "|" + prop.GLOBAL_USER_NAME +"|"+ prop.GLOBAL_PASSWORD);
        in.add("createTB|" + prop.TABLE_NAME_FOR_TEST + "|id|int");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет " + prop.GLOBAL_USER_NAME + "\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Таблица testtb успешно создана\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testDeleteTBCommand(){
        //given
        clear();

        in.add("connect|" + prop.DB_NAME_FOR_TEST + "|" + prop.GLOBAL_USER_NAME +"|"+ prop.GLOBAL_PASSWORD);
        in.add("deleteTB|" + prop.TABLE_NAME_FOR_TEST);
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет " + prop.GLOBAL_USER_NAME + "\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Таблица testtb успешно удалена \r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testDeleteDBCommand(){
        //given
        clear();

        in.add("connect|" + prop.GLOBAL_USER_NAME +"|"+ prop.GLOBAL_PASSWORD);
        in.add("deleteDB|" + prop.DB_NAME_FOR_TEST);
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет postgres\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "База данных testdb успешно удалена \r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }
}
