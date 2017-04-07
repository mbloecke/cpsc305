
run: compile
	java MyAssembler $(asmFile)

compile: MyAssembler.java
	javac MyAssembler.java
