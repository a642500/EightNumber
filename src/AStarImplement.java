import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static guru.nidi.graphviz.engine.Format.PNG;

@SuppressWarnings("WeakerAccess")
public class AStarImplement {
    private final Square target;
    private final Square threshold;
    private final Heuristic mHeuristic;
    private final Distance mDistance;
    private OpenSet openSet = new OpenSet();
    private List<Square> closeSet = new ArrayList<>();
    private DotUtil mDot = new DotUtil();

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
        boolean DEBUG = true;
        Square threshold;
        Heuristic heuristic;
        Square target;

        //noinspection ConstantConditions
        if (DEBUG) {
            heuristic = new DiffHeuristic();
//            threshold = new Square(new int[]{2, 0, 1, 4, 6, 5, 3, 7, 8});
            target = Square.getSolve();
//            threshold = new Square(new int[]{2, 8, 3, 1, 0, 4, 7, 6, 5});
            threshold = new Square(new int[]{2, 8, 3, 1, 6, 4, 7, 0, 5});
//            threshold = new Square(new int[]{8, 0, 6, 5, 2, 3, 1, 7, 4});
//            threshold = new Square(new int[]{0, 3, 4, 5, 1, 6, 7, 8, 2});
//            threshold = new Square(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        } else {
            target = inputTargetSquareFromUser();
            threshold = inputSquareFromUser();
            heuristic = inputHeuristicFromUser();
            System.out.println("Ok, start search it...");
        }

//        AStarImplement aStarImplement = new AStarImplement(new Square(new int[]{5, 0, 8, 4, 2, 1, 7, 3, 6}),
//                new Square(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}), new DistanceDiffHeuristic());
        AStarImplement aStarImplement = new AStarImplement(threshold, target, heuristic);
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
                aStarImplement.mDot.mark(curr);
                curr = curr.getParent();
            }

            System.out.println(String.format("found result, openSize: %d, closeSize: %d",
                    aStarImplement.openSet.size(), aStarImplement.closeSet.size()));
            System.out.println(String.format("Cost time: %d ms", duration));

            String dot = aStarImplement.mDot.build();
            try {
                Graphviz.fromString(dot).width(1920).height(1080).render(PNG).toFile(new File("output.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Heuristic inputHeuristicFromUser() {
        System.out.println("\nPlease select a heuristic:\n" +
                "0. the number of the mismatch tiles  （ bad heuristic, admissibility）\n" +
                "1. the sum of distance between the mismatch tiles and their proper position  （ good heuristic, admissibility）\n" +
                "2. three times of the permutation inversion  （ not admissibility）\n" +
                "3. three times of the permutation inversion + the number of the mismatch tiles   (not admissibility)\n" +
                "4. custom: the sum of the row and column mismatch ( better than 0, worse than 1, admissibility)\n");
        System.out.println("please input a integer to select one:\n");

        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();

        switch (choose) {
            case 0:
                return new DiffHeuristic();
            case 1:
                return new DistanceDiffHeuristic();
            case 2:
                return new PermutationInversion();
            case 3:
                return new MultipPermutationinversion();
            case 4:
                return new RowColumnDiffHeuristic();
            default:
                throw new IllegalArgumentException(String.format("You must input a number between 0 and 4, you input %d", choose));
        }
    }

    private static Square inputTargetSquareFromUser() {
        System.out.println("The final result should be one of those two:\n" +
                "------\t------\n" +
                "1 2 3\t1 2 3\n" +
                "8   4\t4   5\n" +
                "7 6 5\t6 7 8\n" +
                "------\t------\n" +
                "  0  \t  1   \n");
        System.out.println("The first is like our book while the second is in natural ord.\n" +
                "I think the permutation inversion heuristic only work if you choose the second.\n" +
                "Please input 0 or 1 to choose:");
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        switch (choose) {
            case 0:
                return Square.getSolve();
            case 1:
                return Square.getSolve2();
            default:
                throw new IllegalArgumentException(String.format("You must input 0 or 1. You input %d", choose));
        }
    }

    private static Square inputSquareFromUser() {
        System.out.println("Use 0 represent for space");
        System.out.println("------");
        System.out.println("a b c");
        System.out.println("d e f");
        System.out.println("g h i");
        System.out.println("------");
        System.out.println("\n Alert: this question have a bit big search room( 9!=362 880), some case may \ncost minutes if you choose a bad heuristic(the first one we provide). \nI recommend to use this case to test:\n2, 8, 3, 1, 0, 4, 7, 6, 5 (if you choose the 0 final result)\n" +
                "5, 0, 8, 4, 2, 1, 7, 3, 6 (if you choose the 1 final result)\n");
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
        Square square = new Square(array);

        System.out.println("You input is :\n" + square);
        return square;
    }

    boolean canSolve() {
        int inverse = 0;
        for (int i = 0; i < threshold.array.length; i++) {
            for (int j = i + 1; j < threshold.array.length; j++) {
                if (threshold.array[j] != 0 && threshold.array[j] < threshold.array[i]) {
                    inverse++;
                }
            }
        }

        int inverse2 = 0;
        for (int i = 0; i < target.array.length; i++) {
            for (int j = i + 1; j < target.array.length; j++) {
                if (target.array[j] != 0 && target.array[j] < target.array[i]) {
                    inverse2++;
                }
            }
        }

        return inverse % 2 == inverse2 % 2;
    }

    Square solve() {
        if (!canSolve()) {
            return null;
        }


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
                        mDot.link(curr, it);
                        openSet.add(it);
                    } else if (g < node.g()) {
                        it.setF(g + mHeuristic.estimate(it, target));
                        openSet.remove(node);
                        mDot.link(curr, it);
                        openSet.add(it);
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
