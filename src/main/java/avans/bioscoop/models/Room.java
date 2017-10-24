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
public class Room {

    @Id
    @GeneratedValue
    private long id;

    private int roomNumber;

    private boolean wheelchair;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Viewing> viewings = new ArrayList<>();

    public Room(){}

    public Room(int roomNumber, boolean wheelchair){
        this.roomNumber = roomNumber;
        this.wheelchair = wheelchair;
    }

}
