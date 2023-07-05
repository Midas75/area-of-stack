import org.szj.AreaOfStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void test(int testSize){
        List<int[]> list=new ArrayList<>(testSize);
        Random random=new Random();
        for(int i=0;i<testSize;i++){
            list.add(new int[]{i,i,i+random.nextInt(testSize),i+random.nextInt(testSize)});
        }
        long startTime=System.nanoTime();
        AreaOfStack.processBatch(list);
        long endTime=System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println(testSize+","+ executionTime*1000);
    }
    public static void main(String[] args) {
        int testSizesSizes=50;
        int testSizesStep=50;
        List<Integer> testSizes=new ArrayList<>();
        for(int i=1;i<testSizesSizes;i++){
            testSizes.add(i*testSizesStep);
        }
        for(int testSize:testSizes){
            test(testSize);
        }
    }
}
