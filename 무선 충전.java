import java.util.*;
import java.io.*;
import java.awt.Point;

class Solution {
	public static int M, A;
	public static int[] dy = { 0, -1, 0, 1, 0 };
	public static int[] dx = { 0, 0, 1, 0, -1 };
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	public static int[][] map;
	public static Player[] player;
	public static BatteryCharger[] bc;
	public static Tile[][] tile;
	public static boolean[][] visited;
	public static Queue<Point> queue;

	public static class Player {
		int x, y, charge;
		int[] moves;

		public Player(int x, int y) {
			this.x = x;
			this.y = y;
			moves = new int[M];
			charge = 0;
		}

		public void move(int i) {
			x += dx[moves[i]];
			y += dy[moves[i]];
		}

		public void charge(int i) {
			charge += i;
		}
	}

	public static class BatteryCharger {
		int x, y, c, p, val;

		public BatteryCharger(int x, int y, int c, int p, int val) {
			this.x = x;
			this.y = y;
			this.c = c;
			this.p = p;
			this.val = val;
		}

		public void init() {
			initVisited();

			queue = new LinkedList<Point>();
			queue.add(new Point(x, y));
			visited[x][y] = true;
			tile[x][y].add(val);

			while (!queue.isEmpty()) {
				Point p = queue.poll();

				for (int i = 1; i <= 4; i++) {
					int tx = p.x + dx[i];
					int ty = p.y + dy[i];

					if (visitable(tx, ty) && !visited[tx][ty] && getAbs(x, y, tx, ty) <= c) {
						queue.add(new Point(tx, ty));
						visited[tx][ty] = true;
						tile[tx][ty].add(val);
					}
				}
			}
		}
	}

	public static class Tile {
		int x, y;
		ArrayList<Integer> list;

		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
			list = new ArrayList<Integer>();
		}

		public void add(int n) {
			list.add(n);
		}
	}

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			map = new int[11][11];
			visited = new boolean[11][11];

			M = sc.nextInt();
			A = sc.nextInt();

			player = new Player[2];
			bc = new BatteryCharger[A];
			tile = new Tile[11][11];

			player[0] = new Player(1, 1);
			player[1] = new Player(10, 10);

			for (int i = 0; i < 2; i++) {
				for (int m = 0; m < M; m++) {
					player[i].moves[m] = sc.nextInt();
				}
			}

			for (int a = 0; a < A; a++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				int c = sc.nextInt();
				int p = sc.nextInt();

				bc[a] = new BatteryCharger(x, y, c, p, a);
			}

			for (int y = 1; y <= 10; y++) {
				for (int x = 1; x <= 10; x++) {
					tile[x][y] = new Tile(x, y);
				}
			}

			for (int a = 0; a < A; a++) {
				bc[a].init();
			}

			// printBC();

			// System.out.printf("T = 0\n");
			playerCharge();

			for (int m = 0; m < M; m++) {
				// System.out.printf("T = %d\n", m + 1);

				player[0].move(m);
				player[1].move(m);

				playerCharge();
			}

			System.out.printf("#%d %d\n", test_case, player[0].charge + player[1].charge);
		}
	}

	public static void initVisited() {
		for (int y = 1; y <= 10; y++) {
			for (int x = 1; x <= 10; x++) {
				visited[x][y] = false;
			}
		}
	}

	public static int getAbs(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	public static boolean visitable(int x, int y) {
		return 1 <= x && x <= 10 && 1 <= y && y <= 10;
	}

	public static void playerCharge() {
		int px1 = player[0].x;
		int py1 = player[0].y;
		int px2 = player[1].x;
		int py2 = player[1].y;

		if (tile[px1][py1].list.size() > 0 && tile[px2][py2].list.size() > 0) {
			int max = 0;
			int pc1 = 0;
			int pc2 = 0;

			for (int p1 : tile[px1][py1].list) {
				for (int p2 : tile[px2][py2].list) {
					int v;
					boolean same = false;

					if (p1 == p2) {
						v = bc[p1].p;
						same = true;
					} else {
						v = bc[p1].p + bc[p2].p;
					}

					if (v > max) {
						max = v;

						if (same) {
							pc1 = bc[p1].p / 2;
							pc2 = bc[p2].p / 2;
						} else {
							pc1 = bc[p1].p;
							pc2 = bc[p2].p;
						}
					}
				}
			}

			player[0].charge(pc1);
			player[1].charge(pc2);
		} else {
			if (tile[px1][py1].list.size() > 0) {
				int max = 0;

				for (int a : tile[px1][py1].list) {
					max = Math.max(max, bc[a].p);
				}

				player[0].charge(max);
			}

			else if (tile[px2][py2].list.size() > 0) {
				int max = 0;

				for (int a : tile[px2][py2].list) {
					max = Math.max(max, bc[a].p);
				}

				player[1].charge(max);
			}
		}

		// System.out.printf("A: (%d, %d)\t충전량: %d\n", player[0].x, player[0].y,
		// player[0].charge);
		// System.out.printf("B: (%d, %d)\t충전량: %d\n\n", player[1].x, player[1].y,
		// player[1].charge);
	}

	public static void printBC() {
		System.out.println();

		for (int y = 1; y <= 10; y++) {
			for (int x = 1; x <= 10; x++) {
				System.out.printf("%d ", tile[x][y].list.size());
			}

			System.out.println();
		}

		System.out.println();
	}
}