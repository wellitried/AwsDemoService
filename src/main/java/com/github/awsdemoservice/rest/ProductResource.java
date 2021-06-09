package com.github.awsdemoservice.rest;

import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.TreeMap;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger LOG = Logger.getLogger(ProductResource.class);

    private final TreeMap<Long, ProductDto> products = new TreeMap<>();

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        ProductDto productDto = products.get(id);
        LOG.info("GET " + id + ": " + productDto.toString());
        return Response.ok(productDto).build();
    }

    @POST
    public Response create(ProductDto productDto) {
        long maxId = products.isEmpty() ? 0 : products.lastKey();
        productDto.id = ++maxId;
        LOG.info("POST: " + productDto.toString());
        ProductDto savedProductDto = products.put(productDto.id, productDto);
        return Response.ok(savedProductDto).build();
    }
}