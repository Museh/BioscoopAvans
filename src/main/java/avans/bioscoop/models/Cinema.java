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
    private List<Room> rooms = new ArrayList<>();
}
