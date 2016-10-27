#!/bin/bash

http POST localhost:8080/api/customerorders/ < 1-customerorder.json
http POST localhost:8080/api/customerorders/batch < n-customerorders.json

