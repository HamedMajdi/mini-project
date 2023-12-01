package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class ParserInitializer {
    public Stack<Integer> initializeParsStack() {
        Stack<Integer> parsStack = new Stack<Integer>();
        parsStack.push(0);
        return parsStack;
    }

    public ParseTable initializeParseTable() {
        ParseTable parseTable = null;
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseTable;
    }

    public ArrayList<Rule> initializeRules() {
        ArrayList<Rule> rules = new ArrayList<Rule>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }
}