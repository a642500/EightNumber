import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Carlos on 3/20/17.
 */
public class OpenSet {
    private Map<int[], Square> map = new HashMap<>();
    private PriorityQueue<Square> priorityQueue = new PriorityQueue<>();


    @SuppressWarnings("unchecked")
    public Square get(Object o) {
        return map.get(o);
    }

    public int size() {
        return priorityQueue.size();
    }


    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public Square poll() {
        Square t = priorityQueue.poll();
        map.remove(t.array);
        return t;
    }

    public void remove(Square square) {
        priorityQueue.remove(square);
        map.remove(square.array);
    }

    public boolean add(Square square) {
        map.put(square.array, square);
        return priorityQueue.add(square);
    }


}
