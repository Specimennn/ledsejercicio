package co.edu.umanizales.ledsejercicio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@Data
public class LedBulb {

    private String id;

    private boolean state;

    private LocalTime laston;

    private LocalTime lastoff;

    public LedBulb(String id) {
        this.id = id;
        this.state = false;
    }

}
