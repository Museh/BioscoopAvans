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
public class Viewing {

    @Id
    @GeneratedValue
    private long id;

}
