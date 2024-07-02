package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Aluguel;
import model.entity.Anfitriao;
import model.entity.Endereco;
import model.entity.EnderecoSeletor;


public class EnderecoRepository implements BaseRepository<Endereco> {

	@Override
	public Endereco salvar(Endereco endereco) {
		Connection conn = Banco.getConnection();
		String query = " INSERT INTO endereco(numero, cep, rua, bairro, cidade, estado, pais, id_anfitriao)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherPstmt(endereco, pstmt);

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
		String query = " UPDATE endereco SET numero=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, pais=?, id_anfitriao=?"
				+ " WHERE id=? ";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		boolean alterado = false;
		try {
			preencherPstmt(endereco, pstmt);

			pstmt.setInt(9, endereco.getId());
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
				end = preencherRs(rs);
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
				Endereco end = preencherRs(rs);

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
	
	public ArrayList<Endereco> consultarPorAnfitriao(int idAnfitriao) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        String query = " SELECT * FROM endereco WHERE id_anfitriao=" + idAnfitriao + " ";
        ResultSet rs = null;
        ArrayList<Endereco> enderecos = new ArrayList<>();

        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Endereco e = preencherRs(rs);
                enderecos.add(e);
            }
        } catch(SQLException e){
            System.out.println("Erro ao consultar enderecos por anfitriao");
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(stmt);
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
		AnfitriaoRepository anfRepo = new AnfitriaoRepository();

		end = new Endereco();
		end.setId(rs.getInt("id"));
		end.setNumero(rs.getInt("numero"));
		end.setCep(rs.getString("cep"));
		end.setRua(rs.getString("rua"));
		end.setBairro(rs.getString("bairro"));
		end.setCidade(rs.getString("cidade"));
		end.setEstado(rs.getString("estado"));
		end.setPais(rs.getString("pais"));
		Anfitriao anf = anfRepo.consultarPorId(rs.getInt("id_anfitriao"));
		end.setAnfitriao(anf);

		return end;
	}
	
	public void preencherPstmt(Endereco end, PreparedStatement pstmt) throws SQLException {
		pstmt.setInt(1, end.getNumero());
		pstmt.setString(2, end.getCep());
		pstmt.setString(3, end.getRua());
		pstmt.setString(4, end.getBairro());
		pstmt.setString(5, end.getCidade());
		pstmt.setString(6, end.getEstado());
		pstmt.setString(7, end.getPais());
		pstmt.setInt(8, end.getAnfitriao().getId());
	}

	public String preencherFiltros(EnderecoSeletor seletor, String query) {
		query += " WHERE id_anfitriao = " + seletor.getIdAnfitriao();
		boolean primeiro = false;

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
