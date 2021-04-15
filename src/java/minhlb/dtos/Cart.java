/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.dtos;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Minh
 */
public class Cart implements Serializable{
    private String userId;
    private HashMap<String, Product> cart;

    public Cart() {
        this.userId = "Guest";
        this.cart = new HashMap<>();
    }
    
    public Cart(String userId) {
        this.userId = userId;
        this.cart = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Product> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product) throws Exception {
        if(this.cart.containsKey(product.getProductId())){
            int quantity = this.cart.get(product.getProductId()).getAvailable() + 1;
            product.setAvailable(quantity);
        }
        this.cart.put(product.getProductId(), product);
    }
    
    public void updateCart(String productId, int quantity, String pickupDate, String returnDate, int rentalDate) throws Exception {
        if(this.cart.containsKey(productId)){
            this.cart.get(productId).setAvailable(quantity);
            this.cart.get(productId).setPickupDate(pickupDate);
            this.cart.get(productId).setReturnDate(returnDate);
            this.cart.get(productId).setRentalDate(rentalDate);
        }
    }
    
    public void removeCart(String productId) throws Exception {
        if(this.cart.containsKey(productId)){
            this.cart.remove(productId);
        }
    }
    
    public float getTotal() throws Exception {
        float total = 0;
        for(Product product : this.cart.values()){
            total += product.getAvailable() * product.getPrice() * product.getRentalDate();
        }
        return total;
    }
}
