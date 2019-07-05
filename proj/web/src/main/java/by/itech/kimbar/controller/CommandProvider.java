package by.itech.kimbar.controller;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.controller.command.impl.attachment.*;
import by.itech.kimbar.controller.command.impl.email.MessageTemplateCommand;
import by.itech.kimbar.controller.command.impl.email.SendEmailCommand;
import by.itech.kimbar.controller.command.impl.phone.*;
import by.itech.kimbar.controller.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private  Map<String, Command> commands = new HashMap<>();

    private CommandProvider() {
        commands.put("allUsers", new AllUserCommand());
        commands.put("add/user", new AddUserCommand());
        commands.put("delete/user", new DeleteUserCommand());
        commands.put("edit/user", new UpdateUserCommand());
        commands.put("add/attachment", new AddAttachmentCommand());

        commands.put("edit/attachment", new UpdateAttachmentCommand());
        commands.put("allUserAttachments", new AllUserAttachmentCommand());
        commands.put("downloadFile", new DownloadAttachmentCommand());
        commands.put("delete/attachment", new DeleteAttachmentCommand());

        commands.put("allUserPhones", new AllUserPhoneCommand());
        commands.put("add/phone", new AddPhoneCommand());
        commands.put("delete/phone", new DeletePhoneCommand());
        commands.put("edit/phone", new UpdatePhoneCommand());
        commands.put("findUser", new FindUserCommand());
        commands.put("pagination", new PaginationUserCommand());
        commands.put("countOfUsers", new UserCountCommand());
        commands.put("countOfFoundUsers", new CountOfFoundUserCommand());
        commands.put("sendEmail", new SendEmailCommand());
        commands.put("showUserPhoto", new ShowUserPhotoCommand());
        commands.put("templates", new MessageTemplateCommand());
    }

    Command getCommand(String command) {
        return commands.get(command);
    }

    static CommandProvider getInstance() {
        return instance;
    }
}
