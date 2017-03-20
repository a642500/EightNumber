import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Carlos on 3/20/17.
 */
@SuppressWarnings("WeakerAccess")
public class Square implements Comparable<Square> {
    private static final Square sSolve = new Square(new int[]{1, 2, 3, 8, 0, 4, 7, 6, 5});
    public final int[] array;
    private int g;
    private int f;
    private Square parent;

    public Square(int[] p) {
        if (p.length != 9) {
            throw new IllegalArgumentException("9 length is required.");
        }
        this.array = p;
    }

    public static Square getSolve() {
        return sSolve;
    }

    public Square getParent() {
        return parent;
    }

    public void setParent(Square parent) {
        this.parent = parent;
    }

    public int g() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public List<Square> getSuccessors() {
        final List<Square> list = new ArrayList<>(4);
        for (int oldIndex = 0; oldIndex < array.length; oldIndex++) {
            if (0 == array[oldIndex]) {
                // +3,-3 +1,-1
                int newIndexs[] = {oldIndex + 3, oldIndex - 3, oldIndex + 1, oldIndex - 1};
                for (int i = 0; i < newIndexs.length; i++) {
                    int index = newIndexs[i];
                    if (
                            ((i == 0 || i == 1) && 0 <= index && index < 9) ||
                                    (i == 2 && (oldIndex + 1) % 3 != 0) ||
                                    (i == 3 && (oldIndex + 1) % 3 != 1)
                            ) {

                        int[] newArray = Arrays.copyOf(array, 9);
                        newArray[oldIndex] = newArray[index];
                        newArray[index] = 0;

                        list.add(new Square(newArray));
                    }
                }
                break;
            }
        }
        return list;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int e = 1;
        for (int value : array) {
            hash += value * e;
            e *= 10;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Square && Arrays.equals(array, ((Square) obj).array);
    }

    @Override
    public int compareTo(Square o) {
        return this.f - o.f;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("------\n");
        for (int i = 0; i < array.length; i++) {
            int num = array[i];
            str.append(num);

            if ((i + 1) % 3 == 0) {
                str.append("\n");
            } else {
                str.append(" ");
            }
        }
        str.append("------");
        str.setCharAt(str.indexOf("0"), ' ');
        return str.toString();
    }

    public int f() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
