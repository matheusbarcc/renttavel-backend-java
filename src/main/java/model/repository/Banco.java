package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class Banco {

//	@Resource(name="jdbc/sistema")
//	private static DataSource ds;

    private static final String NAME_DATASOURCE = "SenacDS";


    public static Connection getConnection(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup(NAME_DATASOURCE);
            Connection conn = ds.getConnection();
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao obter a Connection.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento da conexão.");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static Statement getStatement(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            return stmt;
        } catch (SQLException e) {
            System.out.println("Erro ao obter o Statement.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static void closeStatement(Statement stmt){
        try {
            if(stmt != null){
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do Statement.");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static PreparedStatement getPreparedStatement(Connection conn, String sql){
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt;
        } catch (Exception e) {
            System.out.println("Erro ao obter o PreparedStatement.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static PreparedStatement getPreparedStatementWithPk(Connection conn, String sql){
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return stmt;
        } catch (Exception e) {
            System.out.println("Erro ao obter o PreparedStatement.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static void closePreparedStatement(Statement stmt){
        try {
            if(stmt != null){
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do PreparedStatement.");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet result){
        try {
            if(result != null){
                result.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do ResultSet");
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
