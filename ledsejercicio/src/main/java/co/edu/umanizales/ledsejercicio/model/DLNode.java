package co.edu.umanizales.ledsejercicio.model;

import lombok.Data;

@Data
public class DLNode {

    private LedBulb data;

    private DLNode next;

    private DLNode prev;

    public DLNode(LedBulb data) {
        this.data = data;
    }
}
