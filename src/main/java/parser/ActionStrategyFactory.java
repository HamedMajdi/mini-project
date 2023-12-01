package parser;

public class ActionStrategyFactory {
    public static ActionStrategy getStrategy(act actionType) {
        switch (actionType) {
            case shift:
                return new ShiftActionStrategy();
            case reduce:
                return new ReduceActionStrategy();
            case accept:
                return new AcceptActionStrategy();
            default:
                throw new IllegalArgumentException("Invalid action type");
        }
    }
}