import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;
//import org.ini4j.Wini;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.RowLayout;

public class InstaCopy {

  public static File sourceDir;
  public static File targetDir;
  public static FileObj[] filesList;
  public static Scanner input = new Scanner(System.in);
  public static Settings settings;

////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Contains main code
   * 
   * @param args Unused
   */
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    
    //Shell layout
    RowLayout rowLayout = new RowLayout();
    rowLayout.type = SWT.HORIZONTAL;
    rowLayout.wrap = false;
    rowLayout.marginLeft = 50;
    rowLayout.marginTop = 20;
    rowLayout.marginBottom = 20;
    rowLayout.marginRight = 50;
    shell.setLayout(rowLayout);
    
    //Shell properties
    shell.setText("InstaCopy");
    //shell.setSize(250, 150);
    shell.setMaximized(true); // Maximizes window
    
    //Copy button
    Button copyButton = new Button(shell, SWT.PUSH);
//    copyButton.setLocation(50, 50);
    Point size = copyButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    copyButton.setSize(size);
    copyButton.setText("Copy");
//    copyButton.pack();
    
    //Settings button
    Button settingsButton = new Button(shell, SWT.PUSH);
    settingsButton.setText("Settings");
    //settingsButton.pack();
    
    //shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
    
    sourceDir = new File("C:\\Anton\\Programs\\Java\\InstaCopy 2.0\\Source");//getSourceDir();
    targetDir = new File("C:\\Anton\\Programs\\Java\\InstaCopy 2.0\\Target"); //getRootDir();
    
    createFiles();
    settings = new Settings();
        
//    int numCopied = 0;
//    int numErrored = 0;
//    for (FileObj file: filesList) {
//    	try{
//    		if (file.copyFile()) {
//    			numCopied++;
//    		}
//    	} catch (Exception e) {
//    		System.out.println(e.getMessage());
//    		numErrored++;
//    	}
//    }
//    System.out.println("Files copied: " + numCopied + " Errors: " + numErrored);
    
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Prompts user for source directory
   * 
   * @return String representing source
   */
  public static String getSourceDir() {
    System.out.print("Enter source directory: ");
    return input.next();
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Prompts user for root directory
   * 
   * @return String representing root
   */
  public static String getRootDir() {
    System.out.print("Enter root directory: ");
    return input.next();
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
