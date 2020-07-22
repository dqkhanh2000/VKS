package com.sict.mobile.vks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("data")
    @Expose
    private String embeddingData;

    public TrainData(int id, String embeddingData) {
        this.id = id;
        this.embeddingData = embeddingData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmbeddingData() {
        return embeddingData;
    }

    public void setEmbeddingData(String embeddingData) {
        this.embeddingData = embeddingData;
    }
}
