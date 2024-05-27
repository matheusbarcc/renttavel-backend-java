package model.repository;

import model.entity.Aluguel;
import model.entity.Imovel;

import java.sql.*;
import java.util.ArrayList;

public class AluguelRepository implements BaseRepository<Aluguel> {

    @Override
    public Aluguel salvar(Aluguel aluguel) {
        Connection conn = Banco.getConnection();
        String query = " INSERT INTO aluguel(data_checkin, data_checkoutPrevisto, data_checkoutEfetivo, valorTotal, ocupado, valorDiaria, qtdDias, valorLimpeza, valorMulta, id_imovel, id_inquilino)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        try{
            preencherPstmt(aluguel, pstmt);

            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                aluguel.setId(rs.getInt(1));
            }
        } catch (SQLException e){
            System.out.println("Erro ao inserir aluguel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return aluguel;
    }

    @Override
    public boolean excluir(int id) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean excluido = false;
        String query = " DELETE FROM aluguel WHERE id=" + id + " ";
        try{
            if(stmt.executeUpdate(query) == 1){
                excluido = true;
            }
        } catch (SQLException e){
            System.out.println("Erro ao excluir aluguel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return excluido;
    }

    @Override
    public boolean alterar(Aluguel aluguel) {
        Connection conn = Banco.getConnection();
        String query = " UPDATE aluguel SET data_checkin=?, data_checkoutPrevisto=?, data_checkoutEfetivo=?, valorTotal=?, ocupado=?, valorDiaria=?, qtdDias=?, valorLimpeza=?, valorMulta=?, id_imovel=?, id_inquilino=?"
                        + " WHERE id=? ";
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        boolean alterado = false;
        try{
            preencherPstmt(aluguel, pstmt);

            pstmt.setInt(12, aluguel.getId());
            alterado = pstmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.out.println("Erro ao alterar aluguel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return alterado;
    }

    @Override
    public Aluguel consultarPorId(int id) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM aluguel WHERE id=" + id + " ";
        ResultSet rs = null;
        Aluguel a = null;

        try{
            rs = stmt.executeQuery(query);
            ImovelRepository imvRepo = new ImovelRepository();
            // InquilinoRepository inqRepo = new InquilinoRepository();

            if(rs.next()){
                a = new Aluguel();
                preencherAluguel(a, rs, imvRepo); // Adicionar InquilinoRepository nos parametros
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar imovel por id");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return a;
    }

    @Override
    public ArrayList<Aluguel> consultarTodos() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM aluguel ";
        ResultSet rs = null;
        ArrayList<Aluguel> alugueis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            ImovelRepository imvRepo = new ImovelRepository();
            // InquilinoRepository inqRepo = new InquilinoRepository();
            while(rs.next()){
                Aluguel a = new Aluguel();
                preencherAluguel(a, rs, imvRepo); // Adicionar InquilinoRepository nos parametros

                alugueis.add(a);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar todos os alugueis");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return alugueis;
    }

    public ArrayList<Aluguel> consultarPorImovel(int idImovel) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM aluguel WHERE id_imovel=" + idImovel + " ";
        ResultSet rs = null;
        ArrayList<Aluguel> alugueis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            ImovelRepository imvRepo = new ImovelRepository();
            // InquilinoRepository inqRepo = new InquilinoRepository();
            while(rs.next()){
                Aluguel a = new Aluguel();
                preencherAluguel(a, rs, imvRepo); // Adicionar InquilinoRepository nos parametros

                alugueis.add(a);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar todos os alugueis");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return alugueis;
    }

    public ArrayList<Aluguel> consultarPorInquilino(int idInquilino) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM aluguel WHERE id_inquilino=" + idInquilino + " ";
        ResultSet rs = null;
        ArrayList<Aluguel> alugueis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            ImovelRepository imvRepo = new ImovelRepository();
            // InquilinoRepository inqRepo = new InquilinoRepository();
            while(rs.next()){
                Aluguel a = new Aluguel();
                preencherAluguel(a, rs, imvRepo); // Adicionar InquilinoRepository nos parametros

                alugueis.add(a);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar todos os alugueis");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return alugueis;
    }

    public void preencherPstmt(Aluguel aluguel, PreparedStatement pstmt) throws SQLException{
        pstmt.setTimestamp(1, Timestamp.valueOf(aluguel.getDataCheckin()));
        pstmt.setTimestamp(2, Timestamp.valueOf(aluguel.getDataCheckoutPrevisto()));
        pstmt.setTimestamp(3, Timestamp.valueOf(aluguel.getDataCheckoutEfetivo()));
        pstmt.setDouble(4, aluguel.getValorTotal());
        pstmt.setInt(5, aluguel.getIsOcupado());
        pstmt.setDouble(6, aluguel.getValorDiaria());
        pstmt.setInt(7, aluguel.getQtdDias());
        pstmt.setDouble(8, aluguel.getValorLimpeza());
        pstmt.setDouble(9, aluguel.getValorMulta());
        pstmt.setInt(10, aluguel.getImovel().getId());
        pstmt.setInt(11, aluguel.getInquilino().getId());
    }

    public void preencherAluguel(Aluguel a, ResultSet rs, ImovelRepository imvRepo) throws SQLException{
        a.setId(rs.getInt("id"));
        a.setDataCheckin(rs.getTimestamp("data_Checkin").toLocalDateTime());
        a.setDataCheckoutPrevisto(rs.getTimestamp("data_CheckoutPrevisto").toLocalDateTime());
        a.setDataCheckoutEfetivo(rs.getTimestamp("data_CheckoutEfetivo").toLocalDateTime());
        a.setValorTotal(rs.getDouble("valorTotal"));
        a.setIsOcupado(rs.getInt("ocupado"));
        a.setValorDiaria(rs.getDouble("valorDiaria"));
        a.setQtdDias(rs.getInt("qtdDias"));
        a.setValorLimpeza(rs.getDouble("valorLimpeza"));
        a.setValorMulta(rs.getDouble("valorMulta"));
        Imovel imv = imvRepo.consultarPorId(rs.getInt("id_imovel"));
        a.setImovel(imv);
        // Inquilino inq = inqRepo.consultarPorId(rs.getInt("id_inquilino"));
        // a.setInquilino(inq);
    }
}
