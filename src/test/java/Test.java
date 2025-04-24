import org.szj.AreaOfStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Test {
    public static void test(int testSize) {
        int w = 1920, h = 1080;
        List<int[]> list = new ArrayList<>(testSize);
        Random random = new Random();
        for (int i = 0; i < testSize; i++) {
            int x1 = random.nextInt(w), x2 = random.nextInt(w), y1 = random.nextInt(h), y2 = random.nextInt(h);
            list.add(new int[]{Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2)});
        }
        List<Integer> l1, l2;
        long startTime = System.nanoTime();
        l1 = AreaOfStack.processBatch(list);
        long endTime = System.nanoTime();
        double executionTime1 = (endTime - startTime) / 1_000_000_000.0;
        startTime = System.nanoTime();
        l2 = AreaOfStack.processBatchPlain(list);
        endTime = System.nanoTime();
        double executionTime2 = (endTime - startTime) / 1_000_000_000.0;
        boolean same = l1.equals(l2);
        System.out.printf("%d,%d,%d,%.2f,%.2f\n",
                w, h,
                testSize,
                executionTime1 * 1000,
                executionTime2 * 1000);
    }

    public static void main(String[] args) {
        int testSizesSizes = 200;
        int testSizesStep = 1;
        List<Integer> testSizes = new ArrayList<>();
        for (int i = 1; i <= testSizesSizes; i++) {
            testSizes.add(i * testSizesStep);
        }
        for (int testSize : testSizes) {
            test(testSize);
        }
    }
}
