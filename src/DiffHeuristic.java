/**
 * Created by Xu Yifan on 3/20/17.
 */
public class DiffHeuristic implements AStarImplement.Heuristic {

    @Override
    public int estimate(Square start, Square goal) {
        int cost = 0;
        for (int i = 0; i < start.array.length; i++) {
            if (start.array[i] != 0 && goal.array[i] != start.array[i]) {
                cost++;
            }
        }
        return cost;
    }
}
