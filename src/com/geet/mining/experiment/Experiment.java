package com.geet.mining.experiment;

import java.io.File;

import com.geet.mining.model.Issue;

public class Experiment {

	public static void main(String[] args) {
		InputHandler inputHandler = new InputHandler();
		File inputDirectory = new File("src/com/geet/mining/input/");
		if (inputDirectory.isDirectory()) {
			File[] directoriesOfIssue = inputDirectory.listFiles();
			for (int i = 0; i < directoriesOfIssue.length; i++) {
				for (int j = 0; j < directoriesOfIssue.length; j++) {
					if (directoriesOfIssue[i].isDirectory() && directoriesOfIssue[j].isDirectory()) {
						Issue issue1 = inputHandler.readIssueFromDirectory(directoriesOfIssue[i]+"/logs.txt");
						Issue issue2 = inputHandler.readIssueFromDirectory(directoriesOfIssue[j]+"/logs.txt");
						issue1.setCosine(issue2);
						System.out.print(issue1.getCosine()+",");						
					}
				}
				System.out.println(directoriesOfIssue[i].getAbsolutePath());
			}
		}	
	}
}
