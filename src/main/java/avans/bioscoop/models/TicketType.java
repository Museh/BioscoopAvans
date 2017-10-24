package avans.bioscoop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketType {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int price;

}
