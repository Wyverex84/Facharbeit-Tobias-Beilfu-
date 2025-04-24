package rccookie.util;

import java.util.Set;

public final class ClassTag {

    private static final TagTable<Class<?>, String> TAG_TABLE = new TagTable<>();


    public static final boolean tag(Class<?> taggedClass, String tag) {
        return TAG_TABLE.tag(taggedClass, tag);
    }

    public static final boolean untag(Class<?> taggedClass, String tag) {
        return TAG_TABLE.untag(taggedClass, tag);
    }

    public static final Set<Class<?>> getTagged(String tag) {
        return TAG_TABLE.getTagged(tag);
    }

    public static final Set<String> getTags(Class<?> taggedClass) {
        return TAG_TABLE.getTags(taggedClass);
    }


    private ClassTag() {}
}
