package model.repository;

import model.entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AluguelRepository implements BaseRepository<Aluguel> {

    LocalDateTime dataAtual = LocalDateTime.now();

    @Override
    public Aluguel salvar(Aluguel aluguel) {
        Connection conn = Banco.getConnection();
        String query = " INSERT INTO aluguel(data_checkin, data_checkoutPrevisto, data_checkoutEfetivo, valorTotal, valorDiaria, qtdDias, valorLimpeza, valorMulta, id_imovel, id_inquilino)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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
        String query = " UPDATE aluguel SET data_checkin=?, data_checkoutPrevisto=?, data_checkoutEfetivo=?, valorTotal=?, valorDiaria=?, qtdDias=?, valorLimpeza=?, valorMulta=?, id_imovel=?, id_inquilino=?"
                        + " WHERE id=? ";
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        boolean alterado = false;
        try{
            preencherPstmt(aluguel, pstmt);

            pstmt.setInt(11, aluguel.getId());
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
            if(rs.next()){
                a = preencherRs(rs);
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
            while(rs.next()){
                Aluguel a = preencherRs(rs);
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
            while(rs.next()){
                Aluguel a = preencherRs(rs);
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
            while(rs.next()){
                Aluguel a = preencherRs(rs);
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

    public ArrayList<Aluguel> consultarComSeletor(AluguelSeletor seletor){
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ArrayList<Aluguel> alugueis = new ArrayList<>();
        ResultSet rs = null;

        String query = " SELECT a.* FROM aluguel a "
                        + " INNER JOIN imovel imv ON a.id_imovel = imv.id "
                        + " INNER JOIN inquilino inq ON a.id_inquilino = inq.id ";

        if(seletor.temFiltro()){
            query = preencherFiltros(seletor, query);
        }

        if(seletor.temPaginacao()){
            query += " LIMIT " + seletor.getLimite()
                    + " OFFSET " + seletor.getOffset();
        }

        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Aluguel a = preencherRs(rs);
                alugueis.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar alugueis com seletor");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return alugueis;
    }

    public int contarRegistros(AluguelSeletor seletor) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        int totalRegistros = 0;
        ResultSet rs = null;
        String query = " SELECT count(*) FROM aluguel a "
                     + " INNER JOIN imovel imv ON a.id_imovel = imv.id "
                     + " INNER JOIN inquilino inq ON a.id_inquilino = inq.id ";

        if(seletor.temFiltro()) {
            query = preencherFiltros(seletor, query);
        }

        try {
            rs = stmt.executeQuery(query);
            if(rs.next()){
                totalRegistros = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar registros de alugueis");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return totalRegistros;
    }

    public int contarPaginas(AluguelSeletor seletor) {
        int totalPaginas = 0;
        int totalRegistros = this.contarRegistros(seletor);

        totalPaginas = totalRegistros / seletor.getLimite();
        int resto = totalRegistros % seletor.getLimite();

        if(resto > 0) {
            totalPaginas++;
        }

        return totalPaginas;
    }

    public void preencherPstmt(Aluguel aluguel, PreparedStatement pstmt) throws SQLException{
        pstmt.setTimestamp(1, Timestamp.valueOf(aluguel.getDataCheckin()));
        pstmt.setTimestamp(2, Timestamp.valueOf(aluguel.getDataCheckoutPrevisto()));
        if(aluguel.getDataCheckoutEfetivo() == null || aluguel.getDataCheckoutEfetivo().equals("")){
            pstmt.setNull(3, java.sql.Types.TIMESTAMP);
        } else{
            pstmt.setTimestamp(3, Timestamp.valueOf(aluguel.getDataCheckoutEfetivo()));
        }

        pstmt.setDouble(4, aluguel.getValorTotal());
        pstmt.setDouble(5, aluguel.getValorDiaria());
        pstmt.setInt(6, aluguel.getQtdDias());
        pstmt.setDouble(7, aluguel.getValorLimpeza());
        pstmt.setDouble(8, aluguel.getValorMulta());
        pstmt.setInt(9, aluguel.getImovel().getId());
        pstmt.setInt(10, aluguel.getInquilino().getId());
    }

    public Aluguel preencherRs(ResultSet rs) throws SQLException{
        Aluguel a = new Aluguel();
        ImovelRepository imvRepo = new ImovelRepository();
        InquilinoRepository inqRepo = new InquilinoRepository();

        a.setId(rs.getInt("id"));
        a.setDataCheckin(rs.getTimestamp("data_Checkin").toLocalDateTime());
        a.setDataCheckoutPrevisto(rs.getTimestamp("data_CheckoutPrevisto").toLocalDateTime());
        if(rs.getTimestamp("data_CheckoutEfetivo") == null){
            a.setDataCheckoutEfetivo(null);
        } else {
            a.setDataCheckoutEfetivo(rs.getTimestamp("data_CheckoutEfetivo").toLocalDateTime());
        }
        a.setValorTotal(rs.getDouble("valorTotal"));
        a.setValorDiaria(rs.getDouble("valorDiaria"));
        a.setQtdDias(rs.getInt("qtdDias"));
        a.setValorLimpeza(rs.getDouble("valorLimpeza"));
        a.setValorMulta(rs.getDouble("valorMulta"));
        Imovel imv = imvRepo.consultarPorId(rs.getInt("id_imovel"));
        a.setImovel(imv);
        Inquilino inq = inqRepo.consultarPorId(rs.getInt("id_inquilino"));
        a.setInquilino(inq);

        return a;
    }

    public String preencherFiltros(AluguelSeletor seletor, String query){
        query += " WHERE ";
        boolean primeiro = true;

        if((seletor.getDataCheckinInicio() != null) && (seletor.getDataCheckinFinal() != null)) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkin BETWEEN '" + seletor.getDataCheckinInicio() + "' AND '" + seletor.getDataCheckinFinal() + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckinInicio() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkin BETWEEN '" + seletor.getDataCheckinInicio() + "' AND '" + dataAtual + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckinFinal() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkin BETWEEN '0000-00-00' AND '" + seletor.getDataCheckinFinal() + "'";
            primeiro = false;
        }
        if((seletor.getDataCheckoutPrevistoInicio() != null) && (seletor.getDataCheckoutPrevistoFinal() != null)) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutPrevisto BETWEEN '" + seletor.getDataCheckoutPrevistoInicio() + "' AND '" + seletor.getDataCheckoutPrevistoFinal() + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckoutPrevistoInicio() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutPrevisto BETWEEN '" + seletor.getDataCheckoutPrevistoInicio() + "' AND '" + dataAtual + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckoutPrevistoFinal() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutPrevisto BETWEEN '0000-00-00' AND '" + seletor.getDataCheckoutPrevistoFinal() + "'";
            primeiro = false;
        }
        if((seletor.getDataCheckoutEfetivoInicio() != null) && (seletor.getDataCheckoutEfetivoFinal() != null)) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutEfetivo BETWEEN '" + seletor.getDataCheckoutEfetivoInicio() + "' AND '" + seletor.getDataCheckoutEfetivoFinal() + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckoutEfetivoInicio() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutEfetivo BETWEEN '" + seletor.getDataCheckoutEfetivoInicio() + "' AND '" + dataAtual + "'";
            primeiro = false;
        }
        if(seletor.getDataCheckoutEfetivoFinal() != null) {
            if(!primeiro) {
                query += " AND ";
            }
            query += " a.data_checkoutEfetivo BETWEEN '0000-00-00' AND '" + seletor.getDataCheckoutEfetivoFinal() + "'";
            primeiro = false;
        }
        if((seletor.getValorTotalMin() > 0) && (seletor.getValorTotalMax() > 0)){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorTotal BETWEEN " + seletor.getValorTotalMin() + " AND " + seletor.getValorTotalMax() + " ";
            primeiro = false;
        }
        if(seletor.getValorTotalMin() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorTotal BETWEEN " + seletor.getValorTotalMin() + " AND 99999999999999999 ";
            primeiro = false;
        }
        if(seletor.getValorTotalMax() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorTotal BETWEEN 0 AND " + seletor.getValorTotalMax() + " ";
            primeiro = false;
        }
        if((seletor.getValorDiariaMin() > 0) && (seletor.getValorDiariaMax() > 0)){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorDiaria BETWEEN " + seletor.getValorDiariaMin() + " AND " + seletor.getValorDiariaMax() + " ";
            primeiro = false;
        }
        if(seletor.getValorDiariaMin() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorDiaria BETWEEN " + seletor.getValorDiariaMin() + " AND 99999999999999999 ";
            primeiro = false;
        }
        if(seletor.getValorDiariaMax() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorDiaria BETWEEN 0 AND " + seletor.getValorDiariaMax() + " ";
            primeiro = false;
        }
        if((seletor.getValorLimpezaMin() > 0) && (seletor.getValorLimpezaMax() > 0)){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorLimpeza BETWEEN " + seletor.getValorLimpezaMin() + " AND " + seletor.getValorLimpezaMax() + " ";
            primeiro = false;
        }
        if(seletor.getValorLimpezaMin() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorLimpeza BETWEEN " + seletor.getValorLimpezaMin() + " AND 99999999999999999 ";
            primeiro = false;
        }
        if(seletor.getValorLimpezaMax() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorLimpeza BETWEEN 0 AND " + seletor.getValorLimpezaMax() + " ";
            primeiro = false;
        }
        if((seletor.getValorMultaMin() > 0) && (seletor.getValorMultaMax() > 0)){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorMulta BETWEEN " + seletor.getValorMultaMin() + " AND " + seletor.getValorMultaMax() + " ";
            primeiro = false;
        }
        if(seletor.getValorMultaMin() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorMulta BETWEEN " + seletor.getValorMultaMin() + " AND 99999999999999999 ";
            primeiro = false;
        }
        if(seletor.getValorMultaMax() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.valorMulta BETWEEN 0 AND " + seletor.getValorMultaMax() + " ";
            primeiro = false;
        }
        if((seletor.getQtdDiasMin() > 0) && (seletor.getQtdDiasMax() > 0)){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.qtdDias BETWEEN " + seletor.getQtdDiasMin() + " AND " + seletor.getQtdDiasMax() + " ";
            primeiro = false;
        }
        if(seletor.getQtdDiasMin()  > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.qtdDias BETWEEN " + seletor.getQtdDiasMin() + " AND 99999999999999999 ";
            primeiro = false;
        }
        if(seletor.getQtdDiasMax() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.qtdDias BETWEEN 0 AND " + seletor.getQtdDiasMax() + " ";
            primeiro = false;
        }
        if(seletor.getIdImovel() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.id_imovel = " + seletor.getIdImovel();
            primeiro = false;
        }
        if(seletor.getIdInquilino() > 0){
            if(!primeiro){
                query += " AND ";
            }
            query += " a.id_inquilino = " + seletor.getIdInquilino();
            primeiro = false;
        }
        return query;
    }
}
