package avans.bioscoop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Row {

    @Id
    @GeneratedValue
    private long id;

    private int rowNumber;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Seat> seats = new ArrayList<>();
}
