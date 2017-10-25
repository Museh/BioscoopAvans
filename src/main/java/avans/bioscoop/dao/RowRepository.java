package avans.bioscoop.dao;

import avans.bioscoop.models.Row;
import avans.bioscoop.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RowRepository extends JpaRepository<Row, Long> {

    @Query(value = "SELECT * FROM row OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<Row> getRows(int skip, int count);
}
