import java.util.*;
/*
 * Author:  Bhargav U R
 * Date: 21st April 2020
 */
class Node_I{
	int id;
	int l;
	int r;
	public Node_I(int id, int l, int r) {
		this.id = id;
		this.l = l;
		this.r = r;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node_I n = (Node_I) obj;
        if(id==n.id) {
        	return true;
        }
        else {
        	return false;
        }
	}
}

class Graph_I{
	HashMap<Node_I, ArrayList<Node_I>> neighbors_map;
	HashMap<Integer, Node_I> vertex_map;
	
	public Graph_I(){
	  this.neighbors_map = new HashMap<>();
	  this.vertex_map = new HashMap<>();
	}
	
	public boolean checkDominatingSet(HashSet<Integer> current_set, Graph_I g) {
		HashSet<Integer> vertices_of_set_and_neighbors = new HashSet<>(current_set);
		for(Integer vertex_id: current_set) {
			Node_I current_node = g.vertex_map.get(vertex_id);
			ArrayList<Node_I> current_node_neighbors = g.neighbors_map.get(current_node);
			for(int i=0;i<current_node_neighbors.size();i++) {
				Node_I temp = current_node_neighbors.get(i);
				vertices_of_set_and_neighbors.add(temp.id);
			}
		}
		if(vertices_of_set_and_neighbors.size() == g.vertex_map.size()) {
			return true;
		}
		return false;
	}
}

public class Q2_Brute_Force {

	public static void main(String[] args) {
		Scanner ip = new Scanner(System.in);
		int a = ip.nextInt();
		Graph_I g = new Graph_I();
		for(int i=1;i<=a;i++) {
			Node_I n = new Node_I(i, ip.nextInt(), ip.nextInt());
			g.neighbors_map.put(n, new ArrayList<Node_I>());
			g.vertex_map.put(n.id, n);
		}
		ip.close();
		
		for(Map.Entry<Integer, Node_I> n: g.vertex_map.entrySet()) {
			Node_I n_node = n.getValue();
			ArrayList<Node_I> neighbors = g.neighbors_map.get(n_node);
			for(Map.Entry<Integer, Node_I> n_i:g.vertex_map.entrySet()) {
				Node_I n_i_node = n_i.getValue();
				if(!n_node.equals(n_i_node)) {
					if(max(n_node.l,n_i_node.l)<=min(n_node.r,n_i_node.r)) {
						neighbors.add(n_i_node);
					}
				}
			}
		}
		
		Set<Integer> set = g.vertex_map.keySet();
		
		ArrayList<Integer> main_set = new ArrayList<>(set);
		
		HashSet<HashSet<Integer>> subsets = new HashSet<>();
		
		generateSubSets(main_set, subsets);
		
		HashSet<HashSet<Integer>> dominating_sets = new HashSet<>();
		
		for(HashSet<Integer> current_set: subsets) {
			if(checkSubSet(current_set, dominating_sets)) {
				dominating_sets.add(current_set);
			}
			else {
				if(g.checkDominatingSet(current_set, g)) {
					dominating_sets.add(current_set);
				}
				else {
					continue;
				}
			}
		}
		
		System.out.print(dominating_sets.size()%1000000007);
	}
	
	public static boolean checkSubSet(HashSet<Integer> set, HashSet<HashSet<Integer>> subsets) {		
		for(HashSet<Integer> s: subsets) {
			if(isSubSet(s, set)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSubSet(HashSet<Integer> a, HashSet<Integer> b) {
		if(a.containsAll(b))
			return true;
		return false;
	}

	private static void generateSubSets(ArrayList<Integer> vertices_list, HashSet<HashSet<Integer>> subsets) {
		long size = (long) Math.pow(2, vertices_list.size());
		
		for (int i = 0; i < size; i++)
		{
			HashSet<Integer> temp_set = new HashSet<>();
			for (int j = 0; j < vertices_list.size(); j++)
			{
				if ((i & (1 << j)) != 0)
					temp_set.add(vertices_list.get(j));
			}
			subsets.add(temp_set);
		}
	}

	public static int max(int a, int b) {
		if(a>b)
			return a;
		else
			return b;
	}
	
	public static int min(int a, int b) {
		if(a<b)
			return a;
		else
			return b;
	}
}
