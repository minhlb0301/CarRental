/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import minhlb.db.MyConnection;
import minhlb.dtos.Feedback;
import minhlb.dtos.Product;

/**
 *
 * @author Minh
 */
public class ProductDAO implements Serializable {

    private static Connection cn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    private static void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cn != null) {
            cn.close();
        }
    }

    public int getProductCount(String proName) throws Exception {
        int count = 0;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(ProductId) AS Count "
                    + "FROM Product "
                    + "WHERE ProductName LIKE ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + proName + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("Count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public List<Product> getProductByName(String proName, int from, int to) throws Exception {
        List<Product> listProduct = null;
        Product product = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT ProductId, ProductName, [Image], Color, [Year], c.CatogoryName, Price, Quantity, Available "
                    + "FROM (   SELECT ProductId, ProductName, [Image], Color, [Year], CategoryId, Price, Quantity, Available, ROW_NUMBER() OVER (ORDER BY [Year] ASC) AS RowNum "
                    + "         FROM Product "
                    + "         WHERE ProductName LIKE ? AND Available >= 0) AS p JOIN Category c ON p.CategoryId = c.CategoryId "
                    + "WHERE p.RowNum BETWEEN ? AND ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + proName + "%");
            ps.setInt(2, from);
            ps.setInt(3, to);
            rs = ps.executeQuery();
            listProduct = new ArrayList<>();
            while (rs.next()) {
                product = new Product(rs.getString("ProductId"), rs.getString("ProductName"), rs.getString("Image"), rs.getString("Color"),
                        rs.getString("CatogoryName"), rs.getInt("Year"), rs.getInt("Quantity"), rs.getInt("Available"), rs.getFloat("Price"));
                listProduct.add(product);
            }
        } finally {
            closeConnection();
        }
        return listProduct;
    }

    public Product getProductById(String productId) throws Exception {
        Product product = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT ProductName, [Image], Color, CatogoryName, Price, Available "
                    + "FROM Product p JOIN Category c ON p.CategoryId = c.CategoryId "
                    + "WHERE ProductId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product(productId, rs.getString("ProductName"), rs.getString("Image"), rs.getString("Color"), 
                        rs.getString("CatogoryName"), 0, 0, rs.getInt("Available"), rs.getFloat("Price"));
            }
        } finally {
            closeConnection();
        }
        return product;
    }
    
    public boolean updateRemainCar(String productId, int orderQuantity, boolean isDeleteOrder) throws Exception{
        boolean isUpdate = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "";
            if(isDeleteOrder == false){
                sql = "UPDATE Product "
                    + "SET Available = Available - ? "
                    + "WHERE ProductId = ?";
            }else{
                sql = "UPDATE Product "
                        + "SET Available = Available + ? "
                        + "WHERE ProductId = ?";
            }
            ps = cn.prepareStatement(sql);
            ps.setInt(1, orderQuantity);
            ps.setString(2, productId);
            isUpdate = ps.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return isUpdate;
    }
    
    public boolean insertFeedback(String userId, String productId, int rate) throws Exception {
        boolean isInsert = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO Feedback(UserId, ProductId, Rate) "
                    + "VALUES(?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, productId);
            ps.setInt(3, rate);
            isInsert = ps.executeUpdate() > 0;
        }finally {
            closeConnection();
        }
        return isInsert;
    }
    
    public Feedback getFeedbackRate(String productId) throws Exception {
        Feedback feedback = null;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "SELECT AVG(Rate) AS Rate "
                    + "FROM Feedback "
                    + "WHERE ProductId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if(rs.next()){
                feedback = new Feedback();
                feedback.setProductId(productId);
                feedback.setRate(rs.getInt("Rate"));
            }
        }finally {
            closeConnection();
        }
        return feedback;
    }
}
