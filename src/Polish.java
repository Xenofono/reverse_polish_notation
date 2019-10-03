import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * expressionAsList innehåller den färdiga postfix ekvationen, sum innehåller det beräknade värdet baserat på postfix
 * @author Kristoffer Näsström
 */
public class Polish {

    private final List<String> expressionAsList;
    private final double sum;
    private final String[] allVariables;

    //Regex som separerar siffror ifrån alla andra tecken
    private final String regex = "(?<=[-−+*×÷/()])|(?=[-−+*×/÷()])";

    public Polish(String string) {
        allVariables = string.split(regex);
        expressionAsList = new ArrayList<>();
        convert();
        this.sum = calculateExpression();
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

    private double calculateExpression() {
        Stack<Double> stack = new Stack<>();

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

    public double getSum() {
        return sum;
    }
}
