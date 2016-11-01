import java.util.Scanner;

/**
 * Created by Nika Doghonadze
 */
public class Timus_1696 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int N = sc.nextInt();
        int K = sc.nextInt();
        int mod = sc.nextInt();
        long[][][] dp = new long[N + 1][K + 1][4];

        for (int n=1; n<=N; n++) {
            for (int k=1; k<=K; k++) {
                dp[n][k][1] = pow(k, n, mod) % mod;
                dp[n][k][2] = (dp[n-1][k][2] + dp[n-1][k-1][1]) % mod;
                dp[n][k][3] = (dp[n-1][k][3] + dp[n-1][k-1][2]) % mod;
            }
        }
        System.out.println((pow(K, N, mod) -  dp[N][K][3]) % mod + 1);
    }

    private static long pow(long base, int exponent, int mod) {
        long result = 1;
        long pow = base;
        while (exponent > 0) {
            if((exponent & 1) == 1) {
                result *= pow;
                result %= mod;
            }
            pow *= pow;
            pow %= mod;
            exponent = exponent >> 1;
        }
        return result;
    }

}
