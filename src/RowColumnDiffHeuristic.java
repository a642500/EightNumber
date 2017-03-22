/**
 * 不在对应列的方块数+不再对应行的方块数
 *
 * Created by Xu Yifan on 3/20/17.
 */
public class RowColumnDiffHeuristic implements AStarImplement.Heuristic {
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
            if (start.array[i] != 0) {
                if ((i % 3) != ord[num] % 3)// column
                    cost++;
                if ((i / 3) != ord[num] / 3)// row
                    cost++;
            }
        }
        return cost;
    }
}
