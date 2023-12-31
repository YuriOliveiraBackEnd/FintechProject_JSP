package br.com.FintechProject.factory;

import br.com.FintechProject.dao.CartaoDAO;
import br.com.FintechProject.dao.GastoDAO;
import br.com.FintechProject.dao.InvestimentoDAO;
import br.com.FintechProject.dao.RendimentoDAO;
import br.com.FintechProject.dao.UsuarioDAO;
import br.com.FintechProject.dao.impl.OracleCartaoDAO;
import br.com.FintechProject.dao.impl.OracleGastoDAO;
import br.com.FintechProject.dao.impl.OracleRendimentoDAO;
import br.com.FintechProject.dao.impl.OracleInvestimentoDAO;
import br.com.FintechProject.dao.impl.OracleUsuarioDAO;

public class DAOFactory {
  public static GastoDAO getGastoDAO()
  {
	  return new OracleGastoDAO();
  }
  public static UsuarioDAO getUsuarioDAO()
  {
	  return new OracleUsuarioDAO();
  }
  public static CartaoDAO getCartaoDAO()
  {
	  return new OracleCartaoDAO();
  }
  public static RendimentoDAO getRendimentoDAO()
  {
	  return new OracleRendimentoDAO();
  }
  public static InvestimentoDAO  getInvestimentoDAO()
  {
	  return new OracleInvestimentoDAO();
  }
}
