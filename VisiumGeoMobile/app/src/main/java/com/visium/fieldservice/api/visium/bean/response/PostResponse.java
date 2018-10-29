package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostMedidoresPosicao;
import com.visium.fieldservice.entity.PostPhotos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PostResponse implements Serializable {

    @SerializedName("IdPoste")
    private long id;

    @SerializedName("NumeroPosteNaOS")
    private int postNumber;

    @SerializedName("Posicao")
    private PostLocationResponse location;

    @SerializedName("DataCadastro")
    private Date createDate;

    @SerializedName("DataExclusao")
    private Date deleteDate;

    @SerializedName("Finalizado")
    private boolean closed;

    @SerializedName("CodigoGeo")
    private long geoCode;

    @SerializedName("IdLogradouro")
    private int placeId;

    @SerializedName("IdCidade")
    private int cityId;

    @SerializedName("IdOrdemDeServico")
    private long orderId;

    @SerializedName("Fotos")
    private List<PostPhotosResponse> photos;

    @SerializedName("PontoEntregaAPI")
    private List<PostMedidoresPosicao> posicaoMedidores;

    @SerializedName("Altura")
    private double height;

    @SerializedName("Esforco")
    private String postEffort;
    
    @SerializedName("TipoPoste")
    private int postType;

    @SerializedName("Descricao")
    private String observations;

    @SerializedName("Excluido")
    private boolean excluido;

    @SerializedName("Update")
    private boolean update;

    @SerializedName("PontoAtualizacao")
    private PontoAtualizacao pontoAtualizacao;

    @SerializedName("Ocupante_s")
    private int ocupante_s;

    @SerializedName("Ocupante_d")
    private int ocupante_d;

    @SerializedName("DataFinalizado")
    private Date data_finalizado;


    public PostResponse() {}

    public PostResponse(Post post) {
        this.id = post.getId();
        this.postNumber = post.getPostNumber();
        this.location = new PostLocationResponse(post.getLocation());
        this.createDate = post.getCreateDate();
        this.deleteDate = post.getDeleteDate();
        this.closed = post.isClosed();
        this.geoCode = post.getGeoCode();
        this.placeId = post.getPlaceId();
        this.cityId = post.getCityId();
        this.orderId = post.getOrderId();
        this.height = post.getHeight();
        this.postEffort = post.getPostEffort();
        this.postType = post.getPostType();
        this.observations = post.getObservations();
        this.excluido = post.isExcluido();
        this.update = post.isUpdate();
        this.pontoAtualizacao = post.getPontoAtualizacao();
        this.ocupante_s = post.getOcupante_s();
        this.ocupante_d = post.getOcupante_d();
        this.data_finalizado = post.getData_finalizado();
        this.posicaoMedidores = post.getPosicoesMedidores();


        List<PostPhotos> list = post.getPhotos();
        photos = new ArrayList<PostPhotosResponse>();
        for(PostPhotos pp : list) {
            photos.add(new PostPhotosResponse(pp));
        }

        /*List<PostMedidoresPosicao> list2 = post.getPosicoesMedidores();
        posicaoMedidores = new ArrayList<PostMedidorPosicaoResponse>();
        for(PostMedidoresPosicao m : list2) {
            posicaoMedidores.add(new PostMedidorPosicaoResponse(m));
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

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
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

    public PostLocationResponse getLocation() {
        return location;
    }

    public void setLocation(PostLocationResponse location) {
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public List<PostPhotosResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PostPhotosResponse> photos) {
        this.photos = photos;
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

    public List<PostMedidoresPosicao> getPosicaoMedidores() {
        return posicaoMedidores;
    }

    public void setPosicaoMedidores(List<PostMedidoresPosicao> posicaoMedidores) {
        this.posicaoMedidores = posicaoMedidores;
    }
}