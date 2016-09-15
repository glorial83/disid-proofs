#!/bin/bash

http POST localhost:8080/categories/batch < n-categories.json

http POST localhost:8080/categories < 1-category-machine.json
