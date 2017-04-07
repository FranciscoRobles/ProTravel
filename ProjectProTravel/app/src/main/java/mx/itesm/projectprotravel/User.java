package mx.itesm.projectprotravel;

/**
 * Created by lrocg on 22/02/17.
 */

public class User {

    String name;
    String email;
    int viaje;
    String status;

    public User(String name,String email){

        this.name=name;
        this.email=email;
        this.status = "";
        viaje=0;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getStatus() { return status; }

    public int getViaje(){
        return viaje;
    }

    public void setStatus(String status){ this.status=status; }

}
