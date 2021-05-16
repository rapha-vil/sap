package br.gov.ba.funceb.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import br.gov.ba.funceb.framework.implementacao.crud.VariavelConexaoUtil;

/**
 * Responsavel por estabelecer a conexão com o hibernate
 * @author Rafaela Vilela
 *
 */

@ApplicationScoped

public class HibernateUtil implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE ="java:/comp/env/jdbc/datasource";
	
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		
		try {
			
			if (sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
				
			}
			
			return sessionFactory;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar conexão SessionFactory");
		}
		
			
	}
	
	/**
	 * Retorna o SessionFactory corrente
	 * @return
	 */
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Retorna a sessão do SessionFactory
	 * @return
	 */
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	/**
	 * Abre uma nova sessão no SessionFactory
	 * return Session
	 */
	
	public static Session openSession() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		
		return sessionFactory.openSession();
	}
	/**
	 * Obtém a conection do provedor de conexões configurado
	 * @return
	 * @throws SQLException
	 */
	
	public static Connection getConnectionProvider () throws SQLException {
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	
	/**
	 * 
	 * @return Connection no InitialContext java://comp/env/jdbc/datasource
	 * @throws Exception
	 */

	public static Connection getConnection() throws Exception {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		
		return ds.getConnection();
	}
	
	/**
	 * 
	 * @return DataSource JNDI Tomcat 
	 * @throws NamingException
	 */
	
	
	public DataSource getDataSourceJndi () throws NamingException{
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		
	}
	
}
