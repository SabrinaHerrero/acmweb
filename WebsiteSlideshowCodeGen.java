import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebsiteSlideshowCodeGen {
	static JFrame numFieldsFrame;
	static JTextField numRes;
	static int numberOfResources;
	static ArrayList<JLabel> labelsArray;
	static ArrayList<JTextField> textFieldsArray;
	static JLabel status;
	static JButton generateCodeButton;
	
	public static void main(String[] args) {
		numFieldsWindow();
	}
	
	public static void numFieldsWindow() {
		numRes = new JTextField();
		numFieldsFrame = new JFrame();
		numFieldsFrame.setTitle("Number of Slides");
		numFieldsFrame.setSize(200, 150);
		numFieldsFrame.setResizable(false);
		numFieldsFrame.setLocationRelativeTo(null);
		numFieldsFrame.getContentPane().add(createViewNFW(numFieldsFrame));
		numFieldsFrame.setDefaultCloseOperation(3);

		numFieldsFrame.setVisible(true);
		
		
	}
	
	public static JPanel createViewNFW(JFrame frame) {
		JPanel mainPanel = new JPanel();
		JLabel label = new JLabel("Number of Slides (30 Max)");
		numRes = new JTextField();
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				submitNumber();
			}
			
		});
		mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
		mainPanel.add(label);
		mainPanel.add(numRes);
		mainPanel.add(submit);
		numRes.setText("");
		return mainPanel;
	}
	
	public static void submitNumber() {
		Scanner sc = new Scanner(numRes.getText());
		if (sc.hasNextInt()) {
			sc.close();
			int number = Integer.parseInt(numRes.getText());
			if (number > 0 && number <= 30) {
				numberOfResources = number;
				numFieldsFrame.setVisible(false);
				resourcePathFrame();
				numFieldsFrame.dispose();
			}
		}
		else {
			sc.close();
		}
	}
	
	public static void resourcePathFrame() {
		JFrame resPathFrame = new JFrame();
		resPathFrame.setTitle("Set Resource Paths");
		resPathFrame.setSize(600, (numberOfResources*2+2)*35);
		resPathFrame.setLocationRelativeTo(null);
		JPanel pathsMainPanel = new JPanel();
		pathsMainPanel.setLayout(new GridLayout(2*numberOfResources+2, 1, 0, 0));
		labelsArray = new ArrayList<JLabel>(); 
		textFieldsArray = new ArrayList<JTextField>();
		for (int index = 0; index < numberOfResources; index++) {
			JLabel resPathLabel = new JLabel();
			resPathLabel.setText("Slide " + (index+1) + " Path :");
			labelsArray.add(resPathLabel);
			pathsMainPanel.add(resPathLabel);
			JTextField resPathField = new JTextField("URL or Path of Resource");
			resPathField.setMinimumSize(new Dimension(30, 300));
			textFieldsArray.add(resPathField);
			pathsMainPanel.add(resPathField);
		}
		status = new JLabel("Fill in Paths and Press Generate!");
		pathsMainPanel.add(status);
		generateCodeButton = new JButton("Generate HTML Code and Copy to Clipboard");
		generateCodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String genCode = generateCode();
//				JFrame codeFrame = new JFrame();
//				codeFrame.setSize(1000, 700);
//				JPanel codePanel = new JPanel();
//				JTextArea codeArea = new JTextArea();
//				codeArea.setText(genCode);
//				codeArea.setSize(1000, 700);
//				codePanel.add(codeArea);
//				codeFrame.getContentPane().add(codePanel);
//				codeFrame.setLocationRelativeTo(null);
//				codeFrame.setVisible(true);
				StringSelection stringSelection = new StringSelection(genCode);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
				
			}
		});
		pathsMainPanel.add(generateCodeButton);
		resPathFrame.getContentPane().add(pathsMainPanel);
		
		resPathFrame.setVisible(true);

		
	}
	
	public static String generateCode() {
		String indent = "\t\t\t";
		StringBuilder result = new StringBuilder();
		result.append(indent);
		result.append("<div class=\"slideshow-container\">\n");
		for (int index = 1; index <= numberOfResources; index++) {
			result.append(indent);
			result.append("\t<div class=\"mySlides fade\">\n");
			result.append(indent);
			result.append("\t\t<div class=\"numbertext\">" + index + " / " + numberOfResources + "</div>\n");
			result.append(indent);
			result.append("\t\t<img src=\"");
			result.append(textFieldsArray.get(index-1).getText());
			result.append("\" class=\"slideshow-images\"/>\n");
			result.append(indent);
			result.append("\t</div>\n");
		}
		result.append(indent);
		result.append("\t<a class=\"prev\" onclick=\"plusSlides(-1)\">&#10094;</a>\n");
		result.append(indent);
		result.append("\t<a class=\"next\" onclick=\"plusSlides(1)\">&#10095;</a>\n");
		result.append(indent);
		result.append("</div>\n");
		result.append(indent);
		result.append("<div id=\"dots-div\" style=\"text-align:center\">\n");
		for (int index = 1; index <= numberOfResources; index++) {
			result.append(indent);
			result.append("\t<span class=\"dot\" onclick=\"currentSlide(");
			result.append(index);
			result.append(")\"></span>\n");
		}
		result.append(indent);
		result.append("</div>\n");
		return result.toString();
	}

}
