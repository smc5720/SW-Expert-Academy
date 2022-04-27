import java.util.*;
import java.io.*;
import java.awt.*;

class Solution {
	public static int N, W, H, minVal;
	public static int[][] map;
	public static int[] dy = { 0, 0, -1, 1 };
	public static int[] dx = { -1, 1, 0, 0 };
	public static boolean[][] visited;
	public static Queue<Point> queue;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			W = sc.nextInt();
			H = sc.nextInt();

			map = new int[H][W];
			visited = new boolean[H][W];

			minVal = Integer.MAX_VALUE;

			for (int y = 0; y < H; y++) {
				for (int x = 0; x < W; x++) {
					map[y][x] = sc.nextInt();
				}
			}

			DFS(0);
			System.out.printf("#%d %d\n", test_case, minVal);
		}
	}

	public static void DFS(int cnt) {
		if (cnt == N) {
			int remain = getBlock();
			minVal = Math.min(minVal, remain);

			return;
		}

		int[][] tmp = new int[H][W];

		for (int i = 0; i < W; i++) {
			check(i);
			copyArray(map, tmp);
			boom();
			moveDown();
			DFS(cnt + 1);
			copyArray(tmp, map);
		}
	}

	public static void check(int x) {
		initVisited();

		for (int y = 0; y < H; y++) {
			if (map[y][x] != 0) {
				BFS(y, x);
				break;
			}
		}
	}

	public static void BFS(int y, int x) {
		queue = new LinkedList<Point>();
		queue.add(new Point(x, y));
		visited[y][x] = true;

		while (!queue.isEmpty()) {
			Point p = queue.poll();

			for (int j = 0; j < 4; j++) {
				for (int i = 0; i < map[p.y][p.x]; i++) {
					int ty = p.y + dy[j] * i;
					int tx = p.x + dx[j] * i;

					if (visitable(ty, tx)) {
						visited[ty][tx] = true;
						queue.add(new Point(tx, ty));
					}
				}
			}
		}
	}

	public static boolean visitable(int y, int x) {
		return 0 <= y && y < H && 0 <= x && x < W && !visited[y][x] && map[y][x] != 0;
	}

	public static void initVisited() {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				visited[y][x] = false;
			}
		}
	}

	public static void copyArray(int[][] all, int[][] cpy) {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				cpy[y][x] = all[y][x];
			}
		}
	}

	public static void boom() {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (visited[y][x]) {
					map[y][x] = 0;
				}
			}
		}
	}

	public static int getBlock() {
		int cnt = 0;

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (map[y][x] != 0) {
					cnt += 1;
				}
			}
		}

		return cnt;
	}

	public static void printMap() {
		System.out.println();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (map[y][x] == 0) {
					System.out.printf("- ");
				} else {
					System.out.printf("%d ", map[y][x]);
				}
			}

			System.out.printf("\t\t");

			for (int x = 0; x < W; x++) {
				if (visited[y][x]) {
					System.out.printf("■ ");
				} else {
					System.out.printf("□ ");
				}
			}

			System.out.println();
		}

		System.out.println();
	}

	public static void moveDown() {
		for (int x = 0; x < W; x++) {
			for (int y = H - 1; y >= 0; y--) {
				if (map[y][x] == 0) {
					for (int k = y - 1; k >= 0; k--) {
						if (map[k][x] != 0) {
							map[y][x] = map[k][x];
							map[k][x] = 0;
							break;
						}
					}
				}
			}
		}
	}
}