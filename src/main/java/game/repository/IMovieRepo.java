package game.repository;

import game.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepo extends JpaRepository<Movie,Integer> {
    Movie findById(Integer id);
}
