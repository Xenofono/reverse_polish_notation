import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Kristoffer Näsström
 */
public class Polish {

    private Stack<Double> stack;
    private List<String> expressionAsList;
    private String[] allVariables;

    public Polish(String string) {
        allVariables = string.split("(?<=[-−+*×÷/()])|(?=[-−+*×/÷()])");
        expressionAsList = new ArrayList<>();
        convert();
    }

    private void convert() {

        Stack<String> operator = new Stack<>();

        for (int i = 0; i < allVariables.length; i++) {

            if (parseNumbers(allVariables[i])) {
                expressionAsList.add(allVariables[i]);
            } else if (allVariables[i].equals(")")) {

                String popped = operator.pop();
                do {
                    expressionAsList.add(popped);
                    popped = operator.pop();
                } while (!popped.equals("("));

            } else {
                operator.push(allVariables[i]);
            }
        }

        while (!operator.empty()) {
            expressionAsList.add(operator.pop());
        }

    }

    public double calculateExpression() {
        this.stack = new Stack<>();

        for (String entry : expressionAsList) {
            if (parseNumbers(entry)) {
                stack.push(Double.parseDouble(entry));
            } else {
                double first = stack.pop();
                double second = stack.pop();
                double newValue = performOperation(second, entry, first);
                stack.push(newValue);
            }
        }
        return stack.pop();
    }

    private double performOperation(double first, String operator, double second) {

        switch (operator) {
            case "*":
            case "×":
                return first * second;
            case "+":
                return first + second;
            case "-":
            case "−":
                return first - second;
            case "/":
            case "÷":
                return first / second;
            default:
                throw new NumberFormatException("Nä nu får du fixa");
        }

    }

    
    private boolean parseNumbers(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<String> getExpressionAsList() {
        return expressionAsList;
    }
}
