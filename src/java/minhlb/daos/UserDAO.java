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
import minhlb.db.MyConnection;
import minhlb.dtos.User;

/**
 *
 * @author Minh
 */
public class UserDAO implements Serializable {

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

    public User checkLogin(String userId, String password) throws Exception {
        User user = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT Username, [Address], Phone, CreateDate, IsActive, IsAdmin FROM [User] WHERE UserId = ? AND [Password] = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setUserId(userId);
                user.setUsername(rs.getNString("Username"));
                user.setAddress(rs.getNString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setCreateDate(rs.getDate("CreateDate"));
                user.setIsActive(rs.getBoolean("IsActive"));
                user.setIsAdmin(rs.getBoolean("IsAdmin"));
            }
        } finally {
            closeConnection();
        }
        return user;
    }
    public boolean register(User user) throws Exception{
        boolean result = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO [User](UserId, Password, Username, Phone, [Address], CreateDate, IsActive, IsAdmin, [Status]) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setNString(3, user.getUsername());
            ps.setString(4, user.getPhone());
            ps.setNString(5, user.getAddress());
            ps.setTimestamp(6, new Timestamp(user.getCreateDate().getTime()));
            ps.setBoolean(7, user.isIsActive());
            ps.setBoolean(8, user.isIsAdmin());
            ps.setString(9, user.getStatus());
            result = ps.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return result;
    }
    
    public User checkFacebookLogin(String facebookId)throws Exception{
        User user = null;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "SELECT Username, [Address], Phone, CreateDate, IsActive, IsAdmin FROM [User] Where UserId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, facebookId);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setAddress(rs.getNString("Address"));
                user.setCreateDate(rs.getDate("CreateDate"));
                user.setUsername(rs.getNString("Username"));
                user.setPhone(rs.getString("Phone"));
                user.setUserId(facebookId);
                user.setFacebookId(facebookId);
                user.setIsAdmin(rs.getBoolean("IsAdmin"));
                user.setIsActive(rs.getBoolean("IsActive"));
            }
        }finally{
            closeConnection();
        }
        return user;
    }
    
    public boolean registerFacebook(User facebookUser) throws Exception{
        boolean result = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO [User](UserId, Username, CreateDate, IsActive, IsAdmin, [Status]) "
                    + "VALUES(?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, facebookUser.getFacebookId());
            ps.setNString(2, facebookUser.getName());
            ps.setTimestamp(3, new Timestamp(facebookUser.getCreateDate().getTime()));
            ps.setBoolean(4, facebookUser.isIsActive());
            ps.setBoolean(5, facebookUser.isIsAdmin());
            ps.setString(6, facebookUser.getStatus());
            result = ps.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return result;
    }
}
