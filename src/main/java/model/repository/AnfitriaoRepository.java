package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Anfitriao;


public class AnfitriaoRepository implements BaseRepository<Anfitriao>{

	@Override
	public Anfitriao salvar(Anfitriao anfitriao) {
		String query = " INSERT INTO anfitriao(nome, email, senha)" + " VALUES (?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {
			pstmt.setString(1, anfitriao.getNome());
			pstmt.setString(2, anfitriao.getEmail());
			pstmt.setString(3, anfitriao.getSenha());

			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				anfitriao.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar novo anfitriao");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return anfitriao;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluido = false;
		String query = " DELETE FROM anfitriao WHERE id=" + id + " ";

		try {
			if (stmt.executeUpdate(query) == 1) {
				excluido = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir anfitriao");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluido;
	}

	@Override
	public boolean alterar(Anfitriao anfitriao) {
		Connection conn = Banco.getConnection();
		String query = " UPDATE anfitriao SET nome=?, email=?, senha=?" + " WHERE id=? ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		boolean alterado = false;
		try {
			pstmt.setString(1, anfitriao.getNome());
			pstmt.setString(2, anfitriao.getEmail());
			pstmt.setString(3, anfitriao.getSenha());

			pstmt.setInt(4, anfitriao.getId());
			alterado = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar anfitriao");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterado;
	}

	@Override
	public Anfitriao consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM anfitriao WHERE id=" + id + " ";
		ResultSet rs = null;
		Anfitriao anf = null;

		try {
			rs = stmt.executeQuery(query);

			if (rs.next()) {
				anf = new Anfitriao();
				anf.setId(rs.getInt("id"));
				anf.setNome(rs.getString("nome"));
				anf.setEmail(rs.getString("email"));
				anf.setSenha(rs.getString("senha"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar anfitriao por id");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return anf;
	}

	@Override
	public ArrayList<Anfitriao> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM anfitriao ";
		ResultSet rs = null;
		ArrayList<Anfitriao> anfitrioes = new ArrayList<>();

		try {
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Anfitriao anf = new Anfitriao();
				anf.setId(rs.getInt("id"));
				anf.setNome(rs.getString("nome"));
				anf.setEmail(rs.getString("email"));
				anf.setSenha(rs.getString("senha"));

				anfitrioes.add(anf);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os anfitrioes");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return anfitrioes;
	}

}
