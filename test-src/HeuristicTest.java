import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

/**
 * Created by Carlos on 3/31/17.
 */
@RunWith(JUnit4.class)
public class HeuristicTest {

    private void gen(Square threshold, Square target, String filename, AStarImplement.Heuristic heuristic) {
        AStarImplement aStarImplement = new AStarImplement(threshold, target, heuristic);
        long start = System.currentTimeMillis();
        Square solve = aStarImplement.solve();
        long duration = System.currentTimeMillis() - start;

        if (solve == null) {
            System.out.println(String.format("no result, openSize: %d, closeSize: %d",
                    aStarImplement.getOpenSet().size(), aStarImplement.getCloseSet().size()));
        } else {
            Square curr = solve;
            while (curr != null) {
//                System.out.println(curr);
                aStarImplement.getDot().mark(curr);
                curr = curr.getParent();
            }

            System.out.println(String.format("found result, openSize: %d, closeSize: %d",
                    aStarImplement.getOpenSet().size(), aStarImplement.getCloseSet().size()));
            System.out.println(String.format("Cost time: %d ms", duration));

            String dot = aStarImplement.getDot().build();
            try {
                //noinspection ConstantConditions
                FileUtils.writeStringToFile(new File(filename), dot, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void genDotFile() {
        final Square target = Square.getSolve();
        final Square input[] = new Square[]{
//                new Square(new int[]{2, 8, 3, 1, 0, 4, 7, 6, 5}),
                new Square(new int[]{2, 8, 3, 1, 6, 4, 7, 0, 5}),
                new Square(new int[]{8, 0, 6, 5, 2, 3, 1, 7, 4}),
                new Square(new int[]{0, 3, 4, 5, 1, 6, 7, 8, 2})};
//            threshold = new Square(new int[]{2, 0, 1, 4, 6, 5, 3, 7, 8});
//            threshold = new Square(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});

        final AStarImplement.Heuristic heuristics[] = new AStarImplement.Heuristic[]{
                new DiffHeuristic(), new DistanceDiffHeuristic(), new PermutationInversion(), new MultipPermutationinversion(), new RowColumnDiffHeuristic()
        };

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < heuristics.length; j++) {
                final String name = String.format("dots/%d-%d.dot", i + 1, j + 1);
                gen(input[i], target, name, heuristics[j]);
            }
        }
    }
}
