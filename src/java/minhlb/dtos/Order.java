/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Minh
 */
public class Order implements Serializable{
    private String orderId, userId, status;
    private float total;
    private Date createDate;

    public Order(String orderId, String userId, String status, float total, Date createDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.total = total;
        this.createDate = createDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
}
