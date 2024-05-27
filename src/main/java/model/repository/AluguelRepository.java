package model.repository;

import model.entity.Aluguel;
import model.entity.Imovel;
import model.repository.Banco;
import model.repository.BaseRepository;

import java.sql.*;
import java.util.ArrayList;

public class AluguelRepository implements BaseRepository<Aluguel> {

    @Override
    public Aluguel salvar(Aluguel aluguel) {
        Connection conn = Banco.getConnection();
        String query = " INSERT INTO aluguel(data_checkin, data_checkoutPrevisto, data_checkoutEfetivo, valorTotal, ocupado, valorDiaria, qtdDias, valorLimpeza, valorMulta, id_imovel, id_inquilino)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement pstmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);
        try{
            pstmt.setDate(1, Date.valueOf(aluguel.getDataCheckin().toLocalDate()));
            pstmt.setDate(2, Date.valueOf(aluguel.getDataCheckoutPrevisto().toLocalDate()));
            pstmt.setDate(3, Date.valueOf(aluguel.getDataCheckoutEfetivo().toLocalDate()));
            pstmt.setDouble(4, aluguel.getValorTotal());
            pstmt.setBoolean(5, aluguel.isOcupado());
            pstmt.setDouble(6, aluguel.getValorDiaria());
            pstmt.setInt(7, aluguel.getQtdDias());
            pstmt.setDouble(8, aluguel.getValorLimpeza());
            pstmt.setDouble(9, aluguel.getValorMulta());
            pstmt.setInt(10, aluguel.getImovel().getId());
            pstmt.setInt(11, aluguel.getInquilino().getId());

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
        PreparedStatement pstmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);
        boolean alterado = false;
        try{
            pstmt.setDate(1, Date.valueOf(aluguel.getDataCheckin().toLocalDate()));
            pstmt.setDate(2, Date.valueOf(aluguel.getDataCheckoutPrevisto().toLocalDate()));
            pstmt.setDate(3, Date.valueOf(aluguel.getDataCheckoutEfetivo().toLocalDate()));
            pstmt.setDouble(4, aluguel.getValorTotal());
            pstmt.setBoolean(5, aluguel.isOcupado());
            pstmt.setDouble(6, aluguel.getValorDiaria());
            pstmt.setInt(7, aluguel.getQtdDias());
            pstmt.setDouble(8, aluguel.getValorLimpeza());
            pstmt.setDouble(9, aluguel.getValorMulta());
            pstmt.setInt(10, aluguel.getImovel().getId());
            pstmt.setInt(11, aluguel.getInquilino().getId());

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
                a.setId(rs.getInt("id"));
                a.setDataCheckin(rs.getTimestamp("data_checkin").toLocalDateTime());
                a.setDataCheckoutPrevisto(rs.getTimestamp("data_CheckoutPrevisto").toLocalDateTime());
                a.setDataCheckoutEfetivo(rs.getTimestamp("data_CheckoutEfetivo").toLocalDateTime());
                a.setValorTotal(rs.getDouble("valorTotal"));
                a.setOcupado(rs.getBoolean("ocupado"));
                a.setValorDiaria(rs.getDouble("valorDiaria"));
                a.setQtdDias(rs.getInt("qtdDias"));
                a.setValorLimpeza(rs.getDouble("valorLimpeza"));
                a.setValorMulta(rs.getDouble("valorMulta"));
                Imovel imv = imvRepo.consultarPorId(rs.getInt("id_imovel"));
                a.setImovel(imv);
                // Inquilino inq = inqRepo.consultarPorId(rs.getInt("id_inquilino"));
                // a.setInquilino(inq);
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
                a.setId(rs.getInt("id"));
                a.setDataCheckin(rs.getTimestamp("data_Checkin").toLocalDateTime());
                a.setDataCheckoutPrevisto(rs.getTimestamp("data_CheckoutPrevisto").toLocalDateTime());
                a.setDataCheckoutEfetivo(rs.getTimestamp("data_CheckoutEfetivo").toLocalDateTime());
                a.setValorTotal(rs.getDouble("valorTotal"));
                a.setOcupado(rs.getBoolean("ocupado"));
                a.setValorDiaria(rs.getDouble("valorDiaria"));
                a.setQtdDias(rs.getInt("qtdDias"));
                a.setValorLimpeza(rs.getDouble("valorLimpeza"));
                a.setValorMulta(rs.getDouble("valorMulta"));
                Imovel imv = imvRepo.consultarPorId(rs.getInt("id_imovel"));
                a.setImovel(imv);
                // Inquilino inq = inqRepo.consultarPorId(rs.getInt("id_inquilino"));
                // a.setInquilino(inq);
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
}
