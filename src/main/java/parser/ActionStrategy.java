package parser;

import scanner.token.Token;

public interface ActionStrategy {
    void executeAction(Action currentAction, Token lookAhead);
}