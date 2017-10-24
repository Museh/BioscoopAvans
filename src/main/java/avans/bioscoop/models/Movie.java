package avans.bioscoop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private int length;

    private boolean is3D;

    private int age;

    private String language;

    private boolean subs;

    private String image;

    public Movie(){}

    public Movie(String title, int length, boolean is3D, int age, String language, boolean subs, String image){
        this.title = title;
        this.length = length;
        this.is3D = is3D;
        this.age = age;
        this.language = language;
        this.subs = subs;
        this.image = image;
    }

}
