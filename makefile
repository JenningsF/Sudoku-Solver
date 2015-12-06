all: cFiles javaFiles
	
cFiles: sat0.c gb_flip.c gb_flip.h
	gcc sat10.c gb_flip.c -o SAT

javaFiles: ClauseGenerator7.java Solver.java
	javac ClauseGenerator7.java Solver.java

clean: 
	rm SAT ClauseGenerator7.class Solver.class