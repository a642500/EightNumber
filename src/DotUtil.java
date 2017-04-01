/**
 * Created by Xu Yifan on 3/30/17.
 */
public interface DotUtil {
    void link(Square parent, Square child);

    void mark(Square path);

    String build();
}
