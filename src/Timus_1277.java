import java.util.Scanner;

public class Timus_1277 {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int nPoliceman = sc.nextInt();
        int nNodes = sc.nextInt();
        int nEdges = sc.nextInt();
        int museum = sc.nextInt() - 1;
        int refuge = sc.nextInt() - 1;
        int[] nodeWeights = new int[nNodes];
        for (int i=0; i<nodeWeights.length; i++) {
            nodeWeights[i] = sc.nextInt();
        }
        nodeWeights[museum] = INFINITY;
        nodeWeights[refuge] = INFINITY;

        int[][] graph = read_graph(nEdges, nNodes, nodeWeights);

        if (museum == refuge){
            System.out.println("NO");
        } else {
            int flow = calcMaxFlow(graph, refuge, museum + nNodes);
            if (flow <= nPoliceman)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }

    private static int[][] read_graph(int nEdges, int nNodes, int[] nodeWeights) {
        int[][] res = new int[2*nNodes][2*nNodes];
        for (int i=0; i<nEdges; i++) {
            int one = sc.nextInt() - 1;
            int two = sc.nextInt() - 1;
            res[one + nNodes][two] = INFINITY;
            res[two + nNodes][one] = INFINITY;
        }

        for (int i = 0; i < nNodes; i++) {
            res[i][i + nNodes] = nodeWeights[i];
        }

        return res;
    }

    private static int calcMaxFlow(int[][] graph, int from, int to) {
        int flow = 0;
        while (true) {
            int curPathFlow = findPath(graph, new boolean[graph.length], from, to, INFINITY);
            if (curPathFlow == 0)
                return flow;
            flow += curPathFlow;
        }
    }

    private static int findPath(int[][] graph, boolean[] visited, int from, int to, int curFlow) {
        if (from == to)
            return curFlow;
        visited[from] = true;
        for (int visitedNode = 0; visitedNode < visited.length; visitedNode++)
            if (!visited[visitedNode] && graph[from][visitedNode] > 0) {
                int flow = Math.min(curFlow, graph[from][visitedNode]);
                int curPathFlow = findPath(graph, visited, visitedNode, to, flow);
                if (curPathFlow > 0) {
                    graph[from][visitedNode] -= curPathFlow;
                    graph[visitedNode][from] += curPathFlow;
                    return curPathFlow;
                }
            }
        return 0;
    }
}
