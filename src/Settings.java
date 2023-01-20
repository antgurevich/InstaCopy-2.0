import java.io.File;
import org.ini4j.Wini;

public class Settings {  
  
  private static Wini ini;
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Creates ini file
   */
  public Settings() {
    createIni();
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Gets path for source
   * 
   * @return String of SourceDirectory path
   * @throws Exception If SourceDirectory can't be retrieved
   */
  public String getSource() throws Exception{
    try{
      return ini.get("USER_INFO", "SourceDirectory");
    }catch (Exception e) {
      throw new Exception("Error getting Source in ini: " + e.getMessage());
    }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////// 
  /**
   * Gets path for root
   * 
   * @return String of RootDirectory path
   * @throws Exception If RootDirectory can't be retrieved
   */
  public String getRoot() throws Exception{
    try{
      return ini.get("USER_INFO", "RootDirectory");
    }catch (Exception e) {
      throw new Exception("Error getting Root in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Gets value of changePrefix
   * 
   * @return value of changePrefix
   */
  public boolean getChangePrefix() {
    try {
      return ini.get("SETTINGS", "ChangePrefix", boolean.class);
    }catch (Exception e) {
      System.out.println("Error getting changePrefix in ini: " + e.getMessage());
      return false; // Returns false on default (change to throw eventually?)
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Gets value of sortFolders
   * 
   * @return value of sortFolders
   */
  public boolean getSortFolders() {
    try {
      return ini.get("SETTINGS", "SortFolders", boolean.class);
    }catch (Exception e) {
      System.out.println("Error getting SortFolders in ini: " + e.getMessage());
      return false; // Returns false on default (change to throw eventually?)
    }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////// 
  /**
   * Gets prefix value
   * 
   * @return String prefix value
   */
  public String getPrefix() {
    try {
      return ini.get("PREFIX", "Prefix");
    }catch (Exception e) {
      System.out.println("Error getting Prefix in ini: " + e.getMessage());
      return null; // Returns null on default (change to throw eventually?)
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Gets replace value
   * 
   * @return String replace value
   */
  public String getReplace() {
    try {
      return ini.get("PREFIX", "Replace");
    }catch (Exception e) {
      System.out.println("Error getting Replace in ini: " + e.getMessage());
      return null; // Returns null on default (change to throw eventually?)
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////  
  /**
   * Sets source directory in ini
   * 
   * @param sourcePath String of source dir
   */
  public void setSourceDir(String sourcePath) {
    try{
      ini.put("USER_INFO", "SourceDirectory", new File(sourcePath).getPath());
      
      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting Source in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Sets root directory in ini
   * 
   * @param rootPath String of root dir
   */
  public void setRootDir(String rootPath) {
    try{
      ini.put("USER_INFO", "RootDirectory", new File(rootPath).getPath());

      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting Root in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Switches change prefix value in ini
   */
  public void changeChangePrefix() {
    try{
      boolean currentVal = ini.get("SETTINGS", "ChangePrefix", boolean.class);

      ini.put("SETTINGS", "ChangePrefix", !currentVal);
      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting ChangePrefix in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Switches sort folders value in ini
   */
  public void changeSortFolders() {
    try{
      boolean currentVal = ini.get("SETTINGS", "SortFolders", boolean.class);

      ini.put("SETTINGS", "SortFolders", !currentVal);
      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting SortFolders in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Sets prefix in ini
   * 
   * @param prefix String of prefix
   */
  public void setPrefix(String prefix) {
    try{
      ini.put("PREFIX", "Prefix", prefix);

      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting Prefix in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Sets string to replace in ini
   * 
   * @param replace String of what is to be replaced
   */
  public void setReplace(String replace) {
    try{
      ini.put("PREFIX", "Replace", replace);

      ini.store();
    }catch (Exception e) {
      System.out.println("Error setting Replace in ini: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Initializes default values for ini
   */
  public void createDefaults() {
    try{
      ini.put("USER_INFO", "SourceDirectory", InstaCopy.sourceDir.getPath());
      ini.put("USER_INFO", "RootDirectory", InstaCopy.targetDir.getPath());
      
      ini.put("SETTINGS", "ChangePrefix", false);
      ini.put("SETTINGS", "SortFolders", false);
      
      ini.put("PREFIX", "Replace", null);
      ini.put("PREFIX", "Prefix", null);
      
      ini.store();
    }catch (Exception e) {
      System.out.println("Error creating default ini values: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Creates ini (settings) file if not existing, sets instance variable
   */
  public void createIni() {
    try {
      File iniFile = new File("InstaCopySettings.ini");
      if (!iniFile.exists()) { // If doesn't exist, creates & loads with defaults
         iniFile.createNewFile();
         ini = new Wini(iniFile);
         createDefaults();
      }else { // Already exists
        ini = new Wini(iniFile);
      }
    }catch (Exception e) {
      System.out.println("Error creating ini file: " + e.getMessage());
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
