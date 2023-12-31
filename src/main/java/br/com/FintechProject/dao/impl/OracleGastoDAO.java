package br.com.FintechProject.dao.impl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.FintechProject.singleton.ConexaoBanco;
import br.com.FintechProject.bean.ModelGasto;
import br.com.FintechProject.bean.ModelUsuario;
import br.com.FintechProject.dao.GastoDAO;
import br.com.FintechProject.exception.DBException;


public class OracleGastoDAO implements GastoDAO{
private Connection conexao;
	
	

@Override
	public void cadastrar(ModelGasto gasto) {
		PreparedStatement ps = null;
		
		
		try {
			conexao = ConexaoBanco.getInstance().abrirConexao();
			
			String sql = "INSERT INTO GASTO(id_gasto,nr_parcelas, dt_gasto,valor,descricao,tx_titulo,tipo, id_cartao, id_usuario) "
					+ "VALUES (SQ_Gasto.nextval, ?, ?, ?, ?,?,?,?,?)";
			
			 ps = conexao.prepareStatement(sql);
			
			
			ps.setInt(1, gasto.getNr_parcelas());
			
			Date data = Date.valueOf(gasto.getDt_gasto());
			ps.setDate(2, data);
			
			ps.setDouble(3, gasto.getValor());
			
			ps.setString(4, gasto.getDescricao());
			
			ps.setString(5, gasto.getTx_titulo());
			
			ps.setString(6, gasto.getTipo());
			
			ps.setInt(7, gasto.getId_cartao());
			
			ps.setInt(8, gasto.getId_usuario());
			
		
			ps.executeUpdate();
	
			System.out.println("cadastrado");
			
		} catch (SQLException e) {
			System.out.println("Erro ao abrir!!!");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conexao.close();
			}
				catch (SQLException e) {
					System.out.println("Erro ao fechar!!!");
					e.printStackTrace();
			}
		}
	
		
	
	}
	@Override
	public List<ModelGasto> listarTodos(int id_usuariologado){
		//cria uma lista de gastos
	    List<ModelGasto> lista = new ArrayList<ModelGasto>();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	conexao = ConexaoBanco.getInstance().abrirConexao();
	    stmt = conexao.prepareStatement("SELECT * FROM GASTO WHERE id_usuario = ? ORDER BY ID_GASTO");
	    stmt.setInt(1, id_usuariologado);
	    rs = stmt.executeQuery();
	  
	    //Percorre todos os registros encontrados
	    while (rs.next()) {
        int id_gasto = rs.getInt("ID_GASTO");
	    int nr_parcelas = rs.getInt("NR_PARCELAS");
	    LocalDate dt_gasto = rs.getDate("DT_GASTO").toLocalDate();
	    double valor = rs.getDouble("VALOR");
	    String descricao = rs.getString("DESCRICAO");
	    String tx_titulo = rs.getString("TX_TITULO");
	    String tipo = rs.getString("TIPO");
	    int id_cartao = rs.getInt("ID_CARTAO");
	    int id_usuario = rs.getInt("ID_USUARIO");
	  
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataformatada = dt_gasto.format(formatter);
	        //Cria um objeto Colaborador com as informações encontradas
	        ModelGasto gasto = new ModelGasto(id_gasto,nr_parcelas, dataformatada,valor,descricao,tx_titulo,tipo,id_cartao,id_usuario);
	        //Adiciona o colaborador na lista
	        lista.add(gasto);
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }finally {
	      try {
	        stmt.close();
	        rs.close();
	        conexao.close();
	      } catch (SQLException e) {
	        e.printStackTrace();
	      }
	    }
	    return lista;
	  }
	@Override
	public void atualizar(ModelGasto gasto) throws DBException {
	
			PreparedStatement ps = null;

			try {
				conexao = ConexaoBanco.getInstance().abrirConexao();
				String sql = "UPDATE GASTO SET nr_parcelas = ?, dt_gasto = ?, valor = ?, descricao = ? , tx_titulo = ?, tipo = ?, id_cartao = ?, id_usuario = ? WHERE id_gasto = ?";
				
				 ps = conexao.prepareStatement(sql);
				
				ps.setInt(1, gasto.getNr_parcelas());
				
				Date data = Date.valueOf(gasto.getDt_gasto());
				ps.setDate(2, data);
				
				ps.setDouble(3, gasto.getValor());
				
				ps.setString(4, gasto.getDescricao());
				
				ps.setString(5, gasto.getTx_titulo());
				
				ps.setString(6, gasto.getTipo());
				
				ps.setInt(7, gasto.getId_cartao());
				
				ps.setInt(8, gasto.getId_usuario());
			
				ps.setInt(9, gasto.getId_gasto());

				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("Erro ao atualizar.");
			} finally {
				try {
					ps.close();
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		
	
	@Override
	public void remover(int id) throws DBException {
		
				PreparedStatement ps = null;

				try {
					conexao = ConexaoBanco.getInstance().abrirConexao();
					String sql = "DELETE FROM GASTO WHERE ID_GASTO = ?";
					ps = conexao.prepareStatement(sql);
					ps.setInt(1, id);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DBException("Erro ao remover.");
				} finally {
					try {
						ps.close();
						conexao.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		
	
	@Override
	public ModelGasto buscar(int id) {
		ModelGasto gasto = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conexao = ConexaoBanco.getInstance().abrirConexao();
			stmt = conexao.prepareStatement("SELECT * FROM GASTO WHERE ID_GASTO = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()){
				    int id_gasto = rs.getInt("id_gasto");
				    int nr_parcelas = rs.getInt("NR_PARCELAS");
				    LocalDate dt_gasto = rs.getDate("DT_GASTO").toLocalDate();
				    double valor = rs.getDouble("VALOR");
				    String descricao = rs.getString("DESCRICAO");
				    String tx_titulo = rs.getString("TX_TITULO");
				    String tipo = rs.getString("TIPO");
				    int id_cartao = rs.getInt("ID_CARTAO");
				    int id_usuario = rs.getInt("ID_USUARIO");
				  
//				    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//			        String dataformatada = dt_gasto.format(formatter);
				
				gasto = new ModelGasto(id_gasto,nr_parcelas, dt_gasto,valor,descricao,tx_titulo,tipo,id_usuario,id_cartao);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gasto;
	}
	@Override
	public Integer buscarId(String userEmail) {
        int id_usuario = 0;
		ModelUsuario usuario = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conexao = ConexaoBanco.getInstance().abrirConexao();
			ps = conexao.prepareStatement("SELECT ID_USUARIO FROM USUARIO WHERE EMAIL_USUARIO = ?");
			ps.setString(1, userEmail);
			rs = ps.executeQuery();

			if (rs.next()){
				id_usuario = rs.getInt("ID_USUARIO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id_usuario;
	}


	@Override
	public Integer buscarCartao(String nr_cartao) {
		    int id_cartao = 0;
			ModelUsuario usuario = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conexao = ConexaoBanco.getInstance().abrirConexao();
				ps = conexao.prepareStatement("SELECT ID_CARTAO FROM CARTAO WHERE NR_CARTAO = ?");
				ps.setString(1, nr_cartao);
				rs = ps.executeQuery();

				if (rs.next()){
					id_cartao = rs.getInt("ID_CARTAO");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					ps.close();
					rs.close();
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return id_cartao;
		}

	
}
