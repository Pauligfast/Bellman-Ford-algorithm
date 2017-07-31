package com.company;

//Pavlo Khryshcheniuk


import java.util.*;

class Room {
    public int price;
    public Room prev;
    public int x, y, sum;


    Room(int price, int y, int x) {
        this.price = price;
        this.x = x;
        this.y = y;
        sum = 999999;

    }

}

class Museum {
    public Room matrix[][];
    public int m, n;
    public int sums[][];

    Museum(int m, int n) {
        this.m = m;
        this.n = n;
        matrix = new Room[m][n];
        sums = new int[m][n];
    }

    public boolean bellmanFord() {

        boolean zmiana = true;
        int bigcycle = 0;

        while (zmiana && bigcycle < n * m) {
            zmiana = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > 0 && relax(matrix[i][j], matrix[i - 1][j])) {
                        zmiana = true;
                    }
                    if (j > 0 && relax(matrix[i][j], matrix[i][j - 1])) {
                        zmiana = true;
                    }
                    if (i < m - 1 && relax(matrix[i][j], matrix[i + 1][j])) {
                        zmiana = true;
                    }
                    if (j < n - 1 && relax(matrix[i][j], matrix[i][j + 1])) {
                        zmiana = true;
                    }

                }
            }

        }

        return zmiana;
    }

    public boolean relax(Room room1, Room room2) {
        if (room2.sum > room1.sum + room2.price) {
            room2.sum = room1.sum + room2.price;

            room2.prev = room1;
            return true;
        }
        return false;
    }

    public void remembersums() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                sums[i][j] = matrix[i][j].sum;
            }
        }
    }

    public void resetSum() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                matrix[i][j].sum = 999999;
            }

        }
    }

}

public class Main {
    public static Scanner inScan = new Scanner(System.in);

    public static void main(String[] args) {


        int z = inScan.nextInt();

        while (z-- > 0) {//number of sets
            boolean isCycle = false;

            Museum museum = new Museum(inScan.nextInt(), inScan.nextInt());

            // reading prices
            for (int i = 0; i < museum.m; i++) {
                for (int j = 0; j < museum.n; j++) {

                    museum.matrix[i][j] = new Room(inScan.nextInt(), i, j);


                    if (i > 0 && museum.matrix[i - 1][j].price + museum.matrix[i][j].price < 0) { //check if has cycles
                        isCycle = true;
                    }
                    if (j > 0 && museum.matrix[i][j - 1].price + museum.matrix[i][j].price < 0) {
                        isCycle = true;
                    }

                }
            }


            int k = inScan.nextInt();//number of task
            int counter = 0;
            int tasktab[] = new int[k * 2];
            for (int i = 0; i < k; i++) {// entering tasks to queue
                tasktab[counter++] = inScan.nextInt() - 1;
                tasktab[counter++] = inScan.nextInt() - 1;


            }
            museum.matrix[museum.m - 1][0].sum = 0;

            counter = 0;

            if (!isCycle && !museum.bellmanFord()) {


                museum.remembersums();
                museum.resetSum();
                museum.matrix[0][museum.n - 1].sum = 0;
                museum.bellmanFord();
                for (int i = 0; i < k; i++) {
                    int y = tasktab[counter++];
                    int x = tasktab[counter++];
                    System.out.println((museum.sums[y][x] + museum.matrix[museum.m - 1][0].price) + ", " +
                            (museum.matrix[y][x].sum + museum.matrix[0][museum.n - 1].price - museum.matrix[y][x].price));
                }


            } else {

                System.out.println("CYKL");
            }

        }
    }


}
/*
test

2
5 7
1 -1 3 2 1 20 2
1 21 22 23 1 1 0
0 1 1 24 9 25 26
5 27 -1 2 0 3 -1
1 1 3 1 28 -1 4
3
3 2
5 6
5 7
2 3
1 0 -1
2 3 2
2
1 1
2 2


*/
