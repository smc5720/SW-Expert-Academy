import java.util.Scanner;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			String str = sc.next();
			int cnt = 0;

			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == 'x') {
					cnt += 1;
				}
			}

			if (cnt < 8) {
				System.out.printf("#%d YES\n", test_case);
			} else {
				System.out.printf("#%d NO\n", test_case);
			}
		}
	}
}