package zpalmer.tumbldown.core;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;

import zpalmer.tumbldown.api.Post;

public class LikedPostsCache {
    HashMap<String, TreeSet<Post>> cache;

    public LikedPostsCache() {
        this.cache = new HashMap<String, TreeSet<Post>>(100);
    } 

    public TreeSet<Post> get(String key) {
        return cache.getOrDefault(key, new TreeSet<Post>());
    }

    public void add(String key, TreeSet<Post> morePosts) {
        // TODO: double-check this works if there's nothing in there originally
        cache.merge(key, morePosts, (currentSet, newSetToBeAdded) -> {
            var combinedSet = new TreeSet<Post>(currentSet);
            combinedSet.addAll(newSetToBeAdded);
            return combinedSet;
        });
    }

    // TODO: evict when cache gets too big
}
