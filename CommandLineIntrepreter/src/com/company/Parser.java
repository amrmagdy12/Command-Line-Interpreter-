package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    String[] args;
    String cmd;
	int isSavedInFile;
	String destinationPath;

	public int isSavedInFile() {
		return isSavedInFile;
	}

	static Scanner scanner = new Scanner(System.in);

    static final String[] commands = {"clear", "cd", "ls", "cp"
            , "mv", "rm", "mkdir", "rmdir", "cat", "more", "pwd", "args", "date", "help"};


    public Parser(String in) {
		isSavedInFile=0;
        if (!in.isEmpty()) {
            cmd = in.split(" ")[0];
            int len = in.split(" ").length - 1;
            args = new String[len];
            if (in.indexOf(" ") != -1) {
                for (int i = 1; i < in.split(" ").length; i++) {
                    args[i - 1] = in.split(" ")[i];
                }
            }
        }
    }

    public boolean parse() {
        boolean isCommandFound = false;
        int num_of_args=0;
        for(int index=0;index<args.length;index++)
        {
            if(args[index].equalsIgnoreCase(">>")||args[index].equalsIgnoreCase(">"))
            {
                break;
            }
            num_of_args++;
        }
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equalsIgnoreCase(cmd)) {
                isCommandFound = true;
                break;
            }
        }
        if (!isCommandFound) {
            return false;
        }
        boolean isCorrectArgs = false;
        switch (cmd) {
            case "clear": {
                if (num_of_args== 0) {
                    return true;
                }
                break;
            }
            case "cd": {
                if (num_of_args== 1) {
                    return true;
                }
                break;
            }
            case "ls": {
                if (num_of_args == 0) {
                    return true;
                }
                break;
            }
            case "cp": {
                if (num_of_args== 2) {
                    return true;
                }
                break;
            }
            case "mv": {
                if (num_of_args == 2) {
                    return true;
                }
                break;
            }
            case "rm": {
                if (num_of_args == 1) {
                    return true;
                }
                break;
            }
            case "mkdir": {
                if (num_of_args == 1) {
                    return true;
                }
                break;
            }
            case "rmdir": {
                if (num_of_args == 1) {
                    return true;
                }
                break;
            }
            case "cat": {
            	//nzbtha
				if (num_of_args>=1) {
                    return true;
                }
                break;
            }
            case "more": {

                if (num_of_args >= 1) {
                    return true;
                }
                break;
            }
            case "pwd": {
                if (num_of_args == 0) {
                    return true;
                }
                break;
            }
            case "args": {
                if (num_of_args== 1) {
                    return true;
                }
                break;
            }
            case "date": {
            	//nzbtha
                if (num_of_args== 0) {
                    return true;
                }
                break;
            }
            case "help": {
                if (num_of_args == 0) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    public String[] getArgs() {
        return args;
    }
    public String getCmd() {return cmd;}
}
