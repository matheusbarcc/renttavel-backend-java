package model.repository;

import java.sql.*;
import java.util.ArrayList;


import model.entity.Endereco;
import model.entity.EnderecoSeletor;


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
		String query = " UPDATE endereco SET numero=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, pais=?"
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

			pstmt.setInt(8, endereco.getId());
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

	public ArrayList<Endereco> consultarComSeletor(EnderecoSeletor seletor) {
		ArrayList<Endereco> enderecos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet rs = null;

		String query = " SELECT end.* FROM endereco end ";

		if (seletor.temFiltro()) {
			query = preencherFiltros(seletor, query);
		}

		if (seletor.temPaginacao()) {
			query += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset();
		}

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Endereco end = preencherRs(rs);
				enderecos.add(end);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar enderecos com seletor");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return enderecos;
	}
	
	public int contarRegistros(EnderecoSeletor seletor) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        int totalRegistros = 0;
        ResultSet rs = null;
        String query = " SELECT count(*) FROM endereco end ";

        if(seletor.temFiltro()) {
            query = preencherFiltros(seletor, query);
        }

        try {
            rs = stmt.executeQuery(query);
            if(rs.next()){
                totalRegistros = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar registros de enderecos");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return totalRegistros;
    }

    public int contarPaginas(EnderecoSeletor seletor) {
        int totalPaginas = 0;
        int totalRegistros = this.contarRegistros(seletor);

        totalPaginas = totalRegistros / seletor.getLimite();
        int resto = totalRegistros % seletor.getLimite();

        if(resto > 0) {
            totalPaginas++;
        }

        return totalPaginas;
    }

	public Endereco preencherRs(ResultSet rs) throws SQLException {
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
	
		return end;
	}

	public String preencherFiltros(EnderecoSeletor seletor, String query) {
		query += " WHERE ";
		boolean primeiro = true;

		if (seletor.getNumero() > 0) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " end.numero =" + seletor.getNumero();
			primeiro = false;
		}
		if (seletor.getCep() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.cep) LIKE UPPER('" + seletor.getCep() + "%') ";
			primeiro = false;
		}
		if (seletor.getRua() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.rua) LIKE UPPER('" + seletor.getRua() + "%') ";
			primeiro = false;
		}
		if (seletor.getBairro() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.bairro) LIKE UPPER('" + seletor.getBairro() + "%') ";
			primeiro = false;
		}
		if (seletor.getCidade() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.cidade) LIKE UPPER('" + seletor.getCidade() + "%') ";
			primeiro = false;
		}
		if (seletor.getEstado() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.estado) LIKE UPPER('" + seletor.getEstado() + "%') ";
			primeiro = false;
		}
		if (seletor.getPais() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += " UPPER(end.pais) LIKE UPPER('" + seletor.getPais() + "%') ";
			primeiro = false;
		}
		return query;
	}

}
