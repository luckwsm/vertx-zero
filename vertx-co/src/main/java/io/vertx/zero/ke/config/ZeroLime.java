package io.vertx.zero.ke.config;

import com.vie.cv.FileTypes;
import com.vie.cv.Strings;
import com.vie.hoc.HPool;
import com.vie.util.Instance;
import com.vie.util.StringUtil;
import io.vertx.core.json.JsonObject;
import io.vertx.zero.ke.ZeroNode;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang
 */
public class ZeroLime implements ZeroNode<ConcurrentMap<String, String>> {

    private transient final ZeroNode<JsonObject> node
            = Instance.singleton(ZeroVertx.class);

    @Override
    public ConcurrentMap<String, String> read() {
        // 1. Read all zero configuration: zero
        final JsonObject data = this.node.read();
        // 2. Read the string node "lime" for extensions
        final String literal = data.getString(Keys.LIME);
        return build(literal);
    }

    private ConcurrentMap<String, String> build(final String literal) {
        final Set<String> sets = StringUtil.split(literal, Strings.COMMA);
        for (final String set : sets) {
            HPool.exec(Storage.DATA_LIME, set,
                    () -> calcFile(set));
        }
        return Storage.DATA_LIME;
    }

    private String calcFile(final String key) {
        final StringBuilder result = new StringBuilder();
        result.append(Path.KE_FOLDER).append(Strings.SLASH);
        result.append(Limes.PREFIX).append(Strings.DASH);
        result.append(key);
        result.append(Strings.DOT).append(FileTypes.YML);
        return result.toString();
    }
}