package com.visium.fieldservice.entity;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaPhotosResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaResponse;
import com.visium.fieldservice.api.visium.bean.response.PostPhotosResponse;
import com.visium.fieldservice.entity.equipament.Medidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PontoEntrega implements Serializable {
    private long id;
    private long postId;
    private int status;
    private int tipoDemanda;
    private String classificacao;
    private String tipoImovel;
    private String complemento1;
    private String complemento2;
    private String logradouro;
    private double y;
    private double x;
    private int codigo_bd_geo;
    private boolean update;
    private boolean excluido;
    private List<PontoEntregaPhotos> photos;

    /*novo*/
    private long geoCode;
    private long orderId;
    //private PontoEntregaLocation location;
 //   private PontoAtualizacao pontoAtualizacao;
    private double y_atualizacao;
    private double x_atualizacao;
    private boolean closed;
    private int postNumber;
    private Lagradouro lagradouro;
    private int numero_local;
    private int numero_andares_edificio;
    private int numero_total_apartamentos;
    private String nome_edificio;
    private long cityId;

    public PontoEntrega(){}

    public PontoEntrega(PontoEntregaResponse obj) {
        this.id = obj.getId();
        this.postId = obj.getPostId();
        this.status = obj.getStatus();
        this.logradouro = obj.getLogradouro();
        this.codigo_bd_geo = obj.getCodigo_bd_geo();
        this.update = obj.isUpdate();
        this.excluido = obj.isExcluido();
        this.x = obj.getX();
        this.y = obj.getY();
        this.orderId = obj.getOrderId();
        this.geoCode = obj.getCodigo_bd_geo();

        //this.location = new PontoEntregaLocation(obj.getLocation());
       // this.pontoAtualizacao = obj.getPontoAtualizacao();
        this.postNumber = obj.getPostNumber();
        this.tipoDemanda = obj.getTipoDemanda();
        this.classificacao = obj.getClassificacao();
        this.tipoImovel = obj.getTipoImovel();
        this.complemento1 = obj.getComplemento1();
        this.complemento2 = obj.getComplemento2();
        this.lagradouro = obj.getLagradouro();
        this.numero_local =obj.getNumero_local();
        this.numero_andares_edificio = obj.getNumero_andares_edificio();
        this.numero_total_apartamentos = obj.getNumero_total_apartamentos();
        this.nome_edificio = obj.getNome_edificio();
        this.y_atualizacao = obj.getY_atualizacao();
        this.x_atualizacao = obj.getX_atualizacao();
        this.cityId = obj.getCityId();

        List<PontoEntregaPhotosResponse> list = obj.getPhotos();
        photos = new ArrayList<PontoEntregaPhotos>();
        for(PontoEntregaPhotosResponse ppr : list) {
            photos.add(new PontoEntregaPhotos(ppr));
        }
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public double getY_atualizacao() {
        return y_atualizacao;
    }

    public void setY_atualizacao(double y_atualizacao) {
        this.y_atualizacao = y_atualizacao;
    }

    public double getX_atualizacao() {
        return x_atualizacao;
    }

    public void setX_atualizacao(double x_atualizacao) {
        this.x_atualizacao = x_atualizacao;
    }

    public int getNumero_local() {
        return numero_local;
    }

    public void setNumero_local(int numero_local) {
        this.numero_local = numero_local;
    }

    public int getNumero_andares_edificio() {
        return numero_andares_edificio;
    }

    public void setNumero_andares_edificio(int numero_andares_edificio) {
        this.numero_andares_edificio = numero_andares_edificio;
    }

    public int getNumero_total_apartamentos() {
        return numero_total_apartamentos;
    }

    public void setNumero_total_apartamentos(int numero_total_apartamentos) {
        this.numero_total_apartamentos = numero_total_apartamentos;
    }

    public String getNome_edificio() {
        return nome_edificio;
    }

    public void setNome_edificio(String nome_edificio) {
        this.nome_edificio = nome_edificio;
    }

    public Lagradouro getLagradouro() {
        return lagradouro;
    }

    public void setLagradouro(Lagradouro lagradouro) {
        this.lagradouro = lagradouro;
    }

    public int getTipoDemanda() {
        return tipoDemanda;
    }

    public void setTipoDemanda(int tipoDemanda) {
        this.tipoDemanda = tipoDemanda;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public void setComplemento1(String complemento1) {
        this.complemento1 = complemento1;
    }

    public void setComplemento2(String complemento2) {
        this.complemento2 = complemento2;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public String getComplemento1() {
        return complemento1;
    }

    public String getComplemento2() {
        return complemento2;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(long geoCode) {
        this.geoCode = geoCode;
    }

    public List<PontoEntregaPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PontoEntregaPhotos> photos) {
        this.photos = photos;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PontoEntrega
                && ((PontoEntrega) o).getId() == this.id;
    }

    public int getCodigo_bd_geo() {
        return codigo_bd_geo;
    }

    public void setCodigo_bd_geo(int codigo_bd_geo) {
        this.codigo_bd_geo = codigo_bd_geo;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }


}