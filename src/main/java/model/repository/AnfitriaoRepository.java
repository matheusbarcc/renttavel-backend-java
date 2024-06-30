package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.dto.UsuarioDTO;
import model.entity.Anfitriao;
import model.entity.PerfilAcesso;
import util.StringUtils;


public class AnfitriaoRepository implements BaseRepository<Anfitriao>{

	@Override
	public Anfitriao salvar(Anfitriao anfitriao) {
		String query = " INSERT INTO anfitriao(nome, email, perfil_acesso, senha)" + " VALUES (?, ?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {
			preencherPstmt(anfitriao, pstmt);

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
		String query = " UPDATE anfitriao SET nome=?, email=?, perfil_acesso=?, senha=?" + " WHERE id=? ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		boolean alterado = false;
		try {
			preencherPstmt(anfitriao, pstmt);

			pstmt.setInt(5, anfitriao.getId());
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
	
	public boolean alterarIdSessao(Anfitriao anf) {
		Connection conn = Banco.getConnection();
		String query = " UPDATE anfitriao SET id_sessao=? "
						+ " WHERE id=?";
		
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		boolean alterado = false;
		try {
			pstmt.setString(1, anf.getIdSessao());
			pstmt.setInt(2, anf.getId());
			
			alterado = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar id_sessao do anfitriao");
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
				anf = preencherRs(rs);
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
				Anfitriao anf = preencherRs(rs);
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
	
	public Anfitriao consultarPorEmailSenha(UsuarioDTO dto) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM anfitriao"
						+ " WHERE email = '" + dto.getEmail() + "'"
						+ " AND senha = '" + StringUtils.cifrar(dto.getSenha()) + "' ";
		
		ResultSet rs = null;
		Anfitriao anf = new Anfitriao();
		
		try {
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				anf = this.preencherRs(rs);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar anfitriao com email (" + dto.getEmail() + ")");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return anf;
	}
	
	public Anfitriao consultarPorIdSessao(String idSessao) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Anfitriao anf = new Anfitriao();
		String query = " SELECT * FROM anfitriao "
				+ " WHERE id_sessao = '" + idSessao + "'";
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				anf = this.preencherRs(resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar anfitriao com idSessao (" + idSessao + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return anf;
	}
		
	public void preencherPstmt(Anfitriao anf, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, anf.getNome());
		pstmt.setString(2, anf.getEmail());
		pstmt.setString(3, anf.getPerfilAcesso().toString());
		pstmt.setString(4, StringUtils.cifrar(anf.getSenha()));
	}
	
	public Anfitriao preencherRs(ResultSet rs) throws SQLException {
		Anfitriao a = new Anfitriao();
		
		a.setId(rs.getInt("id"));
		a.setNome(rs.getString("nome"));
		a.setEmail(rs.getString("email"));
		a.setPerfilAcesso(PerfilAcesso.valueOf(rs.getString("perfil_acesso")));
		a.setIdSessao(rs.getString("id_sessao"));
		
		return a;
	}
	
}
