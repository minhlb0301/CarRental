/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.db;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Minh
 */
public class MyConnection {
    public static Connection getMyConnection() throws Exception {
        Context ctx = new InitialContext();
        Context envCtx = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("DBCon");
        Connection cn = ds.getConnection();
        return cn;
    }
}
