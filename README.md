# README
To build use:
`mvn package`

To run use:
`./run`

This will give you a barebones REPL, where you can enter text and you will be output at most 5 suggestions sorted alphabetically.

To start the server use:
`./run --gui [--port=<port>]`

Project details:
Onboarding project
Naive neighbors algorithm implementation
Total estimated hours: 10-15

Design Choices:
I created a class, stars.java to hold the attributes of a star presented by the data in the csv files. Ideally, the objects would be loaded into an array and the naive neighbors command would be called on that list to loop through and find the nearest one.

I also implemented a comparable interface that allows to compare objects to one another.

Error/Bugs:
Naive neighbors command doesnt work in the repl.

Credit for code
Source : "KNN Algorithm in java"
Link: https://www.youtube.com/watch?v=8kaYD2g9MVQ
