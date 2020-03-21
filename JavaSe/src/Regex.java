import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试正则表达式用法
 * @author goodtime
 * @create 2020-03-20 11:59 上午
 */
public class Regex {
    public static void main(String[] args) {
        //string中的转义符，要再加一个\进行转义
        String aaaa = "\\d{3}\\w{0,1}(do)?";

        //第一种方法，调用字符串本身的match方法
        boolean matches = "1111".matches(aaaa);
        System.out.println(matches);

        //第二种方法，直接调用Pattern类的matches静态方法
        Pattern.matches(aaaa,"1111");

        //第三种方法，调用Pattern类的compile方法，生成一个pattern对象,再生成一个匹配对象，看看是否匹配
        Pattern pattern = Pattern.compile(aaaa);

        Matcher matcher = pattern.matcher("1111");

        boolean matches1 = matcher.matches();

        System.out.println(matches1);
    }
}
