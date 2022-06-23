import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
  final private Solution sol = new Solution();
  @Test
  void minCostConnectPointsExample1() {
    assertEquals(20,
        sol.minCostConnectPoints(new int[][]{
            {0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}
        }));
  }
  @Test
  void minCostConnectPointsExample2() {
    assertEquals(18,
        sol.minCostConnectPoints(new int[][]{
            {3,12},{-2,5},{-4,1}
        }));
  }
}