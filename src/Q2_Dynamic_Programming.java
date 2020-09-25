import java.util.*;
/*
 * Author:  Bhargav U R
 * Date: 23rd May 2020
 */
public class Q2_Dynamic_Programming {

	public static void main(String[] args) {
		Scanner ip = new Scanner(System.in);
		int a = ip.nextInt();
		int[][] intervals_array = new int[a + 1][2];

		intervals_array[0][0] = Integer.MIN_VALUE;
		;
		intervals_array[0][1] = Integer.MIN_VALUE + 1;
		for (int i = 1; i <= a; i++) {
			intervals_array[i][0] = ip.nextInt();
			intervals_array[i][1] = ip.nextInt();
		}
		ip.close();

		Arrays.sort(intervals_array, new Comparator<int[]>() {
			@Override
			public int compare(final int[] arr_1, final int[] arr_2) {
				final int num_1 = arr_1[1];
				final int num_2 = arr_2[1];
				if (num_1 > num_2)
					return 1;
				else if (num_1 < num_2)
					return -1;
				else
					return 0;
			}
		});

		int[][] dynamic_programming_nxn_array = new int[a + 1][2];
		dynamic_programming_nxn_array[0][0] = 0;
		dynamic_programming_nxn_array[0][1] = 1;
		for (int i = 1; i <= a; i++) {
			dynamic_programming_nxn_array[i][0] = 0;
			dynamic_programming_nxn_array[i][1] = 0;
		}

		for (int i = 1; i <= a; i++) {
			int j = i - 1;
			int latestStartSoFar = intervals_array[i][0];

			while (intervals_array[j][1] >= latestStartSoFar) {
				dynamic_programming_nxn_array[i][0] = dynamic_programming_nxn_array[i][0]
						+ dynamic_programming_nxn_array[j][1];
				latestStartSoFar = Math.max(latestStartSoFar, intervals_array[j][0]);
				j = j - 1;
			}
			j = i - 1;
			int numCoveredIntervals = 0;
			while (intervals_array[j][1] >= intervals_array[i][0]) {
				if (intervals_array[j][0] >= intervals_array[i][0]) {
					numCoveredIntervals = numCoveredIntervals + 1;
				} else {
					dynamic_programming_nxn_array[i][1] = dynamic_programming_nxn_array[i][1]
							+ (int) Math.pow(2, numCoveredIntervals) * dynamic_programming_nxn_array[j][1];
				}
				j = j - 1;
			}
			dynamic_programming_nxn_array[i][1] = dynamic_programming_nxn_array[i][1]
					+ (int) Math.pow(2, numCoveredIntervals)
							* (dynamic_programming_nxn_array[j][0] + dynamic_programming_nxn_array[j][1]);
		}

		System.out.println(dynamic_programming_nxn_array[a][0] + dynamic_programming_nxn_array[a][1]);
	}
}
