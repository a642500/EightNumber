import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class Main {
    static List<State> states = new LinkedList<State>();
    static State curr = null;

    // This code is very poor 😂
    public static void main(String[] args) {
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


//        State input = new State(2, 0, 1, 4, 6, 5, 3, 7, 8);
        State input = new State(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8]);
        appendState(input);

        // (1)
        while (curr.c == 0 || curr.c == 1) {
            curr.cycBEFC();
        }

        // (2)
        while (curr.a != 1) {
            curr.cycExceptC();
        }

        // (3)
        while (curr.b != 2) {
            curr.cycExceptA();
        }

        // (4)
        if (curr.c == 0)
            curr.moveF();
        if (curr.c != 3) {
            while (curr.e != 3) {
                curr.cycExceptABC();
            }

            // (5)
            curr.ensureD();
            curr.moveA();
            curr.moveB();
            curr.moveE();
            curr.moveF();
            curr.moveC();
            curr.moveB();
            curr.moveA();
            curr.moveD();
        }

        // (6)
        while (curr.f != 4) {
            curr.cycExceptABC();
        }
        if (curr.i != 5) {
            // (7)
            while (curr.e != 5)
                curr.cycDGHE();

            // (8)
            curr.ensureD();
            curr.moveA();
            curr.moveB();
            curr.moveC();
            curr.moveF();
            curr.moveE();
            curr.moveH();
            curr.moveI();
            curr.moveF();
            curr.moveC();
            curr.moveB();
            curr.moveA();
            curr.moveD();
        }

        // (9)
        while (curr.h != 6) {
            curr.cycDGHE();
        }
        if (curr.g == 0) {
            curr.moveD();
        }
        if (curr.d == 0) {
            curr.moveE();
        }

        if (curr.g == 7 && curr.d == 8) {
            System.out.println("Ok");
        } else {
            System.out.println("No result!");
        }
    }

    public static void appendState(State newState) {
        states.add(newState);
        System.out.println(newState);
        curr = newState;
    }

    @SuppressWarnings({"Duplicates", "UnusedReturnValue"})
    public static class State {
        final int a, b, c, d, e, f, g, h, i;

        /*
           a b c
           d e f
           g h i
        */
        public State(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
            this.i = i;
        }

        public State ensureD() {
            // ensure d is 0
            if (curr.f == 0) {
                curr.moveI();
            }
            if (curr.i == 0) {
                curr.moveH();
            }

            if (curr.h == 0) {
                curr.moveG();
            }

            if (curr.g == 0) {
                curr.moveD();
            }
            return curr;
        }

        /*
           b e c
           a f i
           d g h
        */
        public State cycExceptC() {
            appendState(new State(b, e, c, a, f, i, d, g, h));
            return curr;
        }

        /*
        a b c
        e h f
        d g i
        */
        public State cycDGHE() {
            appendState(new State(a, b, c, e, h, f, d, g, i));
            return curr;
        }

        /*
         a c f
         d b e
         g h i
      */
        public State cycBEFC() {
            appendState(new State(a, c, f, d, b, e, g, h, i));
            return curr;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder("------\n")
                    .append(a).append(" ").append(b).append(" ").append(c).append("\n")
                    .append(d).append(" ").append(e).append(" ").append(f).append("\n")
                    .append(g).append(" ").append(h).append(" ").append(i).append("\n")
                    .append("------");
            str.setCharAt(str.indexOf("0"), ' ');
            return str.toString();
        }

        /*
                   a c f
                   e b i
                   d g h
                */
        public State cycExceptA() {
            appendState(new State(a, c, f, e, b, i, d, g, h));
            return curr;
        }

        /*
           a b c
           e f i
           d g h
        */
        public State cycExceptABC() {
            appendState(new State(a, b, c, e, f, i, d, g, h));
            return curr;
        }


        public State moveA() {
            if (b == 0) {
                appendState(new State(0, a, c, d, e, f, g, h, i));
                return curr;
            } else if (d == 0) {
                appendState(new State(0, b, c, a, e, f, g, h, i));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveB() {
            if (a == 0) {
                appendState(new State(b, 0, c, d, e, f, g, h, i));
                return curr;
            } else if (e == 0) {
                appendState(new State(a, 0, c, d, b, f, g, h, i));
                return curr;
            } else if (c == 0) {
                appendState(new State(a, 0, b, d, e, f, g, h, i));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveC() {
            if (b == 0) {
                appendState(new State(a, c, 0, d, e, f, g, h, i));
                return curr;
            } else if (f == 0) {
                appendState(new State(a, b, 0, d, e, c, g, h, i));
                return curr;
            } else throw new IllegalArgumentException();
        }


        public State moveD() {
            if (a == 0) {
                appendState(new State(d, b, c, 0, e, f, g, h, i));
                return curr;
            } else if (e == 0) {
                appendState(new State(a, b, c, 0, d, f, g, h, i));
                return curr;
            } else if (g == 0) {
                appendState(new State(a, b, c, 0, e, f, d, h, i));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveE() {
            if (d == 0) {
                appendState(new State(a, b, c, e, 0, f, g, h, i));
                return curr;
            } else if (b == 0) {
                appendState(new State(a, e, c, d, 0, f, g, h, i));
                return curr;
            } else if (f == 0) {
                appendState(new State(a, b, c, d, 0, e, g, h, i));
                return curr;
            } else if (h == 0) {
                appendState(new State(a, b, c, d, 0, f, g, e, i));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveF() {
            if (c == 0) {
                appendState(new State(a, b, f, d, e, 0, g, h, i));
                return curr;
            } else if (e == 0) {
                appendState(new State(a, b, c, d, f, 0, g, h, i));
                return curr;
            } else if (i == 0) {
                appendState(new State(a, b, c, d, e, 0, g, h, f));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveG() {
            if (d == 0) {
                appendState(new State(a, b, c, g, e, f, 0, h, i));
                return curr;
            } else if (h == 0) {
                appendState(new State(a, b, c, d, e, f, 0, g, i));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveH() {
            if (g == 0) {
                appendState(new State(a, b, c, d, e, f, h, 0, i));
                return curr;
            } else if (e == 0) {
                appendState(new State(a, b, c, d, h, f, g, 0, i));
                return curr;
            } else if (i == 0) {
                appendState(new State(a, b, c, d, e, f, g, 0, h));
                return curr;
            } else throw new IllegalArgumentException();
        }

        public State moveI() {
            if (h == 0) {
                appendState(new State(a, b, c, d, e, f, g, i, 0));
                return curr;
            } else if (f == 0) {
                appendState(new State(a, b, c, d, e, i, g, h, 0));
                return curr;
            } else throw new IllegalArgumentException();
        }

    }
}
