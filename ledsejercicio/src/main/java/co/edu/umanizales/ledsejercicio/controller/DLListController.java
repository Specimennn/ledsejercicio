package co.edu.umanizales.ledsejercicio.controller;

import co.edu.umanizales.ledsejercicio.controller.dto.ErrorDTO;
import co.edu.umanizales.ledsejercicio.controller.dto.ResponseDTO;
import co.edu.umanizales.ledsejercicio.model.LedBulb;
import co.edu.umanizales.ledsejercicio.service.DLListService;
import co.edu.umanizales.ledsejercicio.exception.DLListException;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/dllist")


public class DLListController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ErrorDTO> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), null, errors), HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private DLListService DLListService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getBulbs() {

        List<LedBulb> bulbs = new ArrayList<>();

        try {

            bulbs = DLListService.getBulbs().print();

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, bulbs, null), HttpStatus.OK);
    }

    @GetMapping(path = "reset")
    public ResponseEntity<ResponseDTO> reset() {

        try {

            DLListService.getBulbs().reset();

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "bombillos reseteados", null), HttpStatus.OK);
    }

    @GetMapping(path = "add/{id}")
    public ResponseEntity<ResponseDTO> add(@PathVariable String id) {

        try {

            DLListService.getBulbs().add(new LedBulb(id));

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue añadadida", null), HttpStatus.OK);
    }

    @GetMapping(path = "addtostart/{id}")
    public ResponseEntity<ResponseDTO> addToStart(@PathVariable String id) {

        try {

            DLListService.getBulbs().addToStart(new LedBulb(id));

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue añadadida", null), HttpStatus.OK);
    }


    @GetMapping(path = "turnbulbon/{place}")
    public ResponseEntity<ResponseDTO> turnBulbOn(@PathVariable int place) {

        try {

            DLListService.getBulbs().turnBulbOn(place);

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue encendida", null), HttpStatus.OK);
    }

    @GetMapping(path = "turnbulboff/{place}")
    public ResponseEntity<ResponseDTO> turnBulbOff(@PathVariable int place) {

        try {

            DLListService.getBulbs().turnBulbOff(place);

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue encendida", null), HttpStatus.OK);
    }

    @GetMapping(path = "deletebulb/{place}")
    public ResponseEntity<ResponseDTO> deleteBulb(@PathVariable int place) {

        try {

            DLListService.getBulbs().deleteByPlace(place);

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue añadadida", null), HttpStatus.OK);
    }

    @GetMapping(path = "waveforwards/{place}")
    public ResponseEntity<ResponseDTO> waveForwards(@PathVariable int place) {

        if (place >  DLListService.getBulbs().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es mayor al tamaño de la fila", null), HttpStatus.OK);
        } else if (place <  0){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es menor a 0 de la fila", null), HttpStatus.OK);
        }

        try {
            DLListService.getBulbs().waveForwards(place);
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

    @GetMapping(path = "wavebackwards/{place}")
    public ResponseEntity<ResponseDTO> waveBackwards(@PathVariable int place) {

        if (place >  DLListService.getBulbs().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es mayor al tamaño de la fila", null), HttpStatus.OK);
        } else if (place <  0){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es menor a 0 de la fila", null), HttpStatus.OK);
        }

        try {
            DLListService.getBulbs().waveBackwards(place);
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

    @GetMapping(path = "lightshow")
    public ResponseEntity<ResponseDTO> lightshow() {

        try {
            DLListService.getBulbs().lightShow();
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

}
