package co.edu.umanizales.ledsejercicio.service;

import co.edu.umanizales.ledsejercicio.model.DLList;
import co.edu.umanizales.ledsejercicio.model.DLNode;
import co.edu.umanizales.ledsejercicio.model.LedBulb;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data

public class DLListService {

    private DLList bulbs;

    public DLListService() {
        bulbs = new DLList();

    }

    public void invert(){
            bulbs.invert();
    }
}
