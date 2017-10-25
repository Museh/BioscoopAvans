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
public class Cinema {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String country;

    private String city;

    private String street;

    private String zipcode;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "CINEMA_ID", referencedColumnName = "id")
    private List<Room> rooms = new ArrayList<>();

    public Cinema(){}

    public Cinema(String name, String country, String city, String street, String zipcode){
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Cinema(String name, String country, String city, String street, String zipcode, List<Room> rooms){
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.rooms = rooms;
    }
}
