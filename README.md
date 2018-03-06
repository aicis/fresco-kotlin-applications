# FRESCO domain specific language

This repository utilizes the Kotlin language to overload and create new
operators which makes it possible to create a domain-specific language for
FRESCO applications. This makes MPC look like ordinary math.

Kotlin compiles to various different languages, and is compatible with
JVM. There is seamless integration between Java code and Kotlin code, making the
switch to programming in Kotlin very simple.

Note that this code is a prototype and for now not maintained. Use at your own
risk.

## Get started

Run 'mvn install'. This fetches FRESCO version 1.0.0 and Kotlin version
1.2.21. If you would like to use different versions, just change it accordingly
in the pom.xml file.

That's it. Go check out the test files to see how to construct a FRESCO Kotlin
application. You're much welcome to expand the amount of operators available. 