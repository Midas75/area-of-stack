package org.szj;

import java.util.*;

import static java.lang.Math.*;

public class AreaOfStack {

    /**
     * 离散化
     *
     * @param intersections 交集框组
     * @return 两个TreeSet，分别是X的离散化去重组与Y的离散化去重组
     */
    public static TreeSet<Integer>[] getDiscretion(Collection<int[]> intersections) {
        TreeSet<Integer>[] res = new TreeSet[]{new TreeSet<Integer>(), new TreeSet<Integer>()};
        for (int[] intersection : intersections) {
            res[0].add(intersection[0]);
            res[0].add(intersection[2]);
            res[1].add(intersection[1]);
            res[1].add(intersection[3]);
        }
        return res;
    }

    /**
     * 将二维数据压缩为一维，两个int压缩为一个long
     *
     * @param x 高32位
     * @param y 低32位
     * @return long
     */
    public static long getCode(int x, int y) {
        return ((long) x << 32) | y;
    }

    /**
     * 获取对于指定交集组，重叠部分的尺寸值，原理是扫描每个交集的x与y，计算离散化区间内所有的被覆盖的矩形尺寸并求和
     *
     * @param annotations 标注框组
     * @param discretions 离散化组
     * @return 面积值
     */
    public static List<Integer> getTotalSizes(Collection<int[]> annotations, TreeSet<Integer>[] discretions) {
        List<Integer> res=new ArrayList<>(annotations.size());
        Map<Long, Integer> blocks = new HashMap<>();
        for (int[] annotation : annotations) {
            SortedSet<Integer> subSetX = discretions[0].subSet(annotation[0], false, annotation[2], true);
            SortedSet<Integer> subSetY = discretions[0].subSet(annotation[1], false, annotation[3], true);
            int preX = annotation[0];
            for (Integer x : subSetX) {
                int preY = annotation[1];
                for (Integer y : subSetY) {
                    blocks.compute(getCode(preX, preY), (k, v) -> (v == null) ? 1 : v + 1);
                    preY = y;
                }
                preX = x;
            }
        }
        for(int[] annotation:annotations){
            SortedSet<Integer> subSetX = discretions[0].subSet(annotation[0], false, annotation[2], true);
            SortedSet<Integer> subSetY = discretions[0].subSet(annotation[1], false, annotation[3], true);
            int preX=annotation[0];
            int area=0;
            for (Integer x : subSetX) {
                int preY = annotation[1];
                for (Integer y : subSetY) {
                    if(blocks.get(getCode(preX, preY))>=2){
                        area+=(x - preX) * (y - preY);
                    }
                    preY = y;
                }
                preX = x;
            }
            res.add(area);
        }
        return res;
    }
    public static List<Integer> processBatch(Collection<int[]> annotations) {
        return getTotalSizes(annotations,getDiscretion(annotations));
    }
}
