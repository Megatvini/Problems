import java.util.*;

public class Timus_1736 {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int num_teams = sc.nextInt();
        int[] scores = new int[num_teams];
        int scoreSum = readScores(scores);
        solve(num_teams, scores, scoreSum);
    }

    private static int readScores(int[] scores) {
        int scoreSum = 0;
        for (int i = 0; i < scores.length; i++) {
            scores[i] = sc.nextInt();
            scoreSum += scores[i];
        }
        return scoreSum;
    }

    private static void solve(int num_teams, int[] scores, int scoreSum) {
        int num_games = num_teams * (num_teams - 1) / 2;
        int num_nodes = num_games + num_teams + 2;
        List<Edge>[] graph = createGraph(num_nodes);
        fillGraph(graph, num_nodes, num_games, num_teams, scores);
//        printGraph(graph);
        int maxFlow = maxFlow(graph, 0, num_nodes - 1);
        printRes(scoreSum, num_games, graph, maxFlow);
    }

    private static void printGraph(List<Edge>[] graph) {
        for (int i=0; i<graph.length; i++) {
            System.out.print("node: " + i + " [");
            for (Edge edge : graph[i]) {
                System.out.print(edge.t + " ");
            }
            System.out.println("]");
        }
    }

    private static void printRes(int scoreSum, int num_games, List<Edge>[] graph, int maxFlow) {
        if (maxFlow == scoreSum) {
            System.out.println("CORRECT");
            printSolution(graph, num_games);
        } else {
            System.out.println("INCORRECT");
        }
    }


    private static class Edge {
        int t, rev, cap, f;

        Edge(int t, int rev, int cap) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
        }
    }

    private static List<Edge>[] createGraph(int nodes) {
        List<Edge>[] graph = new List[nodes];
        for (int i = 0; i < nodes; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }

    private static void addEdge(List<Edge>[] graph, int s, int t, int cap) {
        graph[s].add(new Edge(t, graph[t].size(), cap));
        graph[t].add(new Edge(s, graph[s].size() - 1, 0));
    }

    private static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.length];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph[u]) {
                if (dist[e.t] < 0 && e.f < e.cap) {
                    dist[e.t] = dist[u] + 1;
                    Q[sizeQ++] = e.t;
                }
            }
        }
        return dist[dest] >= 0;
    }

    private static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph[u].size(); ++ptr[u]) {
            Edge e = graph[u].get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    graph[e.t].get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }

    private static int maxFlow(List<Edge>[] graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.length];
        while (dinicBfs(graph, src, dest, dist)) {
            int[] ptr = new int[graph.length];
            while (true) {
                int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }

    private static void fillGraph(List<Edge>[] graph, int num_nodes, int num_games, int num_teams, int[] scores) {
        for (int i = 1; i <= num_games; i++) {
            addEdge(graph, 0, i, 3);
        }

        int gameIndex = 1;
        for (int i = 0; i < num_teams; i++) {
            for (int j = 0; j < num_teams; j++) {
                if (i >= j) continue;
                int team1 = i + num_games + 1;
                int team2 = j + num_games + 1;
                addEdge(graph, gameIndex, team1, 3);
                addEdge(graph, gameIndex, team2, 3);
                gameIndex++;
            }
        }

        int end = num_nodes - 1;
        int teamIndex = 0;
        for (int i = gameIndex; i < end; i++) {
            addEdge(graph, i, end, scores[teamIndex]);
            teamIndex++;
        }
    }

    private static void printSolution(List<Edge>[] graph, int num_games) {
        for (int i = 1; i <= num_games; i++) {
            printGameRes(graph[i], num_games);
        }
    }

    private static void printGameRes(List<Edge> edges, int num_games) {
        Edge one = edges.get(1);
        Edge two = edges.get(2);

        int f1 = one.f;
        int f2 = two.f;
        int to1 = one.t - num_games;
        int to2 = two.t - num_games;


        if (Math.abs(f1 - f2) == 3) {
            if (f1 > f2) {
                System.out.println(to1 + " > " + to2);
            } else {
                System.out.println(to1 + " < " + to2);
            }
        } else {
            if (f1 > f2) {
                System.out.println(to1 + " >= " + to2);
            } else {
                System.out.println(to1 + " <= " + to2);
            }
        }
    }
}