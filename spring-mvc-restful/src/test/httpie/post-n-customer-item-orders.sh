#!/bin/bash

http POST localhost:8080/customers/1/orders/batch < customer-item-orders/n-johnsnow-orders.json
http POST localhost:8080/customers/6/orders/batch < customer-item-orders/n-aryastark-orders.json
http POST localhost:8080/customers/12/orders/batch < customer-item-orders/n-barristanselmy-orders.json

