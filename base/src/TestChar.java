/**
 * @author goodtime
 * @create 2020-02-29 1:22 下午
 */
public class TestChar {
    public static void main(String[] args) {
        String str = "测";
        char x = '测';
        byte[] byteStr = null;
        byte[] byteChar = null;
        try {
            byteStr = str.getBytes("UTF-8");
            byteChar = charToByte(x);
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println("byteStr ：" + byteStr.length);
        System.out.println("byteChar：" + byteChar.length);
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
}

