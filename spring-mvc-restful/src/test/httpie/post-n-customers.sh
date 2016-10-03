#!/bin/bash

http POST localhost:8080/customers/ < 1-customer-johnsnow.json
http POST localhost:8080/customers/batch < n-customers.json

