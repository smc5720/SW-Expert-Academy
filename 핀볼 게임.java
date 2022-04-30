import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
	public static int N, by, bx, bd, score, maxScore;
	public static Block[][] block;
	public static int[] dy = { 0, 0, -1, 1 };
	public static int[] dx = { -1, 1, 0, 0 };
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public static class Block {
		int y, x, type;

		public Block(int y, int x, int type) {
			this.y = y;
			this.x = x;
			this.type = type;
		}

		// 공의 방향을 변경한다.
		public void receive() {
			if (1 <= type && type <= 5) {
				score += 1;

				// 공의 방향이 왼쪽이라면
				if (bd == LEFT) {
					if (type == 3 || type == 4 || type == 5) {
						bd = RIGHT;
					}

					if (type == 1) {
						bd = UP;
					}

					if (type == 2) {
						bd = DOWN;
					}

					return;
				}

				// 공의 방향이 오른쪽이라면
				if (bd == RIGHT) {
					if (type == 1 || type == 2 || type == 5) {
						bd = LEFT;
					}

					if (type == 3) {
						bd = DOWN;
					}

					if (type == 4) {
						bd = UP;
					}

					return;
				}

				// 공의 방향이 위쪽이라면
				if (bd == UP) {
					if (type == 1 || type == 4 || type == 5) {
						bd = DOWN;
					}

					if (type == 2) {
						bd = RIGHT;
					}

					if (type == 3) {
						bd = LEFT;
					}

					return;
				}

				// 공의 방향이 아래쪽이라면
				if (bd == DOWN) {
					if (type == 2 || type == 3 || type == 5) {
						bd = UP;
					}

					if (type == 1) {
						bd = RIGHT;
					}

					if (type == 4) {
						bd = LEFT;
					}

					return;
				}
			}

			if (6 <= type && type <= 10) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (block[i][j].type == type && (i != y || j != x)) {
							by = i;
							bx = j;
						}
					}
				}
			}
		}

		// 공의 좌표를 이동시킨다.
		public void send() {
			by += dy[bd];
			bx += dx[bd];

			if (!(0 <= by && by < N && 0 <= bx && bx < N)) {
				score += 1;

				if (bd == UP) {
					bd = DOWN;
				}

				else if (bd == DOWN) {
					bd = UP;
				}

				else if (bd == LEFT) {
					bd = RIGHT;
				}

				else if (bd == RIGHT) {
					bd = LEFT;
				}

				by += dy[bd];
				bx += dx[bd];
			}
		}
	}

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			block = new Block[N][N];
			maxScore = 0;

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					block[y][x] = new Block(y, x, sc.nextInt());
				}
			}

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					for (int i = 0; i < 4; i++) {
						score = 0;

						if (block[y][x].type != 0) {
							continue;
						}

						by = y;
						bx = x;
						bd = i;

						int sy = by;
						int sx = bx;

						do {
							block[by][bx].send();
							block[by][bx].receive();
						} while (!((sy == by && sx == bx) || block[by][bx].type == -1));

						maxScore = Math.max(maxScore, score);
					}
				}
			}

			System.out.printf("#%d %d\n", test_case, maxScore);
		}
	}
}