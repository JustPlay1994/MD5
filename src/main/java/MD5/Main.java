package MD5;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    static int wirte_num=0;
    static String _answer="";
    static {
//        PropertyConfigurator.configure("log4j.properties");
        PropertyConfigurator.configure("src/main/java/MD5/log4j.properties");
    }
    public static int num = 0;
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        start();
    }
    public static void start() throws IOException, NoSuchAlgorithmException {
        Logger logger = LogManager.getLogger(Main.class);
        Scanner scanner = new Scanner(System.in);
        int min=0;
        int max=0;
        while (true) {
            System.out.println("min length:");
            min = scanner.nextInt();
            if(min<1){
                System.out.println("error:min length can't < 1！");
                continue;
            }
            System.out.println("max length:");
            max = scanner.nextInt();
            if(max<1){
                System.out.println("error:max length can't < 1！");
                continue;
            }
            if(max<min){
                System.out.println("error:max length can't < min length！");
                continue;
            }
            System.out.println("ciphertext string(default input 0):");
            _answer = scanner.next();
            if(_answer.equals("0")){
                _answer ="801f7636625c3355d9f9e3baa746316b";
            }
            System.out.println("How many times is it recorded in log.log(default input 0):");
            wirte_num=scanner.nextInt();
            if(wirte_num==0){
                wirte_num =1000000;
            }

            System.out.println("min length: " + min + " max length" + max);
            System.out.println("md5 ciphertext: " + _answer);
            System.out.println("export into log.log per"+wirte_num);
            System.out.println("input Y(y) to confirm");
            String isY = scanner.next();
            if(isY.equals("y")||isY.equals("Y"))break;
        }
        for(int i = min; i<=max;++i){
            pailie("",i,0);
        }
        logger.error("NOT FOUND");
        logger.error(_answer);
    }
    public static void pailie(String _str, int _len, int _index) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        /*本地拷贝一份*/
        String str = _str;
        /*如果组好字符串的最后一位，则与答案比较*/
        if(_len == _index){
            /*如果匹配上，则退出程序*/
            if(matchMd5(str)){
                System.exit(0);
            }
            return;
        }
        for(int bit = 33; bit <= 126; ++bit){
            char y = (char) bit;
            str+=y;
            pailie(str,_len,_index+1);
            /*剪掉最后一个字符*/
            if(str.equals("")){

            }else if (str.length()==1){
                str="";
            }
            else {
                str = str.substring(0,_index);
            }
        }
    }
    public static boolean matchMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Logger logger = LogManager.getLogger(Main.class);
        num++;
        /*md5答案*/
        String answer = _answer;
        /*加密*/
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte b[] = md.digest();

        int i;

        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        if((num%wirte_num)==0){
            logger.info("num: "+num);
            logger.info("str: "+str);
            logger.info("md5: "+buf);
        }
        String str1 = answer.toLowerCase();
        if (buf.toString().equals(str1)) {
            logger.info("find!");
            logger.info("ans: "+answer);
            logger.error("find!");
            logger.error("str: "+str);
            logger.error("md5: "+buf);
            return true;
        }

        return false;
    }
}
