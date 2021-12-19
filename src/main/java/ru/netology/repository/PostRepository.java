package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
public class PostRepository {
    List<Post> postList = new CopyOnWriteArrayList<>();
    AtomicInteger postNumber = new AtomicInteger();

    public List<Post> all() {
        return postList;
    }

    public Optional<Post> getById(long id) {
        var post = postList.stream().filter(x -> x.getId() == id).findAny();
        return post;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postNumber.addAndGet(1));
            postList.add(post);
        } else {
            postList.set(postList.indexOf(post), post);
        }
        return post;
    }

    public void removeById(long id) {
        var post = postList.stream().filter(x -> x.getId() == id).findAny();
        postList.remove(post);
    }
}
