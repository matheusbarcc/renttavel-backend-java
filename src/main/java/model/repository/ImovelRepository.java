package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Anfitriao;
import model.entity.Endereco;
import model.entity.Imovel;
import model.entity.ImovelSeletor;

public class  ImovelRepository implements BaseRepository<Imovel>{

    @Override
    public Imovel salvar(Imovel imovel) {
        String query = " INSERT INTO imovel(nome, tipo, capacidadePessoas, qtdQuarto, qtdCama, qtdBanheiro, descricao, ocupado, id_endereco, id_anfitriao)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

        try{
            preencherPstmt(imovel, pstmt);

            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                imovel.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao salvar imovel");
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
        String query = " DELETE FROM imovel WHERE id=" + id + " ";

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
        String query = " UPDATE imovel SET nome=?, tipo=?, capacidadePessoas=?, qtdQuarto=?, qtdCama=?, qtdBanheiro=?, descricao=?, ocupado=?, id_endereco=?, id_anfitriao=?"
                        + " WHERE id=? ";
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        boolean alterado = false;

        try{
            preencherPstmt(imovel, pstmt);

            pstmt.setInt(11, imovel.getId());
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
        String query = " SELECT * FROM imovel WHERE id=" + id + " ";
        ResultSet rs = null;
        Imovel i = null;

        try{
            rs = stmt.executeQuery(query);
            if(rs.next()){
                i = preencherRs(rs);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar imovel por id");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return i;
    }

    @Override
    public ArrayList<Imovel> consultarTodos() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM imovel ";
        ResultSet rs = null;
        ArrayList<Imovel> imoveis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Imovel i = preencherRs(rs);
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

    public ArrayList<Imovel> consultarPorEndereco(int idEndereco) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM imovel WHERE id_endereco=" + idEndereco + " ";
        ResultSet rs = null;
        ArrayList<Imovel> imoveis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Imovel i = preencherRs(rs);
                imoveis.add(i);
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar imoveis por endereco");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return imoveis;
    }

    public ArrayList<Imovel> consultarPorAnfitriao(int idAnfitriao) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM imovel WHERE id_anfitriao=" + idAnfitriao + " ";
        ResultSet rs = null;
        ArrayList<Imovel> imoveis = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Imovel i = preencherRs(rs);
                imoveis.add(i);
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar imoveis por anfitriao");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return imoveis;
    }

    public ArrayList<Imovel> consultarComSeletor(ImovelSeletor seletor) {
        ArrayList<Imovel> imoveis = new ArrayList<>();
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet rs = null;

        String query = " SELECT i.* FROM imovel i "
                       + " INNER JOIN endereco end ON i.id_endereco = end.id "
                       + " INNER JOIN anfitriao anf ON i.id_anfitriao = anf.id ";

        if (seletor.temFiltro()) {
            query = preencherFiltros(seletor, query);
        }

        if (seletor.temPaginacao()) {
            query += " LIMIT " + seletor.getLimite()
                    + " OFFSET " + seletor.getOffset();
        }

        try {
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                Imovel i = preencherRs(rs);
                imoveis.add(i);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar imoveis com seletor");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return imoveis;
    }

    public int contarRegistros(ImovelSeletor seletor){
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        int totalRegistros = 0;
        ResultSet rs = null;
        String query = " SELECT count(*) FROM imovel i "
                    + " INNER JOIN endereco end ON i.id_endereco = end.id "
                    + " INNER JOIN anfitriao anf ON i.id_anfitriao = anf.id ";

        if(seletor.temFiltro()){
            query = preencherFiltros(seletor, query);
        }

        try{
            rs = stmt.executeQuery(query);
            if(rs.next()){
                totalRegistros = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar registros de imovel");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return totalRegistros;
    }

    public int contarPaginas(ImovelSeletor seletor){
        int totalPaginas = 0;
        int totalRegistros = this.contarRegistros(seletor);

        totalPaginas = totalRegistros / seletor.getLimite();
        int resto = totalRegistros % seletor.getLimite();

        if(resto > 0){
            totalPaginas++;
        }

        return totalPaginas;
    }

    public void preencherPstmt(Imovel imovel, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, imovel.getNome());
        pstmt.setInt(2, imovel.getTipo());
        pstmt.setInt(3, imovel.getCapacidadePessoas());
        pstmt.setInt(4, imovel.getQtdQuarto());
        pstmt.setInt(5, imovel.getQtdCama());
        pstmt.setInt(6, imovel.getQtdBanheiro());
        pstmt.setString(7, imovel.getDescricao());
        pstmt.setBoolean(8, imovel.getIsOcupado());
        pstmt.setInt(9, imovel.getEndereco().getId());
        pstmt.setInt(10, imovel.getAnfitriao().getId());
    }

    public Imovel preencherRs(ResultSet rs) throws SQLException {
        Imovel i = new Imovel();
        EnderecoRepository endRepo = new EnderecoRepository();
        AnfitriaoRepository anfRepo = new AnfitriaoRepository();

        i.setId(rs.getInt("id"));
        i.setNome(rs.getString("nome"));
        i.setTipo(rs.getInt("tipo"));
        i.setCapacidadePessoas(rs.getInt("capacidadePessoas"));
        i.setQtdQuarto(rs.getInt("qtdQuarto"));
        i.setQtdCama(rs.getInt("qtdCama"));
        i.setQtdBanheiro(rs.getInt("qtdBanheiro"));
        i.setDescricao(rs.getString("descricao"));
        i.setIsOcupado(rs.getBoolean("ocupado"));
        Endereco end = endRepo.consultarPorId(rs.getInt("id_endereco"));
        i.setEndereco(end);
        Anfitriao anf = anfRepo.consultarPorId(rs.getInt("id_anfitriao"));
        i.setAnfitriao(anf);

        return i;
    }

    public String preencherFiltros(ImovelSeletor seletor, String query) {
        query += " WHERE ";
        boolean primeiro = true;

        if (seletor.getNome() != null && !seletor.getNome().trim().isEmpty()) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " UPPER(i.nome) LIKE UPPER('%" + seletor.getNome() + "%') ";
            primeiro = false;
        }

        List<Integer> tipos = seletor.getTipos();

        if (tipos != null && !tipos.isEmpty()) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.tipo IN (";

            for (int i = 0; i < tipos.size(); i++) {
                query += tipos.get(i);
                if (i < tipos.size() - 1) {
                    query += ", ";
                }
            }
            query += ")";
            primeiro = false;
        }

        if (seletor.getCapacidadePessoas() > 0) {
            if (!primeiro) {
                query += " AND ";
            }
            if (seletor.getCapacidadePessoas() >= 7) {
                query += " i.capacidadePessoas >= 7";
            } else {
                query += " i.capacidadePessoas = " + seletor.getCapacidadePessoas();
            }
            primeiro = false;
        }

        if (seletor.getQtdQuarto() > 0) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.qtdQuarto = " + seletor.getQtdQuarto();
            primeiro = false;
        }

        if (seletor.getQtdCama() > 0) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.qtdCama = " + seletor.getQtdCama();
            primeiro = false;
        }

        if (seletor.getQtdBanheiro() > 0) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.qtdBanheiro = " + seletor.getQtdBanheiro();
            primeiro = false;
        }

        if (seletor.getIsOcupado()) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.ocupado = " + seletor.getIsOcupado();
            primeiro = false;
        }

        if (seletor.getIdEndereco() > 0) {
            if (!primeiro) {
                query += " AND ";
            }
            query += " i.id_endereco = " + seletor.getIdEndereco();
            primeiro = false;
        }

        return query;
    }
}
