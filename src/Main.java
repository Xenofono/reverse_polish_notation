
public class Main {


    public static void main(String[] args) {

        Polish polish = new Polish("((15÷(7−(1+1)))×3)−(2+(1+1))");
        System.out.println(polish);


        Polish polish2 = new Polish("(((35+8)-20*(4/6))+3)");
        System.out.println(polish2);

        Polish polish3 = new Polish("(3 + 5) * (7 – 2)");
        System.out.println(polish3);
    }


}


