#!/bin/bash

http POST localhost:8080/api/categories/batch < n-categories.json

http POST localhost:8080/api/categories < 1-category-machine.json
