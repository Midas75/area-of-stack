package org.szj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<int[]> list=new ArrayList<>();
        list.add(new int[]{100,100,200,200});
        list.add(new int[]{100,100,120,120});
        list.add(new int[]{180,180,220,220});
        list.add(new int[]{110,110,120,120});
        System.out.println(AreaOfStack.processBatch(list));

    }
}
