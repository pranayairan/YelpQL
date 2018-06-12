# YelpQL
An android app that consumes Yelp GraphQL api with apollo android client. Read my blog post about how we can use apollo to consume yelps api and make an app 
https://medium.com/@pranayairan/yelpql-learn-graphql-by-building-yelp-app-da2a71f16c77

This is the current stack
 - Apollo codegen, apollo run time and all plugins updated to 0.5.0
 - Yelp schema is updated to latest till Jume 12 2018
 - Java 8 support added
 
# Demo
![YelpQL Yelps android app using GraphQL](https://media.giphy.com/media/l41K5wS5RXQXRlfoI/giphy.gif)

# Schema
You can download the latest yelp schema by using this command : 

apollo-codegen download-schema https://api.yelp.com/v3/graphql --output schema.json --header "Authorization: Bearer nHwOICuMpcv4zoj4kFkkzlarDPQf0vxgILtYOrjRft6eUgCE8DtzZuQ4oxOQqLyoi1n_qK0Hpp0V5yDI2cHVzC9PjGEfaY2zBrRTJD6SMZ45e9POnWrrm2pTmjBhWXYx"

Make sure you have apollo code gen installed, here are more instructions 
http://dev.apollodata.com/ios/downloading-schema.html
https://github.com/apollographql/apollo-codegen
