#!/bin/bash

http POST localhost:8080/products/ < 1-product-ice.json
http POST localhost:8080/products/batch < n-products.json

