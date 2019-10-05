import java.util.*;

/**
 * postfixExpressionAsList innehåller den färdiga postfix ekvationen, sum innehåller det beräknade värdet baserat på postfix
 *
 * @author Kristoffer Näsström
 */
public class Polish {

    private final List<String> postfixExpressionAsList;
    private final String postfixExpressionString;
    private final double sum;
    private final String[] allVariables;


    public Polish(String input) {
        this.allVariables = parseAndCleanString(input);
        this.postfixExpressionAsList = stringToPostfix();
        this.postfixExpressionString = String.join(" ", postfixExpressionAsList);
        this.sum = calculateExpression();
    }

    private String[] parseAndCleanString(String input) {

        //Regex som separerar siffror ifrån alla andra tecken, har flera varianter av t ex minus
        //vänstra delen matchar allt som kommer efter ett av de tecknena, andra delen delen gör motsatsen
        final String regex = "(?<=[-−–+*×÷/()])|(?=[-−–+*×/÷()])";

        return Arrays.stream(input.split(regex))
                .map(String::strip)
                .filter(str -> !str.isBlank())
                .toArray(String[]::new);
    }


    private List<String> stringToPostfix() {

        Stack<String> operators = new Stack<>();
        List<String> returnList = new ArrayList<>();

        //Nummer läggs i listan, operators läggs på stack. När vi når en ")"  så poppar vi stacken  tills "(" nås
        for (String entry : allVariables) {

            if (isANumber(entry)) {
                returnList.add(entry);
            } else if (entry.equals(")")) {

                String popped = operators.pop();
                do {
                    returnList.add(popped);
                    popped = operators.pop();

                } while (!popped.equals("("));

            } else {
                operators.push(entry);
            }
        }
        //När vi har gått igenom alla variabler och det fortfarande finns operatorer så lägger vi alla dessa på slutet
        while (!operators.empty()) {
            returnList.add(operators.pop());
        }
        return returnList;

    }

    private double calculateExpression() {
        //1 1 + 2 * 1 +
        Stack<Double> stack = new Stack<>();
        for (String entry : postfixExpressionAsList) {
            if (isANumber(entry)) {
                stack.push(Double.parseDouble(entry));
            } else {
                double lastInStack = stack.pop();
                double secondLastInStack = stack.pop();

                double newValue = performOperation(secondLastInStack, entry, lastInStack);
                stack.push(newValue);
            }
        }
        return stack.pop();
    }

    private double performOperation(double value1, String operator, double value2) {

        switch (operator) {
            case "*":
            case "×":
                return value1 * value2;
            case "+":
                return value1 + value2;
            case "-":
            case "−":
            case "–":
                return value1 - value2;
            case "/":
            case "÷":
                return value1 / value2;
            default:
                throw new NumberFormatException("Nä nu får du fixa dina input");
        }

    }


    private boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<String> getPostfixExpressionAsList() {
        return this.postfixExpressionAsList;
    }

    public double getSum() {
        return this.sum;
    }

    public String getPostfixExpressionString(){
        return this.postfixExpressionString;
    }

    @Override
    public String toString(){
        return "pre-converted variables: " + Arrays.toString(this.allVariables) +
                "\nconverted string as List: " + this.postfixExpressionAsList +
                "\nconverted string as String " + this.postfixExpressionString +
                "\nfinal calculation is: " + this.sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polish polish = (Polish) o;
        return Double.compare(polish.sum, sum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum);
    }
}
