import java.util.*;
import java.io.*;

class Solution {
	public static int[][] map, timer, tmp;
	public static int N, M, K;
	public static int[] dy = { 0, 0, -1, 1 };
	public static int[] dx = { -1, 1, 0, 0 };
	public static Cell[][] cell;
	public static Queue<Cell> queue, waitQueue;

	public static enum CellType {
		none, wait, inact, act, die
	};

	public static class Cell {
		int y, x, val, timer;
		CellType state;

		public Cell(int y, int x, int val, int timer, CellType state) {
			this.y = y;
			this.x = x;
			this.val = val;
			this.timer = timer;
			this.state = state;
		}

		public void spend() {
			if (state == CellType.die || state == CellType.none || state == CellType.wait) {
				return;
			}

			timer -= 1;

			if (state == CellType.act && timer == val - 1) {
				spread();
			}

			if (timer == 0) {
				if (state == CellType.inact) {
					timer = val;
					state = CellType.act;
				} else if (state == CellType.act) {
					state = CellType.die;
				}
			}
		}

		public void spread() {
			for (int i = 0; i < 4; i++) {
				int ty = y + dy[i];
				int tx = x + dx[i];

				if (cell[ty][tx].state == CellType.none
						|| (cell[ty][tx].state == CellType.wait && cell[ty][tx].val < val)) {
					cell[ty][tx].state = CellType.wait;
					cell[ty][tx].val = val;
					waitQueue.add(cell[ty][tx]);
				}
			}
		}

		public void awake() {
			state = CellType.inact;
			timer = val;
			queue.add(this);
		}
	}

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			M = sc.nextInt();
			K = sc.nextInt();

			cell = new Cell[N + K * 2][M + K * 2];
			queue = new LinkedList<Cell>();
			waitQueue = new LinkedList<Cell>();

			for (int y = 0; y < N + K * 2; y++) {
				for (int x = 0; x < M + K * 2; x++) {
					cell[y][x] = new Cell(y, x, 0, 0, CellType.none);
				}
			}

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					int n = sc.nextInt();

					if (n != 0) {
						cell[y + K][x + K] = new Cell(y + K, x + K, n, n, CellType.inact);
						queue.add(cell[y + K][x + K]);
					}
				}
			}

			// printMap();

			for (int k = 0; k < K; k++) {
				awakeCell();
				spendTime();
				// printMap();
			}

			System.out.printf("#%d %d\n", test_case, count());
		}
	}

	public static void spendTime() {
		int size = queue.size();

		for (int i = 0; i < size; i++) {
			Cell c = queue.poll();
			c.spend();

			if (c.state != CellType.die) {
				queue.add(c);
			}
		}
	}

	public static void awakeCell() {
		while (!waitQueue.isEmpty()) {
			waitQueue.poll().awake();
		}
	}

	public static void printMap() {
		System.out.println();

		for (int y = 0; y < N + K * 2; y++) {
			for (int x = 0; x < M + K * 2; x++) {
				if (cell[y][x].state == CellType.none) {
					System.out.printf("- ");
				} else if (cell[y][x].state == CellType.wait) {
					System.out.printf("W ");
				} else if (cell[y][x].state == CellType.die) {
					System.out.printf("■ ");
				} else {
					System.out.printf("%d ", cell[y][x].val);
				}
			}

			System.out.printf("\t\t");

			for (int x = 0; x < M + K * 2; x++) {
				if (cell[y][x].state == CellType.none) {
					System.out.printf("- ");
				} else if (cell[y][x].state == CellType.wait) {
					System.out.printf("W ");
				} else if (cell[y][x].state == CellType.die) {
					System.out.printf("■ ");
				} else {
					System.out.printf("%d ", cell[y][x].timer);
				}
			}

			System.out.println();
		}

		System.out.println();
	}

	public static int count() {
		int cnt = 0;

		for (int y = 0; y < N + K * 2; y++) {
			for (int x = 0; x < M + K * 2; x++) {
				if (cell[y][x].state == CellType.wait || cell[y][x].state == CellType.inact
						|| cell[y][x].state == CellType.act) {
					cnt += 1;
				}
			}
		}

		return cnt;
	}
}