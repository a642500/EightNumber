/**
 * Created by Xu Yifan on 4/1/17.
 */
public class DotUtilStringImpl implements DotUtil {
    private StringBuilder mStringBuilder = new StringBuilder("digraph G { node [shape=box];");

    @Override
    public void link(Square parent, Square child) {
        mStringBuilder.append(String.format("%s -> %s [label= \" %d\"];", parent.toDotString(), child.toDotString(), child.f()));
    }

    @Override
    public void mark(Square path) {
        mStringBuilder.append(String.format("%s [color=red]; ", path.toDotString()));
    }

    @Override
    public String build() {
        mStringBuilder.append("}");
        return mStringBuilder.toString();
    }
}
