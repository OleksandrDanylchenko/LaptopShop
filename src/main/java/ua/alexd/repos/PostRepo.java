package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Post;

public interface PostRepo extends CrudRepository<Post, Long> {
}