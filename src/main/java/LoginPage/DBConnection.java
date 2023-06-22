package LoginPage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBConnection {

    public void save(User user) {
        Configuration con = new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(User.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();


        session.save(user);


        tx.commit();
        session.close();
        sf.close();
    }

    public User fetch(String fieldName, String value){
        Configuration con = new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(User.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session= sf.openSession();
        Transaction tx = session.beginTransaction();

        String hqlQuery="SELECT u FROM User u WHERE u."+fieldName+"='"+value+"'";

        User user= null;
        try {
            user = (User) session.createQuery(hqlQuery).getSingleResult();
        } catch (NoResultException e ) {
            user=null;
        }


        tx.commit();
        session.close();
        sf.close();



        return user;
    }

    public boolean fieldControl(String fieldName, String value) {
        Configuration con = new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(User.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

//        String hqlQuery= "Select (count(u)>0) From User u Where u."+fieldName+"='"+value+"'";
//        boolean isExist= (boolean) session.createQuery(hqlQuery).getSingleResult();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Boolean> criteriaQuery = cb.createQuery(Boolean.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(cb.literal(true)).where(cb.equal(root.get(fieldName), value));
        boolean isExist= session.createQuery(criteriaQuery).setMaxResults(1).getResultList().stream().findFirst().orElse(false);


        tx.commit();
        session.close();
        sf.close();
        return isExist;

    }









}