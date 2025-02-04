package Main;

import PresentationLayer.CLI;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI();
        System.out.println("███████╗██╗   ██╗██████╗ ███████╗██████╗       ██╗     ███████╗███████╗");
        System.out.println("██╔════╝██║   ██║██╔══██╗██╔════╝██╔══██╗      ██║     ██╔════╝██╔════╝");
        System.out.println("███████╗██║   ██║██████╔╝█████╗  ██████╔╝█████╗██║     █████╗  █████╗  ");
        System.out.println("╚════██║██║   ██║██╔═══╝ ██╔══╝  ██╔══██╗╚════╝██║     ██╔══╝  ██╔══╝  ");
        System.out.println("███████║╚██████╔╝██║     ███████╗██║  ██║      ███████╗███████╗███████╗");
        System.out.println("╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝      ╚══════╝╚══════╝╚══════╝");
        System.out.println("\n\n");
        cli.main() ;
        System.out.println("\n\n\n\nTermination Notice:\n" +
                "\n" +
                "This system was developed by Lidor and Gil. Usage is governed by terms agreed with \"Super-Lee\". Unauthorized use, reproduction, or distribution is prohibited and may result in legal action.\n" +
                "\n" +
                "All intellectual property rights are owned by Lidor and Gil. Any copyright infringement will be pursued to the fullest extent of the law.\n" +
                "\n" +
                "Instructions for use and basic assumptions are detailed in the attached PDF.");
    }

}
