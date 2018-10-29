package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.LightingArmType;
import com.visium.fieldservice.entity.LightingLampType;
import com.visium.fieldservice.entity.LightingLightFixtureType;
import com.visium.fieldservice.entity.LightingPowerRating;
import com.visium.fieldservice.entity.LightingStatus;
import com.visium.fieldservice.entity.LightingTrigger;
import com.visium.fieldservice.entity.PontoAtualizacao;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class LightingResponse implements Serializable {

    @SerializedName("IdIp")
    private long id;

    @SerializedName("IdPoste")
    private long postId;

    @SerializedName("TipoBraco")
    private String armType;

    @SerializedName("TipoLuminaria")
    private String lightFixtureType;

    @SerializedName("QtdLuminaria")
    private int lightFixtureAmount;

    @SerializedName("TipoLampada")
    private String lampType;

    @SerializedName("Potencia")
    private double powerRating;

    @SerializedName("Fase")
    private String powerPhase;

    @SerializedName("Acionamento")
    private String trigger;

    @SerializedName("LampadaAcesa")
    private String status;

    @SerializedName("QtdLampada")
    private int lampAmount;

    @SerializedName("Excluido")
    private boolean excluido;

    @SerializedName("Update")
    private boolean update;

    @SerializedName("PontoAtualizacao")
    private PontoAtualizacao pontoAtualizacao;

    public LightingResponse() {}

    public LightingResponse(Lighting lighting) {
        this.id = lighting.getId();
        this.postId = lighting.getPostId();
        this.armType = lighting.getArmType().getName();
        this.lightFixtureType = lighting.getLightFixtureType().getName();
        this.lightFixtureAmount = lighting.getLightFixtureAmount();
        this.lampType = lighting.getLampType().getName();
        this.powerRating = lighting.getPowerRating().getRating();
        this.powerPhase = lighting.getPowerPhase();
        this.trigger = lighting.getTrigger().getName();
        this.status = lighting.getStatus().getName();
        this.lampAmount = lighting.getLampAmount();
        this.excluido = lighting.isExcluido();
        this.update = lighting.isUpdate();
        this.pontoAtualizacao = lighting.getPontoAtualizacao();
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

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public LightingArmType getArmType() {
        return LightingArmType.parse(armType);
    }

    public void setArmType(String armType) {
        this.armType = armType;
    }

    public LightingLightFixtureType getLightFixtureType() {
        return LightingLightFixtureType.parse(lightFixtureType);
    }

    public void setLightFixtureType(String lightFixtureType) {
        this.lightFixtureType = lightFixtureType;
    }

    public int getLightFixtureAmount() {
        return lightFixtureAmount;
    }

    public void setLightFixtureAmount(int lightFixtureAmount) {
        this.lightFixtureAmount = lightFixtureAmount;
    }

    public LightingLampType getLampType() {
        return LightingLampType.parse(lampType);
    }

    public void setLampType(String lampType) {
        this.lampType = lampType;
    }

    public LightingPowerRating getPowerRating() {
        return LightingPowerRating.parse(powerRating);
    }

    public void setPowerRating(double powerRating) {
        this.powerRating = powerRating;
    }

    public String getPowerPhase() {
        return powerPhase;
    }

    public void setPowerPhase(String powerPhase) {
        this.powerPhase = powerPhase;
    }

    public LightingTrigger getTrigger() {
        return LightingTrigger.parse(trigger);
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public LightingStatus getStatus() {
        return LightingStatus.parse(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLampAmount() {
        return lampAmount;
    }

    public void setLampAmount(int lampAmount) {
        this.lampAmount = lampAmount;
    }
}