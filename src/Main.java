
public class Main {


    public static void main(String[] args) {

        Polish polish = new Polish("((15÷(7−(1+1)))×3)−(2+(1+1))");
        System.out.println(polish.getExpressionAsList());
        System.out.println(polish.calculateExpression());


        Polish polish2 = new Polish("((35+8)-20*(4/6))+3");
        System.out.println(polish2.getExpressionAsList());
        System.out.println(polish2.calculateExpression());


    }


}


