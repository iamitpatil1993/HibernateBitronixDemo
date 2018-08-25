import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.hibernate.BaseTest;

public class HelloWorldEntityTest extends BaseTest {

	@Test
	public void test() {
		
		HelloWorldEntity entity = new HelloWorldEntity();
		em.persist(entity);
		assertTrue(em.contains(entity));;
	}

}
