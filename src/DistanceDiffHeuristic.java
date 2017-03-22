/**
 * 计算位置不符的方块移动到正确位置的移动距离的和， 也就是曼哈顿距离
 *
 * Created by Xu Yifan on 3/20/17.
 */
public class DistanceDiffHeuristic implements AStarImplement.Heuristic {

    private int ord[] = null;

    @Override
    public int estimate(Square start, Square goal) {
        if (ord == null) {
            ord = new int[9];
            int[] array = goal.array;
            for (int i = 0; i < array.length; i++) {
                ord[array[i]] = i;
            }
        }

        int cost = 0;
        for (int i = 0; i < start.array.length; i++) {
            int num = start.array[i];
            if (num == 0) continue;
            cost += Math.abs(ord[num] - i);
        }
        return cost;
    }
}
