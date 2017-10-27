package avans.bioscoop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
public class Row {

    @Id
    @GeneratedValue
    private long id;

    private int rowNumber;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROW_ID", referencedColumnName = "id")
    private List<Seat> seats = new ArrayList<>();

    public Row(){}

    public Row(int rowNumber){
        this.rowNumber = rowNumber;
    }

    public Row(int rowNumber, List<Seat> seats){
        this.rowNumber = rowNumber;
        this.seats = seats;
    }
}
