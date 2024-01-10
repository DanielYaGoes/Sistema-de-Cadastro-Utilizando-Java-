package conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	private static EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("Desafio-04");
	
	public static EntityManager getConnection() {
		return EntityManagerFactory.createEntityManager();
	}
	
}
