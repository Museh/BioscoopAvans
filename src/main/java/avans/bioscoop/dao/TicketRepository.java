package avans.bioscoop.dao;

import avans.bioscoop.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

    @Query(value = "SELECT SEAT_ID FROM TICKET WHERE VIEWING_ID = ?1", nativeQuery = true)
    List<BigInteger> findTicketsByViewingId(Long id);
}
