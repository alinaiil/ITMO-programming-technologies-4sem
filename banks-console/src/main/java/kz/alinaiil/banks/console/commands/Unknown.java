package kz.alinaiil.banks.console.commands;

public class Unknown implements Command {
    @Override
    public void execute() {
        System.out.println("Unknown command. To get a list of commands type -help.");
    }
}
