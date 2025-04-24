package rccookie.data.json;

import rccookie.data.RuntimeLoadable;

public interface JsonLoadable extends RuntimeLoadable {
    

    @Override
    default void load(Object data) {
        load((JsonObject)data);
    }

    public void load(JsonObject data);
}
