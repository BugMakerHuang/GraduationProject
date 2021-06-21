import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class LdTest {
    @Test
    public void test(){
        System.out.println(sub(new int[]{4,5,6,7,5,6,8},new int[]{5,6}));
        System.out.println(sub(new int[]{4,5,7,5,8},new int[]{5,6}));
        System.out.println(sub(new int[]{4,5,6,7,5,6,8},new int[]{6}));
        System.out.println(sub(new int[]{4,5,6,7,5,6,8},new int[]{4,5,6}));
    }

    @Test
    public void test2(){
        System.out.println(add("1234","1234"));
    }

    public int sub(int[] a,int[] b){
        int index = -1;

        for(int i=0;i<a.length;i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i + j] != b[j])
                    break;
                if (j == b.length - 1)
                    index  = i;
            }
        }
        if(index != -1)
            return index;
        return -1;
    }

    public static String add(String numberA,String numberB){
        Pattern pattern = Pattern.compile("[0-9]*");

        if(pattern.matcher(numberA).matches()){
            return new BigDecimal(numberA).add(new BigDecimal(numberB)).toString();
        }
        return "ERROR";
    }
}
