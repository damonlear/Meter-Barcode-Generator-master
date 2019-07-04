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
            3, 1, 3, 1, 3,                              //ʹ�õ�λ���루5��
            1, 3,                                       //���ʹ��루2��
            1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3    //��Ʒ���кţ�14��
    };
    private static final int LEN = 21;

    public static String calcMac(String original) throws Throwable {
        if (original == null || original.length() != LEN) {
            throw new Throwable("��������ݳ��Ȳ���21λ");
        }

        //��һ�����ָ�ԭ����
        ArrayList<Integer> list = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d").matcher(original);
        while (matcher.find()) {
            list.add(Integer.parseInt(matcher.group()));
        }

        //�ڶ���������Ӧ�����������Ȩֵ�������
        //�˻����
        int add = 0;
        for (int i = 0; i < LEN; i++) {
            add += (regexs[i] * list.get(i));
        }

        //���������ú�������ģ��10��ȡ������
        //10-
        int i = 10 - add % 10;
        return original + (i == 10 ? 0 : i);
    }

    public static void write2File(String file, String context) {
        try {
            File writeName = new File(file); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
            writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���

            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(context); // \r\n��Ϊ����
            out.flush(); // �ѻ���������ѹ���ļ�

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("�������д���⼼�����޹�˾");
        System.out.println("���ܱ��������������������");
        System.out.println("�汾��V2");
        System.out.println("2019��03��27���޸�");

        String str21 = "";
        while (true) {
            System.out.println("������21λ�ʲ��ţ�����У��λ��");
            Scanner sc21 = new Scanner(System.in);
            str21 = sc21.next();
            if (str21 != null && str21.length() == 21) {
                break;
            }
        }

        int num = 0;
        while (true) {
            System.out.println("��������������");
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

        System.out.println("�����ļ��С�����");
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
                System.out.println("���ݳɹ���" + str21);

            }
        }
        write2File("�������ʲ����.txt", sb.toString());
        System.out.println("�������");

    }
}
