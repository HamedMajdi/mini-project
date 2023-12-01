package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import Log.Log;
import codeGenerator.CodeGenerator;
import errorHandler.ErrorHandler;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private lexicalAnalyzer lexicalAnalyzer;
    private CodeGenerator cg;
    private ParserInitializer initializer;

    public Parser() {
        initializer = new ParserInitializer();
        parsStack = initializer.initializeParsStack();
        parseTable = initializer.initializeParseTable();
        rules = initializer.initializeRules();
        cg = new CodeGenerator();
    }

//    public Parser() {
//        parsStack = new Stack<Integer>();
//        parsStack.push(0);
//        try {
//            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        rules = new ArrayList<Rule>();
//        try {
//            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
//                rules.add(new Rule(stringRule));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        cg = new CodeGenerator();
//    }

    public void startParse(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
        Token lookAhead = lexicalAnalyzer.getNextToken();
        boolean finish = false;
        Action currentAction;
        while (!finish) {
            try {
                currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
                ActionStrategy strategy = ActionStrategyFactory.getStrategy(currentAction.action);
                strategy.executeAction(currentAction, lookAhead);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        if (!ErrorHandler.hasError) cg.printMemory();
    }
}

//    public void startParse(java.util.Scanner sc) {
//        lexicalAnalyzer = new lexicalAnalyzer(sc);
//        Token lookAhead = lexicalAnalyzer.getNextToken();
//        boolean finish = false;
//        Action currentAction;
//        while (!finish) {
//            try {
//                Log.print(/*"lookahead : "+*/ lookAhead.toString() + "\t" + parsStack.peek());
////                Log.print("state : "+ parsStack.peek());
//                currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
//                Log.print(currentAction.toString());
//                //Log.print("");
//
//                switch (currentAction.action) {
//                    case shift:
//                        parsStack.push(currentAction.number);
//                        lookAhead = lexicalAnalyzer.getNextToken();
//
//                        break;
//                    case reduce:
//                        Rule rule = rules.get(currentAction.number);
//                        for (int i = 0; i < rule.RHS.size(); i++) {
//                            parsStack.pop();
//                        }
//
//                        Log.print(/*"state : " +*/ parsStack.peek() + "\t" + rule.LHS);
////                        Log.print("LHS : "+rule.LHS);
//                        parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
//                        Log.print(/*"new State : " + */parsStack.peek() + "");
////                        Log.print("");
//                        try {
//                            cg.semanticFunction(rule.semanticAction, lookAhead);
//                        } catch (Exception e) {
//                            Log.print("Code Genetator Error");
//                        }
//                        break;
//                    case accept:
//                        finish = true;
//                        break;
//                }
//                Log.print("");
//            } catch (Exception ignored) {
//                ignored.printStackTrace();
//
//            }
//        }
//        if (!ErrorHandler.hasError) cg.printMemory();
//    }

