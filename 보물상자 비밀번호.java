import java.util.*;
import java.io.*;

class Solution {
	public static int N, K;
	public static int[] number;
	public static HashMap<Character, Integer> map;
	public static PriorityQueue<Long> pqueue;
	public static HashSet<Long> set;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		map = new HashMap<Character, Integer>();

		map.put('0', 0);
		map.put('1', 1);
		map.put('2', 2);
		map.put('3', 3);
		map.put('4', 4);
		map.put('5', 5);
		map.put('6', 6);
		map.put('7', 7);
		map.put('8', 8);
		map.put('9', 9);
		map.put('A', 10);
		map.put('B', 11);
		map.put('C', 12);
		map.put('D', 13);
		map.put('E', 14);
		map.put('F', 15);

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			K = sc.nextInt();

			String pwd = sc.next();
			number = new int[N];

			pqueue = new PriorityQueue<Long>(new Comparator<Long>() {
				@Override
				public int compare(Long a, Long b) {
					return Long.compare(b, a);
				}
			});
			set = new HashSet<Long>();

			for (int i = 0; i < N; i++) {
				number[i] = map.get(pwd.charAt(i));
			}

			int gap = N / 4;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < 4; j++) {
					long num = 0;

					for (int k = j * gap + i; k < j * gap + i + gap; k++) {
						long val = number[k % N];
						long n = (long) Math.pow(16, j * gap + i + gap - 1 - k);

						num += val * n;
					}

					if (!set.contains(num)) {
						set.add(num);
						pqueue.add(num);
					}
				}
			}

			int cnt = 0;

			while (!pqueue.isEmpty()) {
				long num = pqueue.poll();
				cnt += 1;

				if (cnt == K) {
					System.out.printf("#%d %d\n", test_case, num);
					break;
				}
			}
		}
	}
}