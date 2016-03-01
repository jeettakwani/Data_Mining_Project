import java.util.Scanner;

public class RossmannSales {

    public static void main(String args[]) throws Exception {



        System.out.println("Which experiment would you like to run? : ");
        System.out.println("1 : Experiment 1 ");
        System.out.println("2 : Experiment 2 ");
        System.out.println("3 : Experiment 3 ");
        System.out.println("4 : Experiment 4 ");
        System.out.println("5 : Experiment 5 ");
        System.out.println("6 : Experiment 6 ");
        System.out.println("7 : Experiment 7 ");
        System.out.println("8 : Experiment 8 ");
        System.out.println("9 : Experiment 9 ");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();


        switch (choice) {


            case 1:
                Experiments.experiment1();
                break;

            case 2:
                Experiments.experiment2();
                break;

            case 3:
                Experiments.experiment3();
                break;

            case 4:
                Experiments.experiment4();
                break;

            case 5:
                Experiments.experiment5();
                break;

            case 6:
                Experiments.experiment6();
                break;

            case 7:
                Experiments.experiment7();
                break;

            case 8:
                Experiments.experiment8();
                break;

            case 9:
                Experiments.experiment9();
                break;


        }


    }
}
