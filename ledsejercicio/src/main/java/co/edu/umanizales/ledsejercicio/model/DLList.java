package co.edu.umanizales.ledsejercicio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalTime;


import java.util.ArrayList;
import java.util.List;


import co.edu.umanizales.ledsejercicio.controller.DLListController;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import co.edu.umanizales.ledsejercicio.controller.dto.ErrorDTO;
import co.edu.umanizales.ledsejercicio.controller.dto.ResponseDTO;
import co.edu.umanizales.ledsejercicio.model.LedBulb;
import co.edu.umanizales.ledsejercicio.service.DLListService;
import co.edu.umanizales.ledsejercicio.exception.DLListException;


import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

@Data
@Getter
@Setter

public class DLList {

    private DLNode head;
    private DLNode tail;
    private int size;
    private List<LedBulb> bulbs = new ArrayList<>();

    public void validAdd (LedBulb led) throws DLListException{

        if (head == null) {

        } else {

            DLNode current = head;

            //reviso si ya existe una bombillo con la id de la bombillo siendo agregada
            if (current.getData().getId().equals(led.getId()) ){
                throw new DLListException("Ya existe una bombillo con la identificación" + led.getId());
            }


            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una bombillo con la id de la bombillo siendo agregada
                if (current.getData().getId().equals(led.getId()) ){
                    throw new DLListException("Ya existe una bombillo con la identificación" + led.getId());
                }

                current = current.getNext();

            }
        }
    }
    
    public List<LedBulb> print() throws DLListException {

        //vacío la lista normal que ya tengo
        bulbs.clear();

        //recorro la lista agregando las bombillas de cada nodo a la lista normal
        if (head != null){
            DLNode temp = head;
            while (temp != null){
                bulbs.add(temp.getData());
                temp = temp.getNext();
            }
        } else {
            throw new DLListException("la lista está vacía");
        }

        //retorno dicha lista normal
        return bulbs;
    }

    public void invert() {
        if (this.head != null){

            //copio la lista y defino el ayudante
            DLList copy = new DLList();
            DLNode temp = this.head;

            //ciclo por la fila
            while (temp != null){

                //voy metiendo al principio a cada uno por el que voy ciclando
                try {
                    copy.addToStart(temp.getData());
                } catch (DLListException e){

                }
                temp = temp.getNext();
            }

            //reemplazo esta lista por la copia invertida que acabo de crear
            this.head = copy.getHead();

        }
    }

    public void reset() throws DLListException{
        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new DLListException("la lista está vacía");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //voy apagando
            temp.getData().setState(false);
            temp.getData().setLastoff(null);
            temp.getData().setLaston(null);

            temp = temp.getNext();
        }


    }

    public void addToStart(LedBulb bulb) throws DLListException {

        DLNode newNode = new DLNode(bulb);

        //para cuando haga las validaciones

        try {
            validAdd(bulb);
        } catch (DLListException e){
            throw new DLListException(e.getMessage());
        }


        //si la cabeza está llena, pongo de previo a la cabeza al nuevo, y pongo a la cabeza de siguiente al nuevo
        if(head !=null)
        {
            head.setPrev(newNode);
            newNode.setNext(head);
        }

        //defino la cabeza como el nuevo
        head = newNode;
        size ++;

    }

    public void add(LedBulb bulb) throws DLListException{

        //si la cabeza es nula, la nueva cabeza es la bombilla ingresada
        if (head == null) {
            head = new DLNode(bulb);

        } else {

            //sino, defino un nuevo nodo
            DLNode newNode = new DLNode(bulb);
            DLNode current = head;

            //reviso si ya existe una bombilla con la id de la bombilla siendo agregada
            if (current.getData().getId().equals(bulb.getId()) ){
                throw new DLListException("Ya existe una bombilla con la identificación" + bulb.getId());
            }

            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una bombilla con la id de la bombilla siendo agregada
                if (current.getData().getId().equals(bulb.getId()) ){
                    throw new DLListException("Ya existe una bombilla con la identificación" + bulb.getId());
                }
                
                current = current.getNext();

            }

            //en el final, meto la bombilla
            newNode.setPrev(current);
            current.setNext(newNode);

        }

        //aumento el tamaño de la lista
        size++;
    }

    public void deleteByPlace(int place) throws DLListException{

        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new DLListException("la lista está vacía");
        } else if (place < 1) {
            throw new DLListException("el ingresado es menor a 1");
        } else if (place > size){
            throw new DLListException("el lugar ingresado es mayor al tamaño de la lista");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){
                DLNode prev = temp.getPrev();
                DLNode next = temp.getNext();

                //reviso si es la cabeza
                if (prev == null){
                    //si sí, la cabeza será la siguiente, es decir, quito la mascota de la cabeza
                    head = next;
                }else{
                    //sino, hago que el previo tenga como siguiente a el siguiente de temp, es decir, saco a temp de la lista
                    prev.setNext(next);
                }

                //reviso si es el último
                if (next != null){
                    //si next no es nulo, es decir, si no estoy en el final de la fila, hago que next tenga de previo a prev
                    next.setPrev(prev);
                }

                //después de haberlo removido, resto del tamaño de la fila
                size--;
            }
            steps++;
            temp = temp.getNext();
        }

    }

    public void turnBulbOn(int place) throws DLListException{

    //reviso que los lugares ingresados sean válidos
        if (head == null) {
        throw new DLListException("la lista está vacía");
    } else if (place < 1) {
        throw new DLListException("el ingresado es menor a 1");
    } else if (place > size){
        throw new DLListException("el lugar ingresado es mayor al tamaño de la lista");
    }

    //defino un ayudante y un contador para saber cuantos pasos llevo
    DLNode temp = head;
    int steps = 1;

    //recorro la lista
        while (temp != null){

        //reviso si el puesto es igual al puesto buscado
        if (steps == place){
            temp.getData().setState(true);
            temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
        }
        steps++;
        temp = temp.getNext();
    }
}

    public void turnBulbOff(int place) throws DLListException{

        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new DLListException("la lista está vacía");
        } else if (place < 1) {
            throw new DLListException("el ingresado es menor a 1");
        } else if (place > size){
            throw new DLListException("el lugar ingresado es mayor al tamaño de la lista");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){
                temp.getData().setState(false);
                temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
            }
            steps++;
            temp = temp.getNext();
        }
    }

    public void waveForwards(int place) throws DLListException{

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista para llegar al lugar donde quiero que empiece la ola
        while (temp != null) {

            //reviso si el puesto es igual al puesto buscado
            if (steps == place) {

                //cuando llegue prendo el primer bombillo
                temp.getData().setState(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                //reviso si hay mas bombillos adelante
                if (temp.getNext() != null){

                    //empiezo otro ciclo para seguir la ola después del primer bombillo
                    while (temp.getNext() != null) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            throw new DLListException("se ha interrumpido la ola");
                        }

                        //apago el bombillo con el que empecé
                        temp.getData().setState(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                        //paso al siguiente bombillo y lo prendo
                        temp = temp.getNext();
                        temp.getData().setState(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    }

                }

            }

            //aumento el contador de pasos y sigo con el siguiente bombillo
            steps++;
            temp = temp.getNext();

        }
    }

    public void waveBackwards (int place) throws DLListException{

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista para llegar al lugar donde quiero que empiece la ola
        while (temp != null) {

            //reviso si el puesto es igual al puesto buscado
            if (steps == place) {

                //cuando llegue prendo el primer bombillo
                temp.getData().setState(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                //reviso si hay mas bombillos adelante
                if (temp.getPrev() != null){

                    //empiezo otro ciclo para seguir la ola después del primer bombillo
                    while (temp.getPrev() != null) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            throw new DLListException("se ha interrumpido la ola");
                        }

                        //apago el bombillo con el que empecé
                        temp.getData().setState(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                        //paso al siguiente bombillo y lo prendo
                        temp = temp.getPrev();
                        temp.getData().setState(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    }

                }

            }

            //aumento el contador de pasos y sigo con el siguiente bombillo
            steps++;
            temp = temp.getNext();

        }

    }

    public void lightShow()throws DLListException{

        //primero reviso que la lista no esté vacía para mandar una excepción
        if (head == null){
            throw new DLListException("La lista de bombillas está vacía");
        }

        //primero reviso si la cantidad de bombillas es par o impar
        if (size % 2 == 0){

            //aquí, la lsita sería par
            int middle = (size / 2);


            //defino un ayudante y un contador para saber cuantos pasos llevo
            DLNode temp = head;
            int steps = 1;

            //recorro la lista para llegar al lugar donde quiero que empiece la ola
            while (temp != null) {

                //reviso si el puesto es igual al puesto buscado
                if (steps == (middle + 1)){

                    //cuando llegue prendo el primer bombillo
                    temp.getData().setState(true);
                    temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //creo un nuevo ayudante para que haga la ola hacia atras y hago que prenda el primer bombillo de ese lado
                    DLNode temp2 = temp.getPrev();
                    temp2.getData().setState(true);
                    temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //reviso si hay mas bombillos adelante
                    if (temp.getNext() != null){

                        //empiezo otro ciclo para seguir la ola después del primer bombillo
                        while (temp.getNext() != null) {

                            //espero 1 segundo
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                throw new DLListException("se ha interrumpido la ola");
                            }

                            //apago el bombillo con el que empecé
                            temp.getData().setState(false);
                            temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //apago el bombillo con el que empecé con temp2
                            temp2.getData().setState(false);
                            temp2.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //paso al siguiente bombillo y lo prendo
                            temp = temp.getNext();
                            temp.getData().setState(true);
                            temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                            //con temp 2 me devuelvo un bombillo y lo prendo
                            temp2 = temp2.getPrev();
                            temp2.getData().setState(true);
                            temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                        }

                    }

                }

                //aumento el contador de pasos y sigo con el siguiente bombillo
                steps++;
                temp = temp.getNext();

            }


        } else{
            //aquí la lista sería impar
            int middle = (size / 2) + 1;


            //defino un ayudante y un contador para saber cuantos pasos llevo
            DLNode temp = head;
            int steps = 1;

            //recorro la lista para llegar al lugar donde quiero que empiece la ola
            while (temp != null) {

                //reviso si el puesto es igual al puesto buscado
                if (steps == middle){

                    //cuando llegue prendo el primer bombillo
                    temp.getData().setState(true);
                    temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //creo un nuevo ayudante para que haga la ola hacia atras y hago que prenda el primer bombillo de ese lado
                    DLNode temp2 = temp;

                    //reviso si hay mas bombillos adelante
                    if (temp.getNext() != null){

                        //empiezo otro ciclo para seguir la ola después del primer bombillo
                        while (temp.getNext() != null) {

                            //espero 1 segundo
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                throw new DLListException("se ha interrumpido la ola");
                            }

                            //apago el bombillo con el que empecé
                            temp.getData().setState(false);
                            temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //apago el bombillo con el que empecé con temp2
                            temp2.getData().setState(false);
                            temp2.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //paso al siguiente bombillo y lo prendo
                            temp = temp.getNext();
                            temp.getData().setState(true);
                            temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                            //con temp 2 me devuelvo un bombillo y lo prendo
                            temp2 = temp2.getPrev();
                            temp2.getData().setState(true);
                            temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                        }

                    }

                }

                //aumento el contador de pasos y sigo con el siguiente bombillo
                steps++;
                temp = temp.getNext();

            }

        }

    }


}
