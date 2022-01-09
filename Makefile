PATH_TO_FX=/home/alanlopez/Documents/Programming/Java/javafx-sdk-17.0.1/lib

compile:
	javac --module-path $(PATH_TO_FX) --add-modules=javafx.controls -d ./classes --source-path ./src src/automatas/Demo.java

run:
	java --module-path $(PATH_TO_FX) --add-modules=javafx.controls -classpath classes automatas.Demo

.PHONY: clean
clean:
	rm -rf classes
	rm -rf dist
	rm -rf automatas.*
	rm -rf Automatas.*
