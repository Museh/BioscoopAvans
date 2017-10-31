package avans.bioscoop.dao;

import avans.bioscoop.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

    @Query(value = "SELECT SEAT_ID FROM TICKET WHERE VIEWING_ID = ?1", nativeQuery = true)
    List<Number> findTicketsByViewingId(Long id);

    @Query(value = "SELECT * FROM TICKET WHERE barcode = ?1", nativeQuery = true)
    List<Ticket> findTicketByBarcode(String barcode);
}
