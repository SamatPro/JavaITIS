package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Post;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post> {
    List<Post> findFavouritePostsByUserId(Long userId);
}
