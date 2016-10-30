import java.util.Scanner;

/**
 * Created by Nika Doghonadze
 */
public class Timus_1716 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        double n = sc.nextInt();
        double s = sc.nextInt();
        double nYes = s - 2*n;
        double nNo = n - nYes;

        double res = n*(1 - (nYes*(nYes-1)/(n*n) + nNo*(nNo-1)/(n*n) + nYes/(n*n)));
        System.out.println(res);
    }
}
