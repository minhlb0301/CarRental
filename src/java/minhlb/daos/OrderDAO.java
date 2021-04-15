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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import minhlb.db.MyConnection;
import minhlb.dtos.Order;
import minhlb.dtos.OrderDetail;

/**
 *
 * @author Minh
 */
public class OrderDAO implements Serializable {
    
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
    
    public int getOrderCount() throws Exception {
        int count = 0;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(OrderId) AS COUNT FROM [Order]";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("COUNT");
            }
        } finally {
            closeConnection();
        }
        return count;
    }
    
    public boolean insertOrder(Order order) throws Exception {
        boolean isInsert = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO [Order](OrderId, UserId, Total, CreateDate, [Status]) "
                    + "VALUES(?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getUserId());
            ps.setFloat(3, order.getTotal());
            ps.setTimestamp(4, new Timestamp(order.getCreateDate().getTime()));
            ps.setString(5, order.getStatus());
            isInsert = ps.executeUpdate() > 0;
            
        } finally {
            closeConnection();
        }
        return isInsert;
    }
    
    public boolean insertOrderDetail(OrderDetail orderDetail) throws Exception {
        boolean isInsert = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO OrderDetail(DetailId, OrderId, ProductId, Quantity, Price, PickupDate, ReturnDate) "
                    + "VALUES(?,?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, orderDetail.getDetailId());
            ps.setString(2, orderDetail.getOrderId());
            ps.setString(3, orderDetail.getProductId());
            ps.setInt(4, orderDetail.getQuantity());
            ps.setFloat(5, orderDetail.getPrice());
            ps.setTimestamp(6, new Timestamp(orderDetail.getPickupDate().getTime()));
            ps.setTimestamp(7, new Timestamp(orderDetail.getReturnDate().getTime()));
            isInsert = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isInsert;
    }
    
    public List<Order> getOrder(String orderId, Date fromDate, Date toDate) throws Exception {
        List<Order> listOrder = null;
        Order order = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT OrderId, UserId, Total, CreateDate, [Status] "
                    + "FROM [Order] "
                    + "WHERE OrderId LIKE ? AND CreateDate BETWEEN ? AND ? "
                    + "ORDER BY CreateDate DESC";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + orderId + "%");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if(fromDate == null){
                fromDate = formatter.parse("2021-01-01");
            }
            if(toDate == null){
                toDate = new Date();
            }
            ps.setTimestamp(2, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(3, new Timestamp(toDate.getTime()));
            rs = ps.executeQuery();
            listOrder = new ArrayList<>();
            while(rs.next()){
                order = new Order(rs.getString("OrderId"), rs.getString("UserId"), rs.getString("Status"), rs.getFloat("Total"), rs.getDate("CreateDate"));
                listOrder.add(order);
            }
        } finally {
            closeConnection();
        }
        return listOrder;
    }
    
    public List<Order> getOrderByUserId(String userId) throws Exception {
        List<Order> listOrder = null;
        Order order = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT OrderId, Total, CreateDate, [Status] "
                    + "FROM [Order] "
                    + "WHERE UserId = ? "
                    + "ORDER BY CreateDate DESC";
            ps = cn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            listOrder = new ArrayList<>();
            while (rs.next()) {
                order = new Order(rs.getString("OrderId"), userId, rs.getString("Status"), rs.getFloat("Total"), rs.getDate("CreateDate"));
                listOrder.add(order);
            }
        } finally {
            closeConnection();
        }
        return listOrder;
    }
    
    public List<OrderDetail> getOrderDetail(String orderId) throws Exception {
        List<OrderDetail> listDetail = null;
        OrderDetail orderDetail = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT DetailId, od.ProductId, p.ProductName, od.Quantity, od.Price, PickupDate, ReturnDate "
                    + "FROM OrderDetail od JOIN Product p ON od.ProductId = p.ProductId "
                    + "WHERE OrderId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, orderId);
            rs = ps.executeQuery();
            listDetail = new ArrayList<>();
            while (rs.next()) {
                orderDetail = new OrderDetail(rs.getString("DetailId"), orderId, rs.getString("ProductId"), rs.getInt("Quantity"),
                        rs.getFloat("Price"), rs.getDate("PickupDate"), rs.getDate("ReturnDate"));
                orderDetail.setProductName(rs.getString("ProductName"));
                listDetail.add(orderDetail);
            }
        } finally {
            closeConnection();
        }
        return listDetail;
    }
    
    public boolean deleteOrder(String orderId) throws Exception {
        boolean isDelete = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "UPDATE [Order] "
                    + "SET [Status] = ? "
                    + "WHERE OrderId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "Inactive");
            ps.setString(2, orderId);
            isDelete = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isDelete;
    }
    
    public List<Order> searchOrder(String orderId, String userId, Date fromDate, Date toDate) throws Exception {
        List<Order> listOrder = null;
        Order order = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT Total, CreateDate, [Status] "
                    + "FROM [Order] WHERE OrderId LIKE ? AND UserId = ? AND CreateDate BETWEEN ? AND ?"
                    + "ORDER BY CreateDate DESC";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + orderId + "%");
            ps.setString(2, userId);
            ps.setTimestamp(3, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(4, new Timestamp(toDate.getTime()));
            rs = ps.executeQuery();
            listOrder = new ArrayList<>();
            while (rs.next()) {
                order = new Order(orderId, userId, rs.getString("Status"), rs.getFloat("Total"), rs.getDate("CreateDate"));
                listOrder.add(order);
            }
        } finally {
            closeConnection();
        }
        return listOrder;
    }
}
