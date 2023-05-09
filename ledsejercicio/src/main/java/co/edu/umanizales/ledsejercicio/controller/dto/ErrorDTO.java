package co.edu.umanizales.ledsejercicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDTO {
    private int code;
    private String message;

}
