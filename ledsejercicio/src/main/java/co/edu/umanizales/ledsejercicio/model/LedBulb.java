package co.edu.umanizales.ledsejercicio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class LedBulb {

    private String id;

    private boolean state;

    private Date laston;

    private Date lastoff;

    public LedBulb(String id) {
        this.id = id;
        this.state = false;
    }

}
