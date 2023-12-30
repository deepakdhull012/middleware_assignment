# Pre-requisite

* System should have java installed and setup ready for running spring boot appliation.
* Rabbit mq should be up and running on port 5672

# Running instructions

* Clone the code from the repo - 
* run `mvn clean install` for all the repos
* run `order, product, notification1, notification2`
* Product service has request controller - running at port 8200
* Grpc server will run on port 8000

# Create-Order contract

* Request url : `http://localhost:8200/products/create-order`
* Type : `POST`,
* Body: `
{
    "customerId": 1,
    "orderItems": [
        {
            "productId": 1,
            "qty": 2
        },
        {
            "productId": 2,
            "qty": 10
        }
    ]
}

# Update-Order contract

* Request url : `http://localhost:8200/products/update-order`
* Type : `POST`,
* Body: `
{
    "orderId": "orderId",
    "customerId": 1,
    "orderItems": [
        {
            "productId": 1,
            "qty": 2
        },
        {
            "productId": 2,
            "qty": 10
        }
    ]
}
`