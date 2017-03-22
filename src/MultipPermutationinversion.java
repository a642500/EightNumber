/**
 * Created by Xu Yifan on 3/20/17.
 */
public class MultipPermutationinversion implements AStarImplement.Heuristic {

    @Override
    public int estimate(Square start, Square goal) {

        int cost = 0;
        for (int i = 0; i < start.array.length; i++) {
            if (start.array[i] != 0 && goal.array[i] != start.array[i]) {
                cost++;
            }

            for (int j = i + 1; j < start.array.length; j++) {
                if (start.array[j] != 0 && start.array[j] < start.array[i]) {
                    cost += 3;
                }
            }
        }
        return cost;
    }
}
