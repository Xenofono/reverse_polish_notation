import java.util.*;

/**
 * Den här klassen tar ett matematiskt uttryck skrivit i infix med korrekt placerade paranteser och omvandlar det till ett
 * postfixuttryck, också kallat omvänd polsk notation (reverse polish notation). Slutligen så beräknas och lagras resultatet
 * av det konverterade uttrycket som en double.
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

    /**
     * Separerar siffror och operatorer, tar bort whitespace och tar bort eventuella blanka element
     * innan kvarstående element samlas i en array som returneras direkt.
     * @param input är ett matematiskt uttryck skrivet i infix, det vanliga sättet med parenteser.
     * @return strängarray som städats och filtrerats.
     */
    private String[] parseAndCleanString(String input) {

        //Regex som separerar siffror ifrån alla andra tecken, har flera varianter av t ex minus
        //vänstra delen matchar allt som kommer efter ett av de tecknena, andra delen delen gör motsatsen
        final String regex = "(?<=[-−–+*×÷/()])|(?=[-−–+*×/÷()])";

        return Arrays.stream(input.split(regex))
                .map(String::strip)
                .filter(str -> !str.isBlank())
                .toArray(String[]::new);
    }


    /**
     * Metoden tar den städade strängen och omvandlar den till postfix, nummer hamnar direkt i listan medan operatorer
     * läggs i en stack som poppas till listan när loopen når en stängningsparentes.
     * @return Lista som representerar det inmatade infixuttrycket i korrekt följd enligt postfix
     */
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

    /**
     * Går igenom hela uttrycket och utför beräkningen i en loop tills endast svaret kvarstår i stacken.
     * @return slutliga beräkningen av hela postfixuttrycket som en double.
     */
    private double calculateExpression() {

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

    /**
     * Metoden anropas av calculateExpression(), sedan triggas rätt case och resultatet returneras.
     * @param value1 double från stacken som ska beräknas.
     * @param operator operator från listan, används som switch case.
     * @param value2 double från stacken som ska beräknas.
     * @return resultatet av inmatade värdena som en double.
     */
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


    /**
     * Metoden används för att skilja åt siffror och operatorer.
     * @param str är antingen ett tal eller en operator.
     * @return boolean ifall inmatade värdet kan ses som en double.
     */
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

    /**
     * Om o har samma adress som this så returneras true,
     * om o är null eller annan klass så returneras false,
     * annars castas o till Polish och dess sum jämförs med kallande instansens sum.
     * @param o är ett objekt som ska jämföras mot en instans av Polish.
     * @return boolean ifall en instans av Polish har samma sum som en annan.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polish polish = (Polish) o;
        return Double.compare(polish.sum, sum) == 0;
    }

    /**
     * Resultatet av uttrycket används för att skapa en hashcode
     * @return hashvärdet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sum);
    }
}
