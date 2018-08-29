package com.example.hibernate.transactionmanager;

import javax.persistence.EntityManager;

/**
 * Functional Interface that represent transaction, will be used in Execute around to pass tranasactional code.
 * 
 * @author amit
 *
 */

@FunctionalInterface
public interface Transaction<T> {

	T execute(EntityManager entityManager) throws Exception;
	
}
