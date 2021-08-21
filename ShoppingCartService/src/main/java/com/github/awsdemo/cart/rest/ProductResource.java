package com.github.awsdemo.cart.rest;

import com.github.awsdemo.cart.dao.Product;
import com.github.awsdemo.cart.dao.ProductService;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger LOG = Logger.getLogger(ProductResource.class);

    @Inject
    ProductService productService;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        LOG.info("GET: " + id);
        try {
            final Product product = productService.getById(id);
            return Response.ok(product).build();
        } catch (NoResultException exception) {
            LOG.error(exception);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(Product product) {
        LOG.info("POST: " + product.toString());
        Product persistedProduct = productService.persist(product);
        return Response.ok(persistedProduct).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.info("DELETE: " + id);
        int deletedRows = productService.delete(id);
        if (deletedRows == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }
}