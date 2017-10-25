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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Row> rows = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Viewing> viewings = new ArrayList<>();

    public Room(){}

    public Room(int roomNumber, boolean wheelchair){
        this.roomNumber = roomNumber;
        this.wheelchair = wheelchair;
    }

    public Room(int roomNumber, boolean wheelchair, Viewing viewing){
        this.roomNumber = roomNumber;
        this.wheelchair = wheelchair;
        this.viewings.add(viewing);
    }

    public Room(int roomNumber, boolean wheelchair, List<Viewing> viewings, List<Row> rows){
        this.roomNumber = roomNumber;
        this.wheelchair = wheelchair;
        this.viewings = viewings;
        this.rows = rows;
    }

    public Room(int roomNumber, boolean wheelchair, Viewing viewing, List<Row> rows){
        this.roomNumber = roomNumber;
        this.wheelchair = wheelchair;
        this.viewings.add(viewing);
        this.rows = rows;
    }

}
