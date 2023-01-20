import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import org.ini4j.Wini;

public class FileObj {
  
  public static Scanner input = new Scanner(System.in);  
  private File file;
  private String filePath;
  private String fileName;
  private String fileDate;
  private String fileType;
  private File targetPath;
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Constructor
   * 
   * @param filePath String for absolute path to file
   * @param fileName String for file name, includes filetype
   * @param fileType String for type of file, includes .
   */
  public FileObj(String filePath, String fileName, String fileType) {
    this.file = new File(filePath);
    this.filePath = filePath;
    this.fileName = fileName;
    
    findFileDate();
    this.fileDate = getFileDate();
    
    this.fileType = fileType;
    this.targetPath = new File(InstaCopy.targetDir + "\\" + fileName);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Copies file from source to root
   * 
   * @return boolean representing if file was copied
   * @throws Exception if error occurs during copying
   */
  public boolean copyFile() throws Exception{    
    if (checkLog()) return false; // File was already copied
    
    String oldFileName = this.fileName; // Used for success/error output in case fileName is changed
    
    try {
      if (InstaCopy.settings.getChangePrefix()) {
        String prefix = InstaCopy.settings.getPrefix();
        String replace = InstaCopy.settings.getReplace();
        if (prefix == null || replace == null) { // Stops copy if no value for either settings
          throw new Exception("Either prefix or replaceWith were null while copying "
              + this.fileName);
        }

        
        String newName = this.fileName.replace(replace, prefix);
        setFileName(newName);
        setTargetPath(new File (InstaCopy.targetDir + "\\" + newName));
      }

      if (InstaCopy.settings.getSortFolders()) { // Sets path to correct destination for folders
        setTargetPath(new File (InstaCopy.targetDir + "\\" + this.fileDate + "\\" + this.fileName));
        File dirFolder = new File(InstaCopy.targetDir + "\\" + this.fileDate);
        dirFolder.mkdir(); // Creates directory if doesn't already exist
      }
      
      FileUtils.copyFile(this.file, this.targetPath, true); // Copies file
      //logCopiedFile();
      System.out.println("Successfully copied: " + oldFileName + " --> " + this.targetPath);
      return true;

    }catch (IOException IOExc) {
      //System.out.println("IOException: Error during copying file " + this.fileName);
      throw new Exception("IOException: Error during copying file " + oldFileName);
    }catch (Exception e) {
      //System.out.println("Exception during copying " + this.fileName + ": " + e.getMessage());
      throw new Exception("Exception during copying " + oldFileName + ": " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////   
  /**
   * Finds the file's creation date, and calls setFileDate() to set attribute
   */
  public void findFileDate() {
    try{
      Path file = Paths.get(this.filePath);
      BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
      setFileDate(attr.creationTime().toString().substring(0, 10));
    }catch (IOException ioExc) {
      System.out.println("IOException: Error during finding file date");
    }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////// 
  /**
   * Appends file name into log
   */
  public void logCopiedFile() {
    FileWriter writer = null;
    try {
      File fileLog = new File("InstaCopyLog.txt");
      //fileLog.createNewFile(); // Creates file if not already made

      writer = new FileWriter(fileLog, true);
      writer.write(this.filePath + "\n");
      writer.close();
    }catch (Exception e) {
      System.out.println("Error occured while logging file: " + e.getMessage());
    }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////// 
  /**
   * Checks if file was already copied previously
   * 
   * @return boolean representing if file is in log
   */
  public boolean checkLog() {
    Scanner fileInput = null;
    try{
      FileInputStream fileStream = new FileInputStream("InstaCopyLog.txt");       
      fileInput = new Scanner(fileStream);
      while (fileInput.hasNextLine()) {
        String line = fileInput.next();
        
        if (line.equals(this.filePath)) { // Doesn't copy file
          return true;
        }
      }
    }catch (Exception e) {
      System.out.println("Error occured during parsing log: " + e.getMessage());
    }finally {
      if (fileInput != null) {
        fileInput.close();
      }
    }
    return false;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Gets file instance variable
   * 
   * @return File for file
   */
  public File getFile() {
    return this.file;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Gets file's creation date instance variable
   * 
   * @return String for fileDate
   */
  public String getFileDate() {
    return this.fileDate;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Sets fileName attribute
   * 
   * @param fileName String representing file's name
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Sets fileDate attribute
   * 
   * @param fileDate String representing fileDate (may be changed to datetime type later)
   */
  public void setFileDate(String fileDate) {
    this.fileDate = fileDate;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Sets targetPath attribute
   * 
   * @param path Path of target
   */
  public void setTargetPath(File path) {
    this.targetPath = path;
  }
  
}
