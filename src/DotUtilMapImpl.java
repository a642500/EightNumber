import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xu Yifan on 4/1/17.
 */
public class DotUtilMapImpl implements DotUtil {

    private StringBuilder mStringBuilder = new StringBuilder("digraph G { node [shape=box];");


    private Map<Square, Square> map = new HashMap<>(1000);

    @Override
    public void link(Square parent, Square child) {
        map.put(child, parent);
//        mStringBuilder.append(String.format("%s -> %s [label= \" %d\"];", parent.toDotString(), child.toDotString(), child.f()));
    }

    @Override
    public void mark(Square path) {
        map.remove(path);
        mStringBuilder.append(String.format("%s [color=red]; ", path.toDotString()));
        if (path.getParent() != null)
            mStringBuilder.append(String.format("%s -> %s [label= \" %d\", color=red];", path.getParent().toDotString(), path.toDotString(), path.f()));
    }

    @Override
    public String build() {
        map.forEach((child, parent) -> {
            mStringBuilder.append(String.format("%s -> %s [label= \" %d\"];", parent.toDotString(), child.toDotString(), child.f()));
        });

        mStringBuilder.append("}");
        return mStringBuilder.toString();
    }
}
