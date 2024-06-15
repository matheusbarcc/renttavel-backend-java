package model.repository;

import model.entity.Inquilino;
import model.entity.InquilinoSeletor;

import java.util.ArrayList;
import java.sql.*;

public class InquilinoRepository implements BaseRepository<Inquilino>{

	@Override
	public Inquilino salvar(Inquilino inquilino) {
		String query = " INSERT INTO inquilino(nome, email, telefone)" + " VALUES (?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {
			pstmt.setString(1, inquilino.getNome());
			pstmt.setString(2, inquilino.getEmail());
			pstmt.setString(3, inquilino.getTelefone());

			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				inquilino.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar inquilino");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return inquilino;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluido = false;
		String query = " DELETE FROM inquilino WHERE id=" + id + " ";

		try {
			if (stmt.executeUpdate(query) == 1) {
				excluido = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir inquilino");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluido;
	}

	@Override
	public boolean alterar(Inquilino inquilino) {
		Connection conn = Banco.getConnection();
		String query = " UPDATE inquilino SET nome=?, email=?, telefone=?" + " WHERE id=? ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		boolean alterado = false;
		try {
			pstmt.setString(1, inquilino.getNome());
			pstmt.setString(2, inquilino.getEmail());
			pstmt.setString(3, inquilino.getTelefone());

			pstmt.setInt(4, inquilino.getId());
			alterado = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar inquilino");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterado;
	}

	@Override
	public Inquilino consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM inquilino WHERE id=" + id + " ";
		ResultSet rs = null;
		Inquilino inq = null;

		try {
			rs = stmt.executeQuery(query);

			if (rs.next()) {
				inq = new Inquilino();
				inq.setId(rs.getInt("id"));
				inq.setNome(rs.getString("nome"));
				inq.setEmail(rs.getString("email"));
				inq.setTelefone(rs.getString("telefone"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar inquilino por id");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return inq;
	}

	@Override
	public ArrayList<Inquilino> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM inquilino ";
		ResultSet rs = null;
		ArrayList<Inquilino> inquilinos = new ArrayList<>();

		try {
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Inquilino inq = new Inquilino();
				inq.setId(rs.getInt("id"));
				inq.setNome(rs.getString("nome"));
				inq.setEmail(rs.getString("email"));
				inq.setTelefone(rs.getString("telefone"));

				inquilinos.add(inq);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os inquilinos");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return inquilinos;
	}
	
	public ArrayList<Inquilino> consultarComSeletor(InquilinoSeletor seletor) {
        ArrayList<Inquilino> inquilinos = new ArrayList<>();
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet rs = null;

        String query = " SELECT inq.* FROM inquilino inq ";

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
                Inquilino inq = preencherRs(rs);
                inquilinos.add(inq);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar inquilinos com seletor");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return inquilinos;
    }

	public int contarRegistros(InquilinoSeletor seletor){
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet rs = null;
		String query = " SELECT count(*) FROM inquilino inq";
		int totalRegistros = 0;

		if (seletor.temFiltro()) {
			query = preencherFiltros(seletor, query);
		}

		try{
			rs = stmt.executeQuery(query);
			if(rs.next()){
				totalRegistros = rs.getInt(1);
			}
		} catch (SQLException e){
			System.out.println("Erro ao contar registros de inquilino");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return totalRegistros;
	}

	public int contarPaginas(InquilinoSeletor seletor){
		int totalPaginas = 0;
		int totalRegistros = this.contarRegistros(seletor);

		totalPaginas = totalRegistros / seletor.getLimite();
		int resto = totalRegistros % seletor.getLimite();

		if(resto > 0){
			totalPaginas++;
		}

		return totalPaginas;
	}
	
	public Inquilino preencherRs(ResultSet rs) throws SQLException {
        Inquilino inq = new Inquilino();
        
		inq.setId(rs.getInt("id"));
		inq.setNome(rs.getString("nome"));
		inq.setEmail(rs.getString("email"));
		inq.setTelefone(rs.getString("telefone"));

        return inq;
    }
	
	public String preencherFiltros(InquilinoSeletor seletor, String query){
        query += " WHERE ";
        boolean primeiro = true;

        if(seletor.getNome() != null){
            if(!primeiro){
                query += " AND ";
            }
            query += " UPPER(inq.nome) LIKE UPPER('%" + seletor.getNome() + "%') ";
            primeiro = false;
        }
        if(seletor.getEmail() != null){
            if(!primeiro){
                query += " AND ";
            }
            query += " UPPER(inq.email) LIKE UPPER('%" + seletor.getEmail() + "%') ";
            primeiro = false;
        }
        if(seletor.getTelefone() != null){
            if(!primeiro){
                query += " AND ";
            }
            query += " UPPER(inq.telefone) LIKE UPPER('%" + seletor.getTelefone() + "%') ";
            primeiro = false;
        }
        return query;
    }

}
