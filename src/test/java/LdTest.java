import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class LdTest {
    @Test
    public void test() {
        System.out.println(sub(new int[]{4, 5, 6, 7, 5, 6, 8}, new int[]{5, 6}));
        System.out.println(sub(new int[]{4, 5, 7, 5, 8}, new int[]{5, 6}));
        System.out.println(sub(new int[]{4, 5, 6, 7, 5, 6, 8}, new int[]{6}));
        System.out.println(sub(new int[]{4, 5, 6, 7, 5, 6, 8}, new int[]{4, 5, 6}));
    }

    @Test
    public void test2() {
        System.out.println(add("1234", "1234"));
    }

    public int sub(int[] a, int[] b) {
        int index = -1;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i + j] != b[j])
                    break;
                if (j == b.length - 1)
                    index = i;
            }
        }
        if (index != -1)
            return index;
        return -1;
    }

    public static String add(String numberA, String numberB) {
        Pattern pattern = Pattern.compile("[0-9]*");

        if (pattern.matcher(numberA).matches()) {
            return new BigDecimal(numberA).add(new BigDecimal(numberB)).toString();
        }
        return "ERROR";
    }

    @Test
    public void getUrl() {
        List<String> testLink = new LinkedList<>();
        while (true) {
            testLink.add(interfaceUtil("http://localhost:30049/caculator/zh", ""));
        }
    }

    @Test
    public void countDownLatchTest() {
        ExecutorService service = Executors.newCachedThreadPool(); //创建一个线程池
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务
        final CountDownLatch cdAnswer = new CountDownLatch(3);//因为有三个战士，所以初始值为3，每一个战士执行任务完毕则cutDown一次，当三个都执行完毕，变为0，则指挥官停止等待。
        for(int i=0;i<1000000000;i++){
            Runnable runnable = () -> {
                try {
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "正准备接受命令");
                    cdOrder.await(); //战士们都处于等待命令状态
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "已接受命令");
                    Thread.sleep((long)(Math.random()*10000));
                    System.out.println(interfaceUtil("http://localhost:30049/caculator/zh",""));
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "回应命令处理结果");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdAnswer.countDown(); //任务执行完毕，返回给指挥官，cdAnswer减1。
                }
            };
            service.execute(runnable);//为线程池添加任务
        }
        try {
            Thread.sleep((long)(Math.random()*10000));

            System.out.println("线程" + Thread.currentThread().getName() +
                    "即将发布命令");
            cdOrder.countDown(); //发送命令，cdOrder减1，处于等待的战士们停止等待转去执行任务。
            System.out.println("线程" + Thread.currentThread().getName() +
                    "已发送命令，正在等待结果");
            cdAnswer.await(); //命令发送后指挥官处于等待状态，一旦cdAnswer为0时停止等待继续往下执行
            System.out.println("线程" + Thread.currentThread().getName() +
                    "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        service.shutdown(); //任务结束，停止线程池的所有线程
    }

    /**
     * 调用对方接口方法
     *
     * @param path 对方或第三方提供的路径
     * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
     */
    public String interfaceUtil(String path, String data) {
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            /*out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据
            */
            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
                System.out.println(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
