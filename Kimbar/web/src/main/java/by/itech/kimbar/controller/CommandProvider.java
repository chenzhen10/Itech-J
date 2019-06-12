package by.itech.kimbar.controller;

import by.itech.kimbar.controller.impl.AddContactCommand;
import by.itech.kimbar.controller.impl.AllContactsCommand;
import by.itech.kimbar.controller.impl.GoToAddUserPageCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private final static CommandProvider instance = new CommandProvider();
    private  Map<String, Command> commands = new HashMap();

    private CommandProvider() {
        commands.put("all_users", new AllContactsCommand());
        commands.put("add_user", new AddContactCommand());
        commands.put("go_to_add_user_page", new GoToAddUserPageCommand());
    }

    public  Command getCommand(String command) {
        return commands.get(command);
    }

    public static CommandProvider getInstance() {
        return instance;
    }
}
