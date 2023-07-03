package kz.alinaiil.banks.console.commands;

public class Help implements Command {
    @Override
    public void execute() {
        System.out.println("List of commands:");
        System.out.println("-createCentralBank");
        System.out.println("-showDate");
        System.out.println("-createBank");
        System.out.println("-createClient");
        System.out.println("-createAccount");
        System.out.println("-addDays");
        System.out.println("-transaction");
        System.out.println("-showBalance");
        System.out.println("-addAddress");
        System.out.println("-addPassport");
        System.out.println("-stop");
    }
}
