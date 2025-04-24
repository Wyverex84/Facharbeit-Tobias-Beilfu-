package rccookie.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class TagTable<TaggedType, TagType> {

    private final HashMap<TagType, Set<TaggedType>> tags = new HashMap<>();

    public final boolean tag(TaggedType taggedObj, TagType tag) {
        if(taggedObj == null || tag == null) return false;

        if(tags.get(tag) == null) tags.put(tag, new HashSet<>());

        return tags.get(tag).add(taggedObj);
    }

    public final boolean untag(TaggedType taggedObj, TagType tag) {
        if(taggedObj == null || tag == null) return false;

        if(tags.get(tag) == null) return false;

        return tags.get(tag).remove(taggedObj);
    }


    public final Set<TaggedType> getTagged(TagType tag) {
        return new HashSet<>(tags.get(tag));
    }


    public final Set<TagType> getTags(TaggedType taggedObj) {
        Set<TagType> out = new HashSet<>();

        for(Map.Entry<TagType, Set<TaggedType>> entry : tags.entrySet()) {
            if(entry.getValue().contains(taggedObj)) out.add(entry.getKey());
        }

        return out;
    }



    public TagTable() {}
}
