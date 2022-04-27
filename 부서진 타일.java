import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
	public static int N, M;
	public static int[][] map;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			M = sc.nextInt();

			map = new int[N][M];

			for (int y = 0; y < N; y++) {
				String v = sc.next();

				for (int x = 0; x < M; x++) {
					if (v.charAt(x) == '#') {
						map[y][x] = 1;
					} else {
						map[y][x] = 0;
					}
				}
			}

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					if (map[y][x] == 1) {
						if (locatable(y, x)) {
							// printMap();
							locate(y, x);
						}
					}
				}
			}

			if (checkClear()) {
				System.out.printf("#%d YES\n", test_case);
			} else {
				System.out.printf("#%d NO\n", test_case);
			}
		}
	}

	public static boolean locatable(int y, int x) {
		for (int i = y; i < y + 2; i++) {
			for (int j = x; j < x + 2; j++) {
				if (!(0 <= i && i < N && 0 <= j && j < M)) {
					return false;
				}

				if (map[i][j] == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public static void locate(int y, int x) {
		for (int i = y; i < y + 2; i++) {
			for (int j = x; j < x + 2; j++) {
				map[i][j] = 0;
			}
		}
	}

	public static boolean checkClear() {
		// printMap();

		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				if (map[y][x] == 1) {
					return false;
				}
			}
		}

		return true;
	}

	public static void printMap() {
		System.out.println();

		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				if (map[y][x] == 1) {
					System.out.printf("□ ");
				} else {
					System.out.printf("■ ");
				}
			}

			System.out.println();
		}

		System.out.println();
	}
}