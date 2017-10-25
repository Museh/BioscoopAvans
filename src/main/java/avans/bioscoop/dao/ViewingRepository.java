package avans.bioscoop.dao;

import avans.bioscoop.models.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewingRepository extends JpaRepository<Viewing, Long>{

    @Query(value = "SELECT * FROM viewing ORDER BY start_time asc", nativeQuery = true)
    List<Viewing> findAllViewings();

    @Query(value = "SELECT * FROM viewing WHERE Movie_ID = ?1 ORDER BY start_time asc", nativeQuery = true)
    List<Viewing> findAllViewingsByMovieId(long id);
}
