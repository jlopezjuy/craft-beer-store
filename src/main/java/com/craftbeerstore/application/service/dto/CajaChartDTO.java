package com.craftbeerstore.application.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jlopez
 */
public class CajaChartDTO implements Serializable {

    private static final long serialVersionUID = -6986884614239029184L;

    @JsonProperty(value = "ingreso")
    private BigDecimal ingreso;
    @JsonProperty(value = "egreso")
    private BigDecimal egreso;

    public CajaChartDTO() {
    }

    public CajaChartDTO(BigDecimal ingreso, BigDecimal egreso) {
        this.ingreso = ingreso;
        this.egreso = egreso;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getEgreso() {
        return egreso;
    }

    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }
}
