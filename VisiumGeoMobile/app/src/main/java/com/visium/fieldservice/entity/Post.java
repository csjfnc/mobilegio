package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.PostPhotosResponse;
import com.visium.fieldservice.api.visium.bean.response.PostResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class Post extends Equipment implements Serializable {

    private static final long serialVersionUID = 307781624170434706L;

    private long id;
    private int postNumber;
    private PostLocation location;
    private Date createDate;
    private Date deleteDate;
    private boolean closed;
    private long geoCode;
    private int placeId;
    private int cityId;
    private long orderId;
    private double height;
    private String postEffort;
    private int postType;
    private String observations;
    private List<PostPhotos> photos;
    private List<PostMedidoresPosicao> posicoesMedidores;
    private boolean excluido;
    private boolean update;
    private PontoAtualizacao pontoAtualizacao;
    private Date data_finalizado;

    private int ocupante_s;
    private int ocupante_d;

    public Post(){}

    public Post(PostResponse resp) {
        this.id = resp.getId();
        this.postNumber = resp.getPostNumber();
        this.location = new PostLocation(resp.getLocation());
        this.createDate = resp.getCreateDate();
        this.deleteDate = resp.getDeleteDate();
        this.closed = resp.isClosed();
        this.geoCode = resp.getGeoCode();
        this.placeId = resp.getPlaceId();
        this.cityId = resp.getCityId();
        this.orderId = resp.getOrderId();
        this.height = resp.getHeight();
        this.postEffort = resp.getPostEffort();
        this.postType = resp.getPostType();
        this.observations = resp.getObservations();
        this.excluido = resp.isExcluido();
        this.update = resp.isUpdate();
        this.posicoesMedidores = resp.getPosicaoMedidores();
        this.ocupante_s = resp.getOcupante_s();
        this.ocupante_d = resp.getOcupante_d();
        this.data_finalizado = resp.getData_finalizado();
        this.pontoAtualizacao = resp.getPontoAtualizacao();

        List<PostPhotosResponse> list = resp.getPhotos();
        photos = new ArrayList<PostPhotos>();
        for(PostPhotosResponse ppr : list) {
            photos.add(new PostPhotos(ppr));
        }
        /*List<PostMedidorPosicaoResponse> list2 = resp.getPosicoesMedidores();
        posicoesMedidores = new ArrayList<PostMedidoresPosicao>();
        for(PostMedidorPosicaoResponse r : list2) {
            posicoesMedidores.add(new PostMedidoresPosicao(r));
        }*/
    }
    public Date getData_finalizado() {
        return data_finalizado;
    }

    public void setData_finalizado(Date data_finalizado) {
        this.data_finalizado = data_finalizado;
    }

    public int getOcupante_s() {
        return ocupante_s;
    }

    public void setOcupante_s(int ocupante_s) {
        this.ocupante_s = ocupante_s;
    }

    public int getOcupante_d() {
        return ocupante_d;
    }

    public void setOcupante_d(int ocupante_d) {
        this.ocupante_d = ocupante_d;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PostLocation getLocation() {
        return location;
    }

    public void setLocation(PostLocation location) {
        this.location = location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public long getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(long geoCode) {
        this.geoCode = geoCode;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<PostPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PostPhotos> photos) {
        this.photos = photos;
    }

    public String getPostEffort() {
        return postEffort;
    }

    public void setPostEffort(String postEffort) {
        this.postEffort = postEffort;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public PontoAtualizacao getPontoAtualizacao() {
        return pontoAtualizacao;
    }

    public void setPontoAtualizacao(PontoAtualizacao pontoAtualizacao) {
        this.pontoAtualizacao = pontoAtualizacao;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public List<PostMedidoresPosicao> getPosicoesMedidores() {
        return posicoesMedidores;
    }

    public void setPosicoesMedidores(List<PostMedidoresPosicao> posicoesMedidores) {
        this.posicoesMedidores = posicoesMedidores;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Post && ((Post) o).getId() == this.id;
    }
}