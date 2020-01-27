# Software Engineering (Spring 2020)
## Assignment 1 - Refactoring Java Code

### Kripa Anne
### 20171159

## Steps:
1. Download this zip file.
2. Check if you have Java Version 11+ installed.
3. Install ant by `sudo apt-get install ant`
4. Navigate to the root folder.
5. Run `ant compile`.
6. Run `ant jar`.
7. Run `java -cp build/jar/args.jar com.cleancoder.args.ArgsMain`.
8. For running tests, run `java -cp "lib/junit-4.13.jar:lib/hamcrest-core-1.3.jar:build/jar/args.jar" ./test/com/cleancoder/args/ArgsTest.java testCreateWithNoSchemaOrArguments`.

## Code Refactoring Methods Used:
- Changed single letter variables like ‘m’, ‘am’ to more meaningful variable names.
- In *MapArgumentMarshaler*, divided a function into 2 parts to increase readability. This included creating a new function that is called within a for-loop of the original function.
- Made more meaningful function names like *setArgsInDataStructure* instead of *set* in each of the marshaler files as the data structure varies from map to string to boolean values.
- Within Args.java, changed function name *has* to *hasCharArg*. This makes all the tests that make use of this method much more legible.
- Functions now follow verb-noun naming convention.
- Comments added in appropriate places, such that they aren’t redundant but also, they explain the code when variable or function naming restricts the meaning conveyed.
- Changed incorrect function names to names that better convey function of the method.
- In Args.java, changed *parseArgumentCharacter* to *getMarshaler* and further divided it into *setIntoMarshaler* to separately take care of the condition where the marshaler is valid.
- In Args.java, a new function is formed from dividing *parseSchemaElement* to get *assignMarshaler*. Here, we compromise by passing 2 arguments to *assignMarshaler* over code redundancy by initializing *elementTail* and *elementId* again in the new method. 
- Removed extra braces that bulk up code for single statement ‘if’ and ‘for’ loops.
- Standardized the indentation throughout the entire code as per visual property.
- Replaced numbers and strings in the ArgsTest file with variables containing the values to centralize any future changes to the tests.

## Unit Tests:
- Corrected *malFormedMapArgument()*'s Test
- Added a test for negative numbers
- Added a test discovering a multiple flag function i.e. -xy 2 2.3

## Out of the Box Improvements:
* In method *assignMarshaler* in Args.java, converted the long if-else statement to a simple switch statement. While polymorphism is recommended over if-else or switch-case statements generally, as forming a separate class structure for the individual strings/elementTails (”&” etc.) only increases redundancy.  

