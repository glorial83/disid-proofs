#!/bin/bash

http POST localhost:8080/api/products/ < 1-product-ice.json
http POST localhost:8080/api/products/batch < n-products.json

