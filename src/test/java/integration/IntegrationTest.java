package integration;

import controller.Main;
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
        in.add("connect|sqlcmd|admin|admin");
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
        in.add("connect|sqlcmd|admin|admin");
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
                        "[users]\r\n" +
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
    public void testCreateCommand(){
        //given
        clear();

        in.add("connect|sqlcmd|admin|admin");
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
}
