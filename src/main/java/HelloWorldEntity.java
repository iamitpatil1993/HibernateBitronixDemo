import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "hello_world_entity")
public class HelloWorldEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic
	@GeneratedValue(generator = "hello_world_pk_gen")
	@GenericGenerator(name = "hello_world_pk_gen", strategy = "uuid2")
	private UUID id;

}
