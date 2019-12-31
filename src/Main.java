import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {

    public static void lambdaShow(Predicate<String> predicate, String input){
        System.out.println(predicate.test(input));
    }


    public static void main(String[] args) {


        System.out.println("KALKYLATOR");
        Scanner scan = new Scanner(System.in);
        String infix;



        lambdaShow(str -> str.length()<5, "hej");
        lambdaShow(str -> str.length()<5 && str.startsWith("h"), "hej");

        while (true) {
            System.out.print("Ange ett korrekt matematiskt uttryck: ");
            infix = scan.nextLine();
            if(infix.equals("quit")){
                System.exit(0);
            }
            try{
                var polish = Polish.of(infix);
                System.out.println("************");
                System.out.println(polish);
                System.out.println("************");


            }catch (EmptyStackException e){
                System.out.println("Felaktigt uttryck, prova igen");

            }

        }

//        Polish polish = new Polish("((15÷(7−(1+1)))×3)−(2+(1+1))");
//        System.out.println(polish);
//
//
//        Polish polish2 = new Polish("(((35+8)-20*(4/6))+3)");
//        System.out.println(polish2);
//
//        Polish polish3 = new Polish("(3 + 5) * (7 – 2)");
//        System.out.println(polish3);
//
//        Polish polish4 = new Polish("(1+1)*2+1");
//        System.out.println(polish4);



    }


}


