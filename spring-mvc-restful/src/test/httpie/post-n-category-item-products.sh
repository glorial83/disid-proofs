#!/bin/bash

http POST localhost:8080/api/categories/1/products/batch < category-item-products/n-weapon-products.json
http POST localhost:8080/api/categories/2/products/batch < category-item-products/n-protection-products.json
http POST localhost:8080/api/categories/3/products/batch < category-item-products/n-creature-products.json
http POST localhost:8080/api/categories/3/products < category-item-products/n-creature-1-product.json
http POST localhost:8080/api/categories/4/products/batch < category-item-products/n-machine-products.json

