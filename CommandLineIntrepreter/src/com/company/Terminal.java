package com.company;
//import org.apache.commons.io.FileUtils;

import java.io.*;
		import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



public class Terminal {
	boolean isMoved = false;
	static final private String DefaultPath = "E:\\";
	static private String currentPath = DefaultPath;
	static private Scanner scanner = new Scanner(System.in);

	public Terminal() {
		// 	System.out.print(currentPath);
	}

	//copy file from place A to place B
	public void cp(String sourcePath, String destinationPath) {
		try {
			FileInputStream infile = new FileInputStream(sourcePath);
			FileOutputStream outfile = new FileOutputStream(destinationPath);
			byte[] b = new byte[1024];
			int len;
			while ((len = infile.read(b, 0, 1024)) > 0) {
				outfile.write(b, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("error recieved");
		} catch (IOException inputoutputexception) {
			inputoutputexception.printStackTrace();
			System.out.println("error recieved");
		}
	}

	//move file from place A to place B
	public void mv(String sourcePath, String destinationPath) {
//		If the last argument names an existing directory,
//		mv moves each other given file into a file with the
//		same name in that directory. Otherwise, if only
//		two files are given, it moves the first onto the
//		second. It is an error if the last argument is not6
//		directory and more than two files are given.
	}

	public static void main(String[] args) {
		while (true) {
			Parser parser = new Parser(takeInput());
			Terminal terminal = new Terminal();
			if (parser.parse()) {
				String cmd = parser.getCmd();
				String[] arguments = parser.getArgs();
				switch (cmd) {
					case "clear": {
						terminal.clear();
						break;
					}
					case "cd": {
						terminal.cd(arguments[0]);
						break;
					}
					case "ls": {
						terminal.ls(arguments);
						break;
					}
					case "cp": {
						terminal.cp(arguments[0], arguments[1]);
						break;
					}
					case "mv": {
						terminal.mv(arguments[0], arguments[1]);
						break;
					}
					case "rm": {
						terminal.rm(arguments[0]);
						break;
					}
					case "mkdir": {
						terminal.mkdir(arguments[0]);
						break;
					}
					case "rmdir": {
						terminal.rmdir(arguments[0]);
						break;
					}
					case "cat": {
						terminal.cat(arguments);
						break;
					}
					case "more": {
						terminal.more(arguments);
						break;
					}
					case "pwd": {
						terminal.pwd(arguments);
						break;
					}
					case "args": {
						terminal.args(arguments[0]);
						break;
					}
					case "date": {
						terminal.date();
						break;
					}
					case "help": {
						terminal.help();
						break;
					}

				}
			}

			terminal.clear();
		}
	}

	public static String takeInput() {
		String in = scanner.nextLine();
		return in;
	}

	//remove file at Place A
	public void rm(String sourcePath) {
//		rm removes each specified file. By default, it does
//		not remove directories.
		File file = new File(sourcePath);
		file.delete();
	}

	//make new directory
	public void mkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	//make new directory
	public void rmdir(String path) {
//		rmdir removes each given empty directory. If any
//		nonoption argument does not refer to an existing
//		empty directory, it is an error.
		File directory = new File(path);

		// make sure directory exists
		if (!directory.exists()) {

			System.out.println("Directory does not exist.");
			System.exit(0);

		} else {

			try {

				delete(directory);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		System.out.println("Done");
	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				// System.out.println("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted!");
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	//sout current path
	public void pwd(String[] arguments) {
		System.out.print(currentPath);
	}

	//list all elements at current folder/directory
	public void ls(String[] arguments) {
		File file = new File(currentPath);
		String files[] = file.list();
		for (String s : files) {
			System.out.println(s);
		}
	}

	//change directory
	public void cd(String path) {
		currentPath = path;
	}

	public String[] cat(String[] paths, boolean overloadedcall) {
		String[] dataoffiles = new String[paths.length];
		StringBuilder fileslist = new StringBuilder();
		String[] outputfilespaths = new String[paths.length];
		int found = 0 , tmp = 0;
		int counter = 0;

		if(paths[0].equals("cat")){
			tmp = 1 ;
		}
		for (int i = tmp; i < paths.length; i++) {
			if (paths[i].equals("ls") && i == 0) {
				File file = new File(currentPath);
				String[] files = file.list();
				for (String s : files) {
					fileslist.append(s);
					fileslist.append("\n");
				}
				dataoffiles[i] = fileslist.toString();

				continue;
			}

			if (paths[i].equals(">>")) {
				found = 1;
				break;
			} else if (paths[i].equals(">")) {
				found = 2;
				break;
			} else {
				int counter2 =0 ;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(paths[i]));
					// here we get the name of the files to later use for another command
					while (true) {
						String s = reader.readLine();
						if (s == null) {
							break;
						}
						if (dataoffiles[counter2] == null) {
							dataoffiles[counter2] = "";
							dataoffiles[counter2] += s;
							dataoffiles[counter2] += "\n";
							continue;
						}
						dataoffiles[counter2] += s;
						dataoffiles[counter2] += "\n";
					}
					reader.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException E) {
					E.printStackTrace();
				}
			}
		}

		/* to be done */ //checkArrowsvalidity(paths ,arrowindex) ;

		// now check arg is it concatenation or overwrite ..
		int arrowindex = getindexofarrow(paths);

		// concatenate
		if (found == 1) {
			try {

				for (int k = arrowindex + 1; k < paths.length; k++) {
					FileWriter writer = new FileWriter(new File(paths[k]), true);
					BufferedWriter bufferedWriter = new BufferedWriter(writer);

					outputfilespaths[counter++] = paths[k];  // to be used for another command

					for (String data : dataoffiles) {
						if (data == null)
							break;
						bufferedWriter.newLine();
						bufferedWriter.write(data);
					}
					bufferedWriter.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return outputfilespaths;

		}
		// overwrite
		else if (found == 2) {
			try {
				for (int k = arrowindex + 1; k < paths.length; k++) {
					File file = new File(paths[k]);
					FileWriter Writer = new FileWriter(file);
					outputfilespaths[counter++] = paths[k]; // to be used for another command

					Writer.write("");
					for (String data : dataoffiles) {
						if (data == null)
							break;
						Writer.write(data);
					}
					Writer.close();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException E) {
				E.printStackTrace();
			}
			return outputfilespaths;

		}
		// da fe 7alet en mafesh arrows aslun f hrg3 eli mwgood fel files
		return dataoffiles;
	}

	//print all content in files A and/only B
	public void cat(String[] paths) {

		String[] dataoffiles = new String[paths.length];
		int found = 0;
		StringBuilder fileslist = new StringBuilder();
		for (int i = 0; i < paths.length; i++) {
			if (paths[i].equals("ls") && i == 0) {
				File file = new File(currentPath);
				String[] files = file.list();
				for (String s : files) {
					fileslist.append(s);
					fileslist.append("\n");
				}
				dataoffiles[i] = fileslist.toString();

				continue;
			}
			if (paths[i].equals(">>")) {
				found = 1;
				break;
			} else if (paths[i].equals(">")) {
				found = 2;
				break;
			} else {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(paths[i]));
					// here we get the data from each file path
					while (true) {
						String s = reader.readLine();
						if (s == null) {
							break;
						}
						dataoffiles[i] += (s+"\n");
					}
					reader.close();

				} catch (FileNotFoundException e) {
					System.out.println("FileNotFoundException :" + e.getMessage());
				} catch (IOException E) {
					E.printStackTrace();
				}
			}
		}


		// now check arg is it concatenation or overwrite ..
		int arrowindex = getindexofarrow(paths);
		//checkArrowsvalidity(paths ,arrowindex) ;

		if (found == 1) {
			try {
				for (int k = arrowindex + 1; k < paths.length; k++) {
					FileWriter writer = new FileWriter(new File(paths[k]), true);
					BufferedWriter bufferedWriter = new BufferedWriter(writer);
					for (String data : dataoffiles) {
						if (data == null)
							break;
						bufferedWriter.newLine();
						bufferedWriter.write(data);
					}
					bufferedWriter.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (found == 2) {
			try {
				for (int k = arrowindex + 1; k < paths.length; k++) {
					File file = new File(paths[k]);
					FileWriter Writer = new FileWriter(file);
					Writer.write("");
					for (String data : dataoffiles) {
						if (data == null)
							break;
						Writer.write(data);
					}
					Writer.close();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException E) {
				E.printStackTrace();
			}
		}
		// da fe 7alet en mafesh arrows aslun f htb3 eli mwgood 3al console
		else {
			for (String dataoffile : dataoffiles) {
				System.out.println(dataoffile + "\n\n");
			}

		}

	}

	private boolean checkArrowsvalidity(String[] paths, int index) {

		if (index - 1 > -1 && index + 1 <= paths.length && (paths[index].equals(">>") || paths[index].equals(">"))) {
			try {
				if (paths[0].equals("ls")) {
					return true;
				}
				BufferedReader bufferreader = new BufferedReader(new FileReader(paths[index - 1]));
				bufferreader = new BufferedReader(new FileReader(paths[index + 1]));

			} catch (FileNotFoundException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	private int getindexofarrow(String[] paths) {
		for (int i = 0; i < paths.length; i++) {
			if (paths[i].equals(">>") || paths[i].equals(">")) {
				return i;
			}
		}
		return -1;
	}

	//clear console window
	public void clear() {
		System.out.flush();
	}

	//read all in file
	public void more(String[] args) {
		if (args[0].equals("cat") || args[0].equals("ls")) {
			int arrowindex = getindexofarrow(args);
			String[] datafiles = cat(args, true);
			if (arrowindex == -1) {
				try {
					StringBuilder builder = new StringBuilder();

					for (int i = 0; i < datafiles.length; i++) {
						if(datafiles[i] != null)
							builder.append(datafiles[i]);
					}
						for (int i = 0 ; i < builder.toString().split("\n").length ; i += 10) {
							for (int j = 0; j <10 && i+j < builder.toString().split("\n").length ; j++) {
								String currLine = builder.toString().split("\n")[i+j];
								System.out.println(currLine);
							}
							System.out.println("Enter 'C' if you want to print again and any Input if not ");
							String check = scanner.nextLine();
							if (!check.equalsIgnoreCase("C")) {
								break;
							}
						}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// da fe 7alet en fe redirection operators
			else {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(datafiles[0]));
					BufferedReader r = null ;
					String firstline = reader.readLine();
					boolean condition = firstline!= null;
					String currLine ;

					// first line printed
					System.out.println(firstline);
					for (int i = 0; i < datafiles.length; i++) {
						if(i != 0){
							 r = new BufferedReader(new FileReader(datafiles[i])) ;
						}
						for (int j = 1; condition; j++) {
							for (int k = 1; k <= 10; k++) {
								if(i==0)
									currLine = reader.readLine();
								else
									currLine = r.readLine() ;

								if (currLine == null) {
									condition = false;
									break;
								}
								System.out.println(currLine);
							}
							System.out.println("Enter 'C' if you want to print again and any Input if not ");
							String check = scanner.nextLine();
							if (!check.equalsIgnoreCase("C")) {
								break;
							}
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			// for >> more file file .
			if(args.length > 1){
				System.out.println("Can't apply (More) command on more than file");
			}
			else{
				String currLine ;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(args[0]));
					String firstline = reader.readLine();
					boolean condition = firstline!= null;

					// first line printed
					System.out.println(firstline);
					for (int i = 1; condition;i++){
						for (int j = 1; j <= 10; j++) {
							currLine = reader.readLine();
							if (currLine == null) {
								condition = false ;
								break;
							}
							System.out.println(currLine);
						}
						System.out.println("Enter 'C' if you want to print again and any Input if not ");
						String check = scanner.nextLine();
						if(!check.equalsIgnoreCase("C")){
							break;
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}


			}

		}


	}
    public void args(String commandName) {

    }

    //sout current date
    public void date() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }

    //sout all available command syntax
    public void help() {

    }

}
