package Dao;

import java.util.List;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import codigoprincipal.Contato;
import codigoprincipal.Pessoa;
import conection.ConnectionFactory;

public abstract class RepositoryDAO<T> {

	public static Pessoa findPessoaById(Integer id) {
		EntityManager em = ConnectionFactory.getConnection();
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Pessoa.class);
		Criterion restricao = Restrictions.eq("id", id);

		criteria.add(restricao);

		return (Pessoa) criteria.uniqueResult();
	}

	public static List<Pessoa> findAllPessoa() {
		EntityManager em = ConnectionFactory.getConnection();
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pessoa.class, "bean");

		ProjectionList projectionList = Projections.projectionList();

		projectionList.add(Projections.property("bean.id").as("id"));
		projectionList.add(Projections.property("bean.nome").as("nome"));
		projectionList.add(Projections.property("bean.cpf").as("cpf"));
		projectionList.add(Projections.property("bean.email").as("email"));
		projectionList.add(Projections.property("bean.telefone").as("telefone"));
		projectionList.add(Projections.property("bean.endereco").as("endereco"));

		criteria.setProjection(projectionList);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Pessoa.class));

		return criteria.list();
	}

	public static List<Contato> retornaContatosDaPessoa(Pessoa pessoa) {
		EntityManager em = ConnectionFactory.getConnection();
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Contato.class,"bean");
		
		ProjectionList projectionList = Projections.projectionList();

		projectionList.add(Projections.property("bean.id").as("id"));
		projectionList.add(Projections.property("bean.nome").as("nome"));
		projectionList.add(Projections.property("bean.email").as("email"));
		projectionList.add(Projections.property("bean.telefone").as("telefone"));
		projectionList.add(Projections.property("bean.pessoa").as("pessoa"));

		Criterion criterion = Restrictions.eq("bean.pessoa", pessoa);
		
		criteria.add(criterion);
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Contato.class));
		
		return (List<Contato>) criteria.list();

	}

	public static Contato findContatoById(Integer id) {
		EntityManager em = ConnectionFactory.getConnection();
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Contato.class);
		
		Criterion restricao = Restrictions.eq("id", id);
		
		criteria.add(restricao);
		
		return (Contato) criteria.uniqueResult();
	}

	public static <T> T save(T t) {
		EntityManager em = ConnectionFactory.getConnection();

		try {
			em.getTransaction().begin();
			if (t == null) {
				em.persist(t);
			} else {
				em.merge(t);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return t;

	}

	public static <T> void delete(T t,Integer id) {
		EntityManager em = ConnectionFactory.getConnection();
		try {
			em.getTransaction().begin();
			
			t = (T) em.find(t.getClass(), id);
					
			if(t != null) {
				
				em.remove(t);
				
				System.out.println("Exclusão feita");
			}else {
				System.out.println("Não encontrado");
			}
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

	}

}
