package timepass.ge.com.game.repository;

import timepass.ge.com.game.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepo extends JpaRepository<Movie,Integer> {
    public Movie findById(Integer id);

    @Query(value = "select count(*) from public.movie", nativeQuery = true)
    public Integer  findCount();
}
