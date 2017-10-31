package avans.bioscoop.services;

import avans.bioscoop.models.Room;
import avans.bioscoop.models.Viewing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public List<Viewing> getTodaysViewings(List<Viewing> viewings){

        List<Viewing> filteredViewings = new ArrayList<>();

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = Calendar.getInstance().getTime();

        for(Viewing v : viewings){

            System.out.println("comparing: " + myFormat.format(v.getStartTime()) + " AND " + myFormat.format(date));
            if(myFormat.format(v.getStartTime()).equals(myFormat.format(date))){
                filteredViewings.add(v);
            }
        }

        return filteredViewings;
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
