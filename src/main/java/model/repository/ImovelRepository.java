package model.repository;

import model.entity.Anfitriao;
import model.entity.Endereco;
import model.entity.Imovel;

import java.sql.*;
import java.util.ArrayList;

public class ImovelRepository implements BaseRepository<Imovel>{

    @Override
    public Imovel salvar(Imovel imovel) {
        String query = " INSERT INTO imovel(nome, tipo, capacidadePessoas, qtdQuarto, qtdCama, qtdBanheiro, descricao, id_endereco, id_anfitriao)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);

        try{
            pstmt.setString(1, imovel.getNome());
            pstmt.setInt(2, imovel.getTipo());
            pstmt.setInt(3, imovel.getCapacidadePessoas());
            pstmt.setInt(4, imovel.getQtdQuarto());
            pstmt.setInt(5, imovel.getQtdCama());
            pstmt.setInt(6, imovel.getQtdBanheiro());
            pstmt.setString(7, imovel.getDescricao());
            pstmt.setInt(8, imovel.getEndereco().getId());
            pstmt.setInt(9, imovel.getAnfitriao().getId());

            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                imovel.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir novo imovel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return imovel;
    }

    @Override
    public boolean excluir(int id) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean excluido = false;
        String query = "DELETE FROM imovel WHERE id = " + id;

        try{
            if(stmt.executeUpdate(query) == 1){
                excluido = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir imovel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return excluido;
    }

    @Override
    public boolean alterar(Imovel imovel) {
        Connection conn = Banco.getConnection();
        String query = " UPDATE imovel SET nome=?, tipo=?, capacidadePessoas=?, qtdQuarto=?, qtdCama=?, qtdBanheiro=?, descricao=?, id_endereco=?, id_anfitriao=?"
                        + " WHERE id=?";
        PreparedStatement pstmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);
        boolean alterado = false;

        try{
            pstmt.setString(1, imovel.getNome());
            pstmt.setInt(2, imovel.getTipo());
            pstmt.setInt(3, imovel.getCapacidadePessoas());
            pstmt.setInt(4, imovel.getQtdQuarto());
            pstmt.setInt(5, imovel.getQtdCama());
            pstmt.setInt(6, imovel.getQtdBanheiro());
            pstmt.setString(7, imovel.getDescricao());
            pstmt.setInt(8, imovel.getEndereco().getId());
            pstmt.setInt(9, imovel.getAnfitriao().getId());

            pstmt.setInt(10, imovel.getId());
            alterado = pstmt.executeUpdate() > 0;
        } catch (SQLException e){
            System.out.println("Erro ao alterar imovel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return alterado;
    }

    @Override
    public Imovel consultarPorId(int id) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = "SELECT * FROM imovel WHERE id=" + id;
        ResultSet rs = null;
        Imovel i = null;

        try{
            rs = stmt.executeQuery(query);
            // EnderecoRespository endRepo = new EnderecoRepository();
            // AnfitriaoRepository anfRepo = new AnfitriaoRepository();

            if(rs.next()){
                i = new Imovel();
                i.setId(rs.getInt("id"));
                i.setNome(rs.getString("nome"));
                i.setTipo(rs.getInt("tipo"));
                i.setCapacidadePessoas(rs.getInt("capacidadePessoas"));
                i.setQtdQuarto(rs.getInt("qtdQuarto"));
                i.setQtdCama(rs.getInt("qtdCama"));
                i.setQtdBanheiro(rs.getInt("qtdBanheiro"));
                i.setDescricao(rs.getString("descricao"));
                // Endereco end = endRepo.consultarPorId(rs.getInt("id_pais"));
                // i.setEndereco(end);
                // Anfitriao anf = anfRepo.consultarPorId(rs.getInt("id_anfitriao"));
                // i.setAnfitriao(anf);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar imovel por id");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return i;
    }

    @Override
    public ArrayList<Imovel> consultarTodos() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = "SELECT * FROM imovel";
        ResultSet rs = null;
        ArrayList<Imovel> imoveis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            // EnderecoRespository endRepo = new EnderecoRepository();
            // AnfitriaoRepository anfRepo = new AnfitriaoRepository();
            while(rs.next()){
                Imovel i = new Imovel();
                i.setId(rs.getInt("id"));
                i.setNome(rs.getString("nome"));
                i.setTipo(rs.getInt("tipo"));
                i.setCapacidadePessoas(rs.getInt("capacidadePessoas"));
                i.setQtdQuarto(rs.getInt("qtdQuarto"));
                i.setQtdCama(rs.getInt("qtdCama"));
                i.setQtdBanheiro(rs.getInt("qtdBanheiro"));
                i.setDescricao(rs.getString("descricao"));
                // Endereco end = endRepo.consultarPorId(rs.getInt("id_pais"));
                // i.setEndereco(end);
                // Anfitriao anf = anfRepo.consultarPorId(rs.getInt("id_anfitriao"));
                // i.setAnfitriao(anf);
                imoveis.add(i);
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar todos os imoveis");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return imoveis;
    }
}
