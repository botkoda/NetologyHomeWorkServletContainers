package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    Map<Long, Post> postMap = new ConcurrentHashMap();
    AtomicLong postNumber = new AtomicLong();

    public List<Post> all() {
        List<Post> postList = new ArrayList<>();
        postMap.forEach((k, v) -> postList.add(v));
        return postList;
    }

    public Optional<Post> getById(long id) {
        //  var post = postList.stream().filter(x -> x.getId() == id).findAny();
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postNumber.addAndGet(1));
            postMap.put(postNumber.get(), post);
            //  postList.add(post);
        } else {
            postMap.put(post.getId(), post);
            // postList.set(postList.indexOf(post), post);
        }
        return post;
    }

    public void removeById(long id) {
        postMap.remove(id);
    }
}
