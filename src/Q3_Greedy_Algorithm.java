import java.util.*;
/*
 * Author:  Bhargav U R
 * Date: 21st April 2020
 */
public class Q3_Greedy_Algorithm {

	public static void main(String[] args) {
		Scanner ip = new Scanner(System.in);
		int a = Integer.parseInt(ip.nextLine());
		HashMap<Integer, ArrayList<Integer>> queues = new HashMap<>();
		ArrayList<Integer> patience_list = new ArrayList<>();
		TreeMap<Integer, ArrayList<Integer>> patience_queue_mapping = new TreeMap<>();
		for (int i = 1; i <= a; i++) {
			queues.put(i, new ArrayList<>());
		}
		for (int i = 1; i <= a; i++) {
			String temp = ip.nextLine();
			String[] temp_arr = temp.split(" ");
			ArrayList<Integer> temp_list = queues.get(i);
			for (int j = 1; j <= Integer.parseInt(temp_arr[0]); j++) {
				int temp_value = Integer.parseInt(temp_arr[j]);
				temp_list.add(temp_value);
				patience_list.add(temp_value);
				if(patience_queue_mapping.containsKey(temp_value)) {
					ArrayList<Integer> temp_map_list = patience_queue_mapping.get(temp_value);
					temp_map_list.add(i);
				}
				
				else {
					ArrayList<Integer> temp_map_list = new ArrayList<>();
					temp_map_list.add(i);
					patience_queue_mapping.put(temp_value, temp_map_list);
				}
			}
		}
		ip.close();

		Collections.sort(patience_list);
		int customers_served = 0;
		
		while (queues.size() > 0) {
			if (patience_list.size() == 0) {
				break;
			}
			
			int least_patience_level = patience_list.get(0);
			if (customers_served >= least_patience_level) {
				break;
			}
			
			List<Integer> queue_index_list = patience_queue_mapping.get(least_patience_level);
			int queue_index = queue_index_list.get(0);
			ArrayList<Integer> current_queue = queues.get(queue_index);
			customers_served++;
			patience_list.remove(current_queue.get(0));
			List<Integer> patience_queue_list_update = patience_queue_mapping.get(current_queue.get(0));
			patience_queue_list_update.remove(0);
			current_queue.remove(0);

			if (current_queue.size() == 0) {
				queues.remove(queue_index);
			}

		}

		System.out.print(customers_served);
	}
}
