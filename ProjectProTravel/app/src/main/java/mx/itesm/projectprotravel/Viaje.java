package mx.itesm.projectprotravel;

/**
 * Created by lrocg on 23/02/17.
 */

public class Viaje {

    String nombre;
    String destino;
    String partida;
    String tiempo;
    String lider;
    int viajeros;

    public Viaje(String nombre,String destino,String partida,String tiempo, String lider){
        this.nombre=nombre;
        this.destino=destino;
        this.partida=partida;
        this.tiempo=tiempo;
        this.lider=lider;
        viajeros=0;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDestino(){
        return destino;
    }

    public String getPartida(){
        return partida;
    }

    public String getTiempo(){
        return tiempo;
    }

    public String getLider(){
        return lider;
    }

    public int getViajeros(){
        return viajeros;
    }


}
