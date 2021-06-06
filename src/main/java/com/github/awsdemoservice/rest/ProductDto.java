package com.github.awsdemoservice.rest;

public class ProductDto {

    public ProductDto() {
    }

    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long id;
    public String name;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}