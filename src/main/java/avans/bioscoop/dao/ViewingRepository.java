package avans.bioscoop.dao;

import avans.bioscoop.models.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViewingRepository extends JpaRepository<Viewing, Long>{

}
