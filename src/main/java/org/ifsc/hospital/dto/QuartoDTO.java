package org.ifsc.hospital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuartoDTO {
    private Long id;
    private String descricao;
    private String status;
    private Long alaId;
}
