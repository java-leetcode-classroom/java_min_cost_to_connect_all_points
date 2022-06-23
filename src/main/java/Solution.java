import java.util.*;

public class Solution {
  static class AdjacencyNode {
    private final int Cost;
    private final int Point;

    public AdjacencyNode(int Cost, int Point) {
      this.Cost = Cost;
      this.Point = Point;
    }
  }
  public int minCostConnectPoints(int[][] points) {
    int cost = 0;
    int pointsNum = points.length;
    if (pointsNum == 1) {
      return 0;
    }
    // Step 1: create adjacencyMap
    HashMap<Integer, List<AdjacencyNode>> adjacencyMap = new HashMap<>();
    for (int i = 0; i < pointsNum; i++) {
      int[] point1 = points[i];
      for (int j = i+1; j < pointsNum; j++) {
        int[] point2 = points[j];
        int dist = Math.abs(point1[0]-point2[0]) + Math.abs(point1[1]-point2[1]);
        if (!adjacencyMap.containsKey(i)) {
          adjacencyMap.put(i, new ArrayList<>());
        }
        if (!adjacencyMap.containsKey(j)) {
          adjacencyMap.put(j, new ArrayList<>());
        }
        adjacencyMap.get(i).add(new AdjacencyNode(dist, j));
        adjacencyMap.get(j).add(new AdjacencyNode(dist, i));
      }
    }

    HashSet<Integer> visit = new HashSet<>();
    PriorityQueue<AdjacencyNode> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.Cost));
    queue.add(new AdjacencyNode(0,0));
    // Step 2: Prim's algorithm
    while(visit.size() < pointsNum) {
      AdjacencyNode node = queue.poll();
      if (node != null) {
        if (visit.contains(node.Point)) {
          continue;
        }
        visit.add(node.Point);
        cost += node.Cost;
        List<AdjacencyNode> adjList = adjacencyMap.get(node.Point);
        for (AdjacencyNode adjNode : adjList) {
          if (!visit.contains(adjNode.Point)) {
            queue.add(adjNode);
          }
        }
      }
    }
    return cost;
  }
}
