import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstaCopy {

  private static File sourceDir;
  private static File targetDir;
  private static FileObj[] filesList;
  public static Scanner input = new Scanner(System.in);
  public static Settings settings;
  private static JFrame frame;

////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Contains main code
   * 
   * @param args Unused
   */
  public static void main(String[] args) {
    
	 setupFrame();
    
    sourceDir = getSourceDir();
    targetDir = getTargetDir();
    
    createFiles();
    settings = new Settings();
    
    //startCopy();
    
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Creates frontend interface
   */
  private static void setupFrame() {
	  frame = new JFrame("InstaCopy");
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	  
	  JPanel panel = new JPanel(null); // Overall Container
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  int screenWidth = (int) screenSize.getWidth();
	  int screenHeight = (int) screenSize.getHeight();
	  Color bgColor = new Color(18, 18, 18);
	  Color menuColor = new Color(24, 24, 24);
	  
	  // Top bar
	  JPanel topPanel = new JPanel();
	  topPanel.setBackground(menuColor);
	  topPanel.setSize( screenWidth, screenHeight/10 );
	  setupTopPanel(topPanel);
	  panel.add(topPanel);
	  
	  // Side section
	  JPanel sidePanel = new JPanel(null);
	  sidePanel.setBackground(menuColor);
	  sidePanel.setLocation(screenWidth - screenWidth/4, screenHeight/10);
	  sidePanel.setSize(screenWidth/4, screenHeight - screenHeight/7);
	  setupSidePanel(sidePanel);
	  panel.add(sidePanel);
	  
	  // Main section
	  JPanel bodyPanel = new JPanel(null);
	  bodyPanel.setBackground(bgColor);
	  bodyPanel.setSize( screenWidth - screenWidth/4, screenHeight - screenHeight/10);
	  bodyPanel.setLocation(0, 100);
	  
	  panel.add(bodyPanel);
	  
	  frame.setContentPane(panel);
	  frame.setVisible(true);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  private static void setupTopPanel(JPanel panel) {

	  
	  JLabel title = new JLabel("InstaCopy");
	  title.setForeground(new Color(255, 255, 255));
	  //title.setSize(100, 50);
	  title.setFont(new Font("Arial", Font.PLAIN, 30));
	  title.setLocation(new Point(0, 100));
	  panel.add(title);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  private static void setupSidePanel(JPanel panel) {
	  panel.setLayout(null);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  private static void setupMainPanel(JPanel panel) {
	  panel.setLayout(null); // https://www.javatpoint.com/GridLayout
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Copies files
   */
  private static void startCopy() {
	  int numCopied = 0;
	  int numErrored = 0;
	  for (FileObj file: filesList) {
		  try{
			  if (file.copyFile()) {
				  numCopied++;
			  }
		  } catch (Exception e) {
			  System.out.println(e.getMessage());
			  numErrored++;
		  }
	  }
	  System.out.println("Files copied: " + numCopied + " Errors: " + numErrored);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Prompts user for source directory
   * 
   * @return File of source
   */
  public static File getSourceDir() {
    //System.out.print("Enter source directory: ");
    //return input.next();
	return new File("C:\\Anton\\Programs\\Java\\InstaCopy 2.0\\Source");
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Prompts user for target directory
   * 
   * @return File of target
   */
  public static File getTargetDir() {
    //System.out.print("Enter root directory: ");
    //return input.next();
	return new File("C:\\Anton\\Programs\\Java\\InstaCopy 2.0\\Target"); 
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Creates filesList with a Files object per file
   */
  public static void createFiles() {
    FileFilter textFileFilter = new FileFilter(){
      public boolean accept(File file) {
         boolean isFile = file.isFile();
         return isFile;
      }
   };
    File[] filesInSource = sourceDir.listFiles(textFileFilter);
    filesList = new FileObj[filesInSource.length];
    
    String type;
    
    for (int i = 0; i < filesInSource.length; i++) {
      type = filesInSource[i].getName().substring(filesInSource[i]
          .getName().lastIndexOf('.')).toLowerCase();
      
      filesList[i] = new FileObj(filesInSource[i]
                              .getAbsolutePath(), filesInSource[i].getName(), type);
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
