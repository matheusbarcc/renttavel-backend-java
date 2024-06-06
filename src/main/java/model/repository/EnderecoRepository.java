package model.repository;

import java.sql.*;
import java.util.ArrayList;

import model.entity.Endereco;

public class EnderecoRepository implements BaseRepository<Endereco> {

	@Override
	public Endereco salvar(Endereco endereco) {
		Connection conn = Banco.getConnection();
		String query = " INSERT INTO endereco(numero, cep, rua, bairro, cidade, estado, pais)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, endereco.getNumero());
			pstmt.setString(2, endereco.getCep());
			pstmt.setString(3, endereco.getRua());
			pstmt.setString(4, endereco.getBairro());
			pstmt.setString(5, endereco.getCidade());
			pstmt.setString(6, endereco.getEstado());
			pstmt.setString(7, endereco.getPais());

			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				endereco.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar endereco");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return endereco;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluido = false;
		String query = " DELETE FROM endereco WHERE id=" + id + " ";
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluido = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir endereco");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluido;
	}

	@Override
	public boolean alterar(Endereco endereco) {
		Connection conn = Banco.getConnection();
		String query = " UPDATE aluguel SET data_checkin=?, data_checkoutPrevisto=?, data_checkoutEfetivo=?, valorTotal=?, ocupado=?, valorDiaria=?, qtdDias=?, valorLimpeza=?, valorMulta=?, id_imovel=?, id_inquilino=?"
				+ " WHERE id=? ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		boolean alterado = false;
		try {
			pstmt.setInt(1, endereco.getNumero());
			pstmt.setString(2, endereco.getCep());
			pstmt.setString(3, endereco.getRua());
			pstmt.setString(4, endereco.getBairro());
			pstmt.setString(5, endereco.getCidade());
			pstmt.setString(6, endereco.getEstado());
			pstmt.setString(7, endereco.getPais());

			pstmt.setInt(12, endereco.getId());
			alterado = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar endereco");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterado;
	}

	@Override
	public Endereco consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM endereco WHERE id=" + id + " ";
		ResultSet rs = null;
		Endereco end = null;

		try {
			rs = stmt.executeQuery(query);

			if (rs.next()) {
				end = new Endereco();
				end.setId(rs.getInt("id"));
				end.setNumero(rs.getInt("numero"));
				end.setCep(rs.getString("cep"));
				end.setRua(rs.getString("rua"));
				end.setBairro(rs.getString("bairro"));
				end.setCidade(rs.getString("cidade"));
				end.setEstado(rs.getString("estado"));
				end.setPais(rs.getString("pais"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar endereco por id");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return end;
	}

	@Override
	public ArrayList<Endereco> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM endereco ";
		ResultSet rs = null;
		ArrayList<Endereco> enderecos = new ArrayList<>();

		try {
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Endereco end = new Endereco();
				end = new Endereco();
				end.setId(rs.getInt("id"));
				end.setNumero(rs.getInt("numero"));
				end.setCep(rs.getString("cep"));
				end.setRua(rs.getString("rua"));
				end.setBairro(rs.getString("bairro"));
				end.setCidade(rs.getString("cidade"));
				end.setEstado(rs.getString("estado"));
				end.setPais(rs.getString("pais"));

				enderecos.add(end);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os enderecos");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return enderecos;
	}

}
