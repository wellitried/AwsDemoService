package com.github.awsdemoservice.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM product WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM product WHERE id = ?";

    @Inject
    EntityManager entityManager;

    @Transactional
    public Product getById(final Long id) {
        return (Product) entityManager
                .createNativeQuery(SELECT_BY_ID_QUERY, Product.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    @Transactional
    public int delete(final Long id) {
        return entityManager
                .createNativeQuery(DELETE_BY_ID_QUERY)
                .setParameter(1, id)
                .executeUpdate();
    }

    @Transactional
    public Product persist(final Product product) {
        entityManager.persist(product);
        return product;
    }
}
