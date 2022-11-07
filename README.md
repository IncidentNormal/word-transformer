## Word Transformer

### Overview

Java program that prints the transformation steps required to convert one word to another word.

### Usage

The application accepts a single command line argument - which is a filename. This filename must be present in the same folder as where the program is executed. The file should be a text file of the following structure:

Line 1: start_word, end_word

Line 2: Comma separated list of all the valid words

### Example

```
# cd to root of repo
cd word-transformer

# build the jar
mvn clean install

# run the jar from command line, notice that the final argument is the filename
java -jar target/word-transformer-0.0.1-SNAPSHOT.jar com.duncantait.wordtransformer.WordTransformerApplication 1.input
```