package kz.alinaiil.banks.console.commands;

public class StopConsole implements Command {
    @Override
    public void execute() {
        System.out.println("Have a nice day!");
    }
}
