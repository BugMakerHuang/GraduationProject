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
        ExecutorService service = Executors.newCachedThreadPool(); //����һ���̳߳�
        final CountDownLatch cdOrder = new CountDownLatch(1);//ָ�ӹٵ��������Ϊ1��ָ�ӹ�һ�´������cutDown,��Ϊ0��սʿ��ִ������
        final CountDownLatch cdAnswer = new CountDownLatch(3);//��Ϊ������սʿ�����Գ�ʼֵΪ3��ÿһ��սʿִ�����������cutDownһ�Σ���������ִ����ϣ���Ϊ0����ָ�ӹ�ֹͣ�ȴ���
        for(int i=0;i<1000000000;i++){
            Runnable runnable = () -> {
                try {
                    System.out.println("�߳�" + Thread.currentThread().getName() +
                            "��׼����������");
                    cdOrder.await(); //սʿ�Ƕ����ڵȴ�����״̬
                    System.out.println("�߳�" + Thread.currentThread().getName() +
                            "�ѽ�������");
                    Thread.sleep((long)(Math.random()*10000));
                    System.out.println(interfaceUtil("http://localhost:30049/caculator/zh",""));
                    System.out.println("�߳�" + Thread.currentThread().getName() +
                            "��Ӧ�������");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdAnswer.countDown(); //����ִ����ϣ����ظ�ָ�ӹ٣�cdAnswer��1��
                }
            };
            service.execute(runnable);//Ϊ�̳߳��������
        }
        try {
            Thread.sleep((long)(Math.random()*10000));

            System.out.println("�߳�" + Thread.currentThread().getName() +
                    "������������");
            cdOrder.countDown(); //�������cdOrder��1�����ڵȴ���սʿ��ֹͣ�ȴ�תȥִ������
            System.out.println("�߳�" + Thread.currentThread().getName() +
                    "�ѷ���������ڵȴ����");
            cdAnswer.await(); //����ͺ�ָ�ӹٴ��ڵȴ�״̬��һ��cdAnswerΪ0ʱֹͣ�ȴ���������ִ��
            System.out.println("�߳�" + Thread.currentThread().getName() +
                    "���յ�������Ӧ���");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        service.shutdown(); //���������ֹͣ�̳߳ص������߳�
    }

    /**
     * ���öԷ��ӿڷ���
     *
     * @param path �Է���������ṩ��·��
     * @param data ��Է�����������͵����ݣ����������¸��Է�����JSON�����öԷ�����
     */
    public String interfaceUtil(String path, String data) {
        try {
            URL url = new URL(path);
            //�򿪺�url֮�������
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            /**����URLConnection�Ĳ�������ͨ����������****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**����URLConnection�Ĳ�������ͨ����������****end***/

            //�����Ƿ���httpUrlConnection����������Ƿ��httpUrlConnection���룬���ⷢ��post�����������������
            //��õ�Http�����޷���get��post��get������Ի�ȡ��̬ҳ�棬Ҳ���԰Ѳ�������URL�ִ����棬���ݸ�servlet��
            //post��get�� ��֮ͬ������post�Ĳ������Ƿ���URL�ִ����棬���Ƿ���http����������ڡ�
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET��POST����ȫ��д
            /**GET��������*****start*/
            /**
             * ���ֻ�Ƿ���GET��ʽ����ʹ��connet����������Զ����Դ֮���ʵ�����Ӽ��ɣ�
             * �������POST��ʽ��������Ҫ��ȡURLConnectionʵ����Ӧ����������������������
             * */
            conn.connect();

            /**GET��������*****end*/

            /***POST��������****start*/

            /*out = new PrintWriter(conn.getOutputStream());//��ȡURLConnection�����Ӧ�������

            out.print(data);//�����������������

            out.flush();//��������
            */
            /***POST��������****end*/

            //��ȡURLConnection�����Ӧ��������
            InputStream is = conn.getInputStream();
            //����һ���ַ�������
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//���������������
                System.out.println(str);
            }
            //�ر���
            is.close();
            //�Ͽ����ӣ����д�ϣ�disconnect���ڵײ�tcp socket���ӿ���ʱ���жϡ�������ڱ������߳�ʹ�þͲ��жϡ�
            //�̶����̵߳Ļ��������disconnect�����ӻ����ֱ࣬���շ�������Ϣ��д��disconnect������һЩ��
            conn.disconnect();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
