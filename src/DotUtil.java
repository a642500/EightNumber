/**
 * Created by Xu Yifan on 3/30/17.
 */
public class DotUtil {
    private StringBuilder mStringBuilder = new StringBuilder("digraph G { node [shape=box];");

    public void link(Square parent, Square child) {
        mStringBuilder.append(String.format("%s -> %s;", parent.toDotString(), child.toDotString()));
    }

    public String build() {
        mStringBuilder.append("}");
        return mStringBuilder.toString();
    }
}