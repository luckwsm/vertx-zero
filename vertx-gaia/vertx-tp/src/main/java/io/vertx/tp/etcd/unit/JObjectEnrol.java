package io.vertx.tp.etcd.unit;

import io.vertx.core.json.JsonObject;
import io.vertx.tp.etcd.Enrol;
import io.vertx.tp.etcd.center.EtcdData;
import io.vertx.up.log.Annal;
import io.zero.epic.fn.Fn;

public class JObjectEnrol implements Enrol<JsonObject> {

    private static final Annal LOGGER = Annal.get(JObjectEnrol.class);

    private transient final EtcdData etcd = EtcdData.create(this.getClass());

    @Override
    public JsonObject write(final String path,
                            final JsonObject entity) {
        final JsonObject data = this.etcd.write(path, entity, 0);
        return Fn.getNull(() -> {
            LOGGER.info(Info.ETCD_WRITE, data, path);
            return data;
        }, data);
    }

    @Override
    public JsonObject read(final String path) {
        return Fn.getNull(() -> {
            final String content = this.etcd.read(path);
            final JsonObject data = new JsonObject(content);
            LOGGER.info(Info.ETCD_READ, data, path);
            return data;
        }, path);
    }
}
