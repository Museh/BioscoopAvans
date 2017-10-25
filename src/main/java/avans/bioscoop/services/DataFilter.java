package avans.bioscoop.services;

import avans.bioscoop.models.Room;
import avans.bioscoop.models.Viewing;

import java.util.ArrayList;
import java.util.List;

public class DataFilter {

    public DataFilter(){

    }

    public List<Viewing> filterViewingsByMovieTitle(List<Viewing> viewings, String filter){

        List<Viewing> filtered = new ArrayList<>();

        for(Viewing v : viewings){
            if(v.getMovie().getTitle().toLowerCase().contains(filter.toLowerCase())){
                filtered.add(v);
            }
        }

        return filtered;
    }

    public Room getRoomByViewingID(List<Room> rooms, Long id){

        for(Room r : rooms){
            for(Viewing v : r.getViewings()){
                if(v.getId() == id){
                    return r;
                }
            }
        }

        return null;
    }

}
