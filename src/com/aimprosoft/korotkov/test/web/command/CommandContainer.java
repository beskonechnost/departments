package com.aimprosoft.korotkov.test.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Андрей on 30.01.2017.
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("AllDepartments", new AllDepartmentsCommand());
        commands.put("UpdateDepartment", new UpdateDepartmentCommand());
        commands.put("UpdateDepartmentForm", new UpdateDepartmentFormCommand());
        commands.put("DeleteDepartment", new DeleteDepartmentCommand());
        commands.put("RemovalConfirmationDepartment", new RemovalConfirmationDepartmentCommand());
        commands.put("ListEmployeesThisDepartment", new ListEmployeesThisDepartmentCommand());
        commands.put("AddNewEmployeeInThisDepartment", new AddNewEmployeeInThisDepartmentCommand());
        commands.put("AddEmployee", new AddEmployeeCommand());
        commands.put("AddNewDepartment", new AddNewDepartmentCommand());
        commands.put("AddDepartment", new AddDepartmentCommand());
        commands.put("RemovalConfirmationEmployee", new RemovalConfirmationEmployeeCommand());
        commands.put("DeleteEmployee", new DeleteEmployeeCommand());
        commands.put("UpdateEmployee", new UpdateEmployeeCommand());
        commands.put("UpdateEmployeeForm", new UpdateEmployeeFormCommand());




        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}
