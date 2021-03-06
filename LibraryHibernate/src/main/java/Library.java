import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.seavus.dataaccess.MembershipPostgreHibernateRepository;
import com.seavus.dataaccess.PublicationPostgreHibernateRepository;
import com.seavus.domain.Book;
import com.seavus.domain.Loan;
import com.seavus.domain.Magazine;
import com.seavus.domain.Member;
import com.seavus.domain.Membership;
import com.seavus.presentation.LibraryController;
import com.seavus.service.LibraryService;

public class Library {

	public static void main(String[] args) throws IOException {
		Configuration configuration = new Configuration();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.addAnnotatedClass(Book.class).addAnnotatedClass(Magazine.class)
				.addAnnotatedClass(Membership.class).addAnnotatedClass(Member.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory(serviceRegistry);

		LibraryController libraryController = new LibraryController(
				new LibraryService(new PublicationPostgreHibernateRepository(sessionFactory), new MembershipPostgreHibernateRepository(sessionFactory)));
		libraryController.run();

		sessionFactory.close();
	}

}
