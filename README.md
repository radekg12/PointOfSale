# PointOfSale

Implement a simple point of sale.


### Assume you have:
* one input device: bar codes scanner
* two output devices: LCD display and printer


### Implement:
* single product sale: products bar code is scanned and:
* if the product is found in products database then it's name and price is printed on LCD display
* if the product is not found then error message 'Product not found' is printed on LCD display
* if the code scanned is empty then error message 'Invalid bar-code' is printed on LCD display
* when 'exit' is input then receipt is printed on printer containing a list of all previously scanned items names and prices as well as total sum to be paid for all items; the total sum is also printed on LCD display


### Rules:
* use only SDK classes and your favorite test libraries
* mock/stub the database and IO devices
* concentrate on proper design and clean code, rather than supplying fully functioning application
