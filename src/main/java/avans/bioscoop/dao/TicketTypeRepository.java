package avans.bioscoop.dao;

import avans.bioscoop.models.TicketType;
import avans.bioscoop.models.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    @Query(value = "SELECT * FROM Ticket_Type ", nativeQuery = true)
    List<TicketType> getAllTicketTypes();
}
