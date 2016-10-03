#!/bin/bash

http POST localhost:8080/customerorders/ < 1-customerorder.json
http POST localhost:8080/customerorders/batch < n-customerorders.json

