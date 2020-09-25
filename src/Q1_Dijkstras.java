import java.util.*;
/*
 * Author:  Bhargav U R
 * Date: 21st April 2020
 */
class Node implements Comparable<Node> {
	public Character id;
	public int distance;

	public Node(char id, int distance) {
		super();
		this.id = id;
		this.distance = distance;
	}

	@Override
	public int compareTo(Node g) {
		if (this.distance < g.distance)
			return -1;
		else if (this.distance > g.distance)
			return 1;
		else
			return this.id.compareTo(g.id);
	}
}

class Graph {
	public HashMap<Character, ArrayList<Node>> vertices;

	public Graph() {
		this.vertices = new HashMap<>();
	}

	public void addNode(Character id, ArrayList<Node> adj) {
		this.vertices.put(id, adj);
	}

	public HashMap<Character, Integer> getShortestDistances(char s) {
		HashMap<Character, Integer> shortest_distances = new HashMap<>();
		PriorityQueue<Node> nodes = new PriorityQueue<Node>();

		for (Character vertex : vertices.keySet()) {
			if (vertex == s) {
				shortest_distances.put(vertex, 0);
				nodes.add(new Node(vertex, 0));
			} else {
				shortest_distances.put(vertex, Integer.MAX_VALUE);
				nodes.add(new Node(vertex, Integer.MAX_VALUE));
			}
		}
		HashMap<Character, Node> nodes_map = new HashMap<>();

		for (Node n : nodes) {
			nodes_map.put(n.id, n);
		}

		while (nodes.size() > 0) {
			Node nearest = nodes.poll();

			if (shortest_distances.get(nearest.id) == Integer.MAX_VALUE) {
				break;
			}

			for (Node neighbor : vertices.get(nearest.id)) {
				int temp_dist = shortest_distances.get(nearest.id) + neighbor.distance;
				if (temp_dist < shortest_distances.get(neighbor.id)) {
					shortest_distances.put(neighbor.id, temp_dist);
				}

				Node n = nodes_map.get(neighbor.id);

				if (nodes.remove(n)) {
					n.distance = temp_dist;
					nodes.add(n);
				}

			}
		}
		return shortest_distances;
	}
}

public class Q1_Dijkstras { 

	public static void main(String[] args) {
		Scanner ip = new Scanner(System.in);
		int a = Integer.parseInt(ip.nextLine());
		char[] n1 = new char[a];
		char[] n2 = new char[a];
		int[] d = new int[a];
		for (int i = 0; i < a; i++) {
			String[] temp = ip.nextLine().split(" ");
			n1[i] = temp[0].charAt(0);
			n2[i] = temp[1].charAt(0);
			d[i] = Integer.parseInt(temp[2]);
			temp = null;
		}
		ip.close();

		HashSet<Character> nodes = new HashSet<>();
		for (int i = 0; i < n1.length; i++) {
			nodes.add(n1[i]);
			nodes.add(n2[i]);
		}

		ArrayList<Character> nodes_list = new ArrayList<Character>(nodes);

		Graph g = new Graph();

		for (int i = 0; i < nodes_list.size(); i++) {
			g.addNode(nodes_list.get(i), new ArrayList<>());
		}

		for (int i = 0; i < n1.length; i++) {
			g.vertices.get(n2[i]).add(new Node(n1[i], d[i]));
			g.vertices.get(n1[i]).add(new Node(n2[i], d[i]));
		}

		HashMap<Character, Integer> result = g.getShortestDistances('z');
		
		int min = Integer.MAX_VALUE;
		char min_char = 0;
		for(Map.Entry<Character, Integer> e:result.entrySet()) {
			if(min>e.getValue() && e.getKey()>=65 && e.getKey()<=90) {
				min = e.getValue();
				min_char = e.getKey();
			}
		}
		
		System.out.print(min_char+" "+min);
	}
}