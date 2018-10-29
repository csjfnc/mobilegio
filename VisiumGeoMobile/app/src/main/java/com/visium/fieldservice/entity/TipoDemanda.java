package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum TipoDemanda {

    _0("SEM INFORMACAO"),
    _1("EDIFÍCIO"),
    _2("COMÉRCIO"),
    _3("TERRENO"),
    _4("RESIDENCIAL");

    private String tipo;

    TipoDemanda(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static TipoDemanda parse(String status) {
        for (TipoDemanda type : TipoDemanda.values()) {
            if (StringUtils.equals(type.getTipo(), status)) {
                return type;
            }
        }
        return _1;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (TipoDemanda type : TipoDemanda.values()) {
            names.add(type.getTipo());
        }
        return names;
    }
}
