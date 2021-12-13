package com.course.coursework.entities;

import java.util.Objects;

public class History {
    private int purchaseId;
    private int purchasePrice;
    private int modelId;
    private int userId;

    public History() {
    }

    public History(int purchaseId, int purchasePrice, int modelId, int userId) {
        this.purchaseId = purchaseId;
        this.purchasePrice = purchasePrice;
        this.modelId = modelId;
        this.userId = userId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return purchaseId == history.purchaseId && purchasePrice == history.purchasePrice && modelId == history.modelId && userId == history.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, purchasePrice, modelId, userId);
    }

    @Override
    public String toString() {
        return "History{" +
                "purchaseId=" + purchaseId +
                ", purchasePrice=" + purchasePrice +
                ", modelId=" + modelId +
                ", userId=" + userId +
                '}';
    }
}
