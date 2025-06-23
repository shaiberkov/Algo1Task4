package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
//        שאלה מספר 1
        // ABD, BD, D, ABCD, BCD, CD
        System.out.println("Hello world!");


        int V = 6; // מספר הצמתים
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }


        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(1).add(4);
        graph.get(4).add(5);

        int source = 0;
        int[] paths = countPaths(graph, V, source);

        System.out.println("מספר המסלולים שמסתיימים בכל צומת מ-" + source + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("צומת " + i + ": " + paths[i]);
        }


        int n = 5;
        int result = countColorings(n);
        System.out.println("Number of valid colorings for " + n + " cells: " + result);
    }
    public static List<Integer> topologicalSort(List<List<Integer>> graph, int V) {
        int[] inDegree = new int[V];
        for (List<Integer> neighbors : graph) {
            for (int v : neighbors) {
                inDegree[v]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (int v : graph.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return order;
    }

    public static int[] countPaths(List<List<Integer>> graph, int V, int source) {
        int[] p = new int[V];
        p[source] = 1;

        List<Integer> topoOrder = topologicalSort(graph, V);

        for (int u : topoOrder) {
            for (int v : graph.get(u)) {
                p[v] += p[u];
            }
        }

        return p;
    }



    //  שאלה מספר 2
    // T(n)=2T(n-1)+(3^(n-1)-T(n-1))=3^(n-1)+T(n-1)
    public static int countColorings(int n) {
        if (n == 0) return 0;

        int[][] dp = new int[n][3];

        dp[0][0] = 1; // אדום
        dp[0][1] = 1; // כחול
        dp[0][2] = 1; // ירוק

        for (int i = 1; i < n; i++) {
            for (int c = 0; c < 3; c++) {

                for (int prev = 0; prev < 3; prev++) {
                    if (prev != c) {
                        dp[i][c] += dp[i - 1][prev];
                    }
                }
            }
        }

        return dp[n - 1][0] + dp[n - 1][1] + dp[n - 1][2];
    }
}