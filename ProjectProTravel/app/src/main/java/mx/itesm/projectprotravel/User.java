package mx.itesm.projectprotravel;

/**
 * Created by lrocg on 22/02/17.
 */

public class User {

    String name;
    String email;
    int viaje;

    public User(String name,String email){

        this.name=name;
        this.email=email;
        viaje=0;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public int getViaje(){
        return viaje;
    }

}
