import java.util.Scanner;

/**
 * Created by Nika Doghonadze
 */
public class Timus_1167 {
    private static final int INFINITY = 99999999;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int nHorses = sc.nextInt();
        int nStables = sc.nextInt();

        int[] blacks = new int[nHorses + 1];
        int[] whites = new int[nHorses + 1];

        for (int i=1; i<=nHorses; i++) {
            int curHorse = sc.nextInt();
            blacks[i] = blacks[i - 1] + (1 - curHorse);
            whites[i] = whites[i - 1] + curHorse;
        }

        int[][] dp = new int[nStables + 1][nHorses + 1];
        for (int i = 0; i <= nHorses; i++) {
            dp[1][i] = blacks[i]*whites[i];
        }

        for (int i = 2; i <= nStables; i++) {
            for (int j = 1; j <= nHorses; j++) {
                int min = INFINITY;
                for (int k = i - 1; k < j; k++) {
                    int numBlacks = blacks[j]-blacks[k];
                    int numWhite = whites[j]-whites[k];
                    int cur = dp[i-1][k] + numBlacks*numWhite;
                    min = Math.min(cur, min);
                }
                dp[i][j] = min;
            }
        }

        System.out.println(dp[nStables][nHorses]);
    }
}
