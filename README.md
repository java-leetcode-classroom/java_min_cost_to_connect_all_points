# java_min_cost_to_connect_all_points

You are given an array `points` representing integer coordinates of some points on a 2D-plane, where `points[i] = [xi, yi]`.

The cost of connecting two points `[xi, yi]` and `[xj, yj]` is the **manhattan distance** between them: `|xi - xj| + |yi - yj|`, where `|val|` denotes the absolute value of `val`.

Return *the minimum cost to make all points connected.* All points are connected if there is **exactly one** simple path between any two points.

## Examples

**Example 1:**

![https://assets.leetcode.com/uploads/2020/08/26/d.png](https://assets.leetcode.com/uploads/2020/08/26/d.png)

```
Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
Output: 20
Explanation:
We can connect the points as shown above to get the minimum cost of 20.
Notice that there is a unique path between every pair of points.

```

![https://assets.leetcode.com/uploads/2020/08/26/c.png](https://assets.leetcode.com/uploads/2020/08/26/c.png)

**Example 2:**

```
Input: points = [[3,12],[-2,5],[-4,1]]
Output: 18

```

**Constraints:**

- `1 <= points.length <= 1000`
- $`-10^6$ <= xi, yi <= $10^6$`
- All pairs `(xi, yi)` are distinct.

## 解析

給定一個整數矩陣 points， 其中每個 entry points[i] = [$x_i, y_i]$ 代表 2 維平面上的座標點

定義平面上認兩點 points[i], points[j] 的 manhattan dist 為 $|x_i - x_j| + |y_i - y_j|$

要求寫一個演算法算出要連接所有座標點

並且唯一通過所有點一次所需要花費的最小 manhattan dist 總和

這題目相當於要找出在 2 維座標點上的 [Minimum Spanning Tree](https://en.wikipedia.org/wiki/Minimum_spanning_tree)

要找出  [Minimum Spanning Tree](https://en.wikipedia.org/wiki/Minimum_spanning_tree) 的 Cost

可以透過 [Prim’s algorithm](https://zh.wikipedia.org/zh-tw/%E6%99%AE%E6%9E%97%E5%A7%86%E7%AE%97%E6%B3%95)

這個演算法直觀來看就是先以每個點為起點計算每兩點之間的 Cost

做出一個帶有 Cost 的 adjacency List

然後透過 MinHeap 每次 Pop 出最小的 Cost 還有那個連接點

![](https://i.imgur.com/4We2uLd.png)

## 程式碼

```java
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
      for (int j = i + 1; j < pointsNum; j++) {
        int[] point2 = points[j];
        int dist = Math.abs(point1[0] - point2[0]) + Math.abs(point1[1] - point2[1]);
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
    queue.add(new AdjacencyNode(0, 0));
    // Step 2: Prim's algorithm
    while (visit.size() < pointsNum) {
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

```
## 困難點

1. 要理解如何做出 adjacency list
2. 要理解 [Prim’s algorithm](https://zh.wikipedia.org/zh-tw/%E6%99%AE%E6%9E%97%E5%A7%86%E7%AE%97%E6%B3%95)

## Solve Point

- [x]  需要從每個點為起點 做出一個 帶有 weight 的 adjacency list
- [x]  每次透過 HashSet 紀錄 visit 過的點
- [x]  然後依序把 adjacency List 的點 放到 minHeap 來找出下一個最小 cost 的點