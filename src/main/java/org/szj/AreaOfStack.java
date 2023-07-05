package org.szj;


import java.util.*;

public class AreaOfStack {

    /**
     * 离散化
     *
     * @param intersections 交集框组
     * @return 两个TreeSet，分别是X的离散化去重组与Y的离散化去重组
     */
    public static TreeMap<Integer,Integer>[] getDiscretion(Collection<int[]> intersections) {
        TreeMap<Integer,Integer>[] res = new TreeMap[]{new TreeMap<Integer,Integer>(), new TreeMap<Integer,Integer>()};
        for (int[] intersection : intersections) {
            res[0].put(intersection[0],0);
            res[0].put(intersection[2],0);
            res[1].put(intersection[1],0);
            res[1].put(intersection[3],0);
        }
        int index=0;
        for(Map.Entry<Integer,Integer> kv:res[0].entrySet()){
            kv.setValue(index++);
        }
        index=0;
        for(Map.Entry<Integer,Integer> kv:res[1].entrySet()){
            kv.setValue(index++);
        }
        return res;
    }

    /**
     * 将二维数据压缩为一维，两个int压缩为一个long。注意，该方法在某些场景(多组x==y)下极度不适配Long.hashCode方法，因此可能会带来严重的碰撞。
     *
     * @param x 高32位
     * @param y 低32位
     * @return long
     */
    @Deprecated
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
    public static List<Integer> getTotalSizes(Collection<int[]> annotations, TreeMap<Integer,Integer>[] discretions) {
        List<Integer> res=new ArrayList<>(annotations.size());
        int[][] blocks=new int[discretions[0].size()][discretions[1].size()];
        Integer[] xArray= discretions[0].keySet().toArray(new Integer[0]);
        Integer[] yArray= discretions[1].keySet().toArray(new Integer[0]);
        for (int[] annotation : annotations) {
            int i1=discretions[0].get(annotation[0]),i2=discretions[0].get(annotation[2]);
            int j1=discretions[1].get(annotation[1]),j2=discretions[1].get(annotation[3]);
            blocks[i1][j1] += 1;
            blocks[i2][j1] -= 1;
            blocks[i1][j2] -= 1;
            blocks[i2][j2] += 1;
        }
        for(int row=1;row<blocks.length;row++){
            blocks[row][0]+=blocks[row-1][0];
        }
        for(int col=1;col<blocks[0].length;col++){
            blocks[0][col]+=blocks[0][col-1];
        }
        for(int row=1;row<blocks.length;row++){
            for(int col=1;col<blocks[row].length;col++){
                blocks[row][col]+=blocks[row-1][col]+blocks[row][col-1]-blocks[row-1][col-1];
            }
        }
        for(int[] annotation:annotations){
            int area=0;
            int rowMin=discretions[0].get(annotation[0]),rowMax=discretions[0].get(annotation[2]);
            int colMin=discretions[1].get(annotation[1]),colMax=discretions[1].get(annotation[3]);
            for(int row=rowMin;row<rowMax;row++){
                for(int col=colMin;col<colMax;col++){
                    if(blocks[row][col]>=2){
                        int s=(xArray[row+1]-xArray[row])*(yArray[col+1]-yArray[col]);
                        area+=s;
                    }
                }
            }
            res.add(area);
        }
        return res;
    }
    public static List<Integer> processBatch(Collection<int[]> annotations) {
        return getTotalSizes(annotations,getDiscretion(annotations));
    }
}
