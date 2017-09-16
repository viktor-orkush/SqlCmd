package integration;

import controler.Main;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

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

        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                "Существующие команды:\r\n" +
                "\tconnect|sqlcmd|admin|admin\r\n" +
                "\t\tдля  подключения к базе\r\n" +
                "\tlist\r\n" +
                "\t\tдля получения списка всех таблиц базы, к которой подключились\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tдля вывода этого списка на экран\r\n" +
                "\texit\r\n" +
                "\t\tдля выхода из программы\r\n" +
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
        in.add("connect|sqlcmd|admin|admin");
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n"+
                        "Введите команду или help для помощи\r\n" +
                        "[test, users]\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectListFind(){
        //given
        clear();
        in.add("connect|sqlcmd|admin|admin");
        in.add("list");
        in.add("find|users");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "[test, users]\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "---------------------------\r\n"+
                        "name| password| id| \r\n"+
                        "---------------------------\r\n"+
                        "Введите команду или help для помощи\r\n"+
                        "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testIllegalCommand(){
        //given
        clear();
        in.add("connect|sqlcmd|admin|admin");
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
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Введите команду или help для помощи\r\n" +
                "Не удача по причине Cant get connection for model:sqlcmdsdf?loggerLevel=OFF user:admin \r\n" +
                "Повтори попитку\r\n" +
                "Введите команду или help для помощи\r\n", getData());
    }

    @Test
    public void testCreateCommand(){
        //given
        clear();

        in.add("connect|sqlcmd|admin|admin");
        in.add("clear|users");
        in.add("create|users|id|1|name|victor|password|123");
        in.add("find|users");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals(
                "Введите команду или help для помощи\r\n" +
                        "Привет admin\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "Данные очищены!\r\n " +
                        "Введите команду или help для помощи\r\n" +
                        "Данные добавлены!\r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "---------------------------\r\n" +
                        "name| password| id| \r\n" +
                        "---------------------------\r\n" +
                        "victor| 123| 1| \r\n" +
                        "Введите команду или help для помощи\r\n" +
                        "До скорой встречи!\r\n", getData());
    }
}
