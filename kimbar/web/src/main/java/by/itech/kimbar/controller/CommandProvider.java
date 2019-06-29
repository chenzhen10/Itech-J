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
        commands.put("addUser", new AddUserCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("editUser", new UpdateUserCommand());
        commands.put("addAttachment", new AddAttachmentCommand());
        commands.put("allAttachments", new AllAttachmentCommand());
        commands.put("editAttachment", new UpdateAttachmentCommand());
        commands.put("allUserAttachments", new AllUserAttachmentCommand());
        commands.put("download_file", new DownloadAttachmentCommand());
        commands.put("deleteAttachment", new DeleteAttachmentCommand());
        commands.put("all_phones", new AllPhoneCommand());
        commands.put("allUserPhones", new AllUserPhoneCommand());
        commands.put("addPhone", new AddPhoneCommand());
        commands.put("deletePhone", new DeletePhoneCommand());
        commands.put("editPhone", new UpdatePhoneCommand());
        commands.put("findUser", new FindUserCommand());
        commands.put("pagination", new PaginationUserCommand());
        commands.put("countOfUsers", new UserCountCommand());
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
