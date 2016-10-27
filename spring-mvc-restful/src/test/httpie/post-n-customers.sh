#!/bin/bash

http POST localhost:8080/api/customers/ < 1-customer-johnsnow.json
http POST localhost:8080/api/customers/batch < n-customers.json

