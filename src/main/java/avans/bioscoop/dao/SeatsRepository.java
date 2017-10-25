package avans.bioscoop.dao;

import avans.bioscoop.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Long>{

    @Query(value = "SELECT * FROM seat OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<Seat> getSeats(int skip, int count);
}
