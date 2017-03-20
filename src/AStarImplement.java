import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class AStarImplement {
    private final Square target;
    private final Square threshold;
    private final Heuristic mHeuristic;
    private final Distance mDistance;
    private OpenSet openSet = new OpenSet();
    private List<Square> closeSet = new ArrayList<>();

    public AStarImplement(final Square start, final Square target, Heuristic heuristic) {
        this.target = target;
        threshold = start;
        this.mHeuristic = heuristic;
        mDistance = new StepDistance();
    }

    public AStarImplement(final Square start, final Square target, Heuristic heuristic, Distance distance) {
        this.target = target;
        threshold = start;
        this.mHeuristic = heuristic;
        mDistance = distance;
    }

    public static void main(String[] args) {
        boolean DEBUG = false;
        Square threshold;

        //noinspection ConstantConditions
        if (DEBUG) {
//            threshold = new Square(new int[]{2, 0, 1, 4, 6, 5, 3, 7, 8});
            threshold = new Square(new int[]{2, 8, 3, 1, 0, 4, 7, 6, 5});
        } else {
            threshold = inputFromUser();
        }

        AStarImplement aStarImplement = new AStarImplement(threshold, Square.getSolve(), new DistanceDiffHeuristic());
        long start = System.currentTimeMillis();
        Square solve = aStarImplement.solve();
        long duration = System.currentTimeMillis() - start;

        if (solve == null) {
            System.out.println(String.format("no result, openSize: %d, closeSize: %d",
                    aStarImplement.openSet.size(), aStarImplement.closeSet.size()));
        } else {
            Square curr = solve;
            while (curr != null) {
                System.out.println(curr);
                curr = curr.getParent();
            }

            System.out.println(String.format("found result, openSize: %d, closeSize: %d",
                    aStarImplement.openSet.size(), aStarImplement.closeSet.size()));
            System.out.println(String.format("Cost time: %d ms", duration));
        }
    }

    private static Square inputFromUser() {
        System.out.println("Use 0 represent for space");
        System.out.println("Please input nine number in the ord a, b, c, d, e, f, g, h, i. ");

        Scanner scanner = new Scanner(System.in);
        int array[] = new int[9];
        for (int i = 0; i < 9; i++) {
            array[i] = scanner.nextInt();
        }

        int check[] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};// 9 length

        for (int i : array) {
            try {
                check[i]++;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(String.format("You must use any number in 0~8 once. You use %d", i));
            }
        }


        for (int j = 0; j < check.length; j++) {
            int i = check[j];
            if (i != 0) {
                throw new IllegalArgumentException(String.format("You must use any number in 0~8 once. You use %d for %d times", j, i + 1));
            }
        }
        return new Square(array);
    }

    Square solve() {

        openSet.add(threshold);

        while (!openSet.isEmpty()) {
            Square curr = openSet.poll();
            closeSet.add(curr);

            if (target.equals(curr)) {
                return curr;
            }

            final List<Square> successorList = curr.getSuccessors();

            successorList.forEach(it -> {
                if (!closeSet.contains(it)) {
                    int g = curr.g() + mDistance.distance(curr, it);
                    it.setG(g);
                    it.setParent(curr);

                    Square node = openSet.get(it);
                    if (node == null) {
                        it.setF(g + mHeuristic.estimate(it, target));
                        openSet.add(it);
                    } else {
                        if (g < node.g()) {
                            it.setF(g + mHeuristic.estimate(it, target));
                            openSet.remove(node);
                            openSet.add(it);
                        }
                    }
                }
            });
        }
        return null;
    }

    public interface Distance {
        int distance(final Square start, final Square end);
    }

    public interface Heuristic {
        int estimate(final Square start, final Square goal);
    }

    private class StepDistance implements Distance {
        @Override
        public int distance(Square start, Square end) {
            return 1;
        }
    }
}
