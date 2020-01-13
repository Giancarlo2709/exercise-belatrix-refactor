# exercise-belatrix-refactor
1.	If you were to review the following code, what feedback would you give? Please be specific and indicate any errors that would occur as well as other best practices and code refactoring that should be done. 
2.	Rewrite the code based on the feedback you provided in question 1. Please include unit tests on your code.

# answer
1. The code is very coupled, there is a lot of dependence, which makes it difficult to do the unit testing.
   NullPointerException errors are also observed. In the variable l that is declared as null and when doing message.trim ().

2. The refactoring of the code was done by applying SOLID principles, sole responsibility, Dependency inversion (which tells us that high-level modules must depend on abstractions); This was done to make better use of unit testing.
