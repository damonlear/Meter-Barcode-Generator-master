import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a {

    private static final int[] regexs = {
            3, 1, 3, 1, 3,                              //使用单位代码（5）
            1, 3,                                       //类型代码（2）
            1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3    //产品序列号（14）
    };
    private static final int LEN = 21;

    public static String calcMac(String original) throws Throwable {
        if (original == null || original.length() != LEN) {
            throw new Throwable("输入的数据长度不足21位");
        }

        //第一步：分割原数据
        ArrayList<Integer> list = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d").matcher(original);
        while (matcher.find()) {
            list.add(Integer.parseInt(matcher.group()));
        }

        //第二步：将对应的数字与其加权值依次相乘
        //乘积相加
        int add = 0;
        for (int i = 0; i < LEN; i++) {
            add += (regexs[i] * list.get(i));
        }

        //第三步：用和数处以模数10，取出余数
        //10-
        int i = 10 - add % 10;
        return original + (i == 10 ? 0 : i);
    }

    public static void write2File(String file, String context) {
        try {
            File writeName = new File(file); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖

            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(context); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("深圳市中创电测技术有限公司");
        System.out.println("电能表计量器具条码生产工具");
        System.out.println("版本：V2");
        System.out.println("2019年03月27日修改");

        String str21 = "";
        while (true) {
            System.out.println("请输入21位资产号（不含校验位）");
            Scanner sc21 = new Scanner(System.in);
            str21 = sc21.next();
            if (str21 != null && str21.length() == 21) {
                break;
            }
        }

        int num = 0;
        while (true) {
            System.out.println("请输入生成数量");
            Scanner sc_num = new Scanner(System.in);
            String strNum = sc_num.next();
            if (strNum != null && strNum.length() != 0) {
                try {
                    num = Integer.parseInt(strNum);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }

        System.out.println("生产文件中。。。");
        int index = 0;
        StringBuffer sb = new StringBuffer();
        while (index < num) {
            try {
                String all = calcMac(str21);

                sb.append(all).append("\r\n");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                index++;
                BigDecimal b1 = new BigDecimal(str21);
                BigDecimal one = new BigDecimal("1");

                str21 = b1.add(one).toString();
                System.out.println("数据成功中" + str21);

            }
        }
        write2File("生产的资产编号.txt", sb.toString());
        System.out.println("生产完成");

    }
}
