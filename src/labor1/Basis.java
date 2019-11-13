package labor1;
import javafx.scene.control.*;


class Basis {


/**
    * GetFileNameExt
    * description: holt den Namen und die Extention eines Dateinamens, ohne Pfad
    * @return String Name+Ext
    * @param String sFileName
    * @exception none
    * @version      1.1, 31.01.2006
    * @author Michael Wilhelm HS Harz, FB AI
    * @use Example
    * @see #CheckFileName
    * @see #ChangeFileExt
    * @see #getFileName extrahiert den Dateinamen ohne Pfad
    * @see #FileExists
    */
    public static String getFileNameExt(String filename) {
        int k, n;
        n = filename.length();
        k = filename.lastIndexOf("\\");
        if (k == -1) {
            // test mit /
            k = filename.lastIndexOf("/");
            if (k == -1)
                return filename;
            else {
                  // beginIndex   endIndex
                return filename.substring(k+1, n); // ??????  n-1 ist zu klein            
            }
        }
        else {
              // beginIndex   endIndex
            return filename.substring(k+1, n); // ??????  n-1 ist zu klein                        
        }
    }  // getFileNameExt



 public static void errorBox( String message, String title) {
         Dialog alert = new Alert(Alert.AlertType.ERROR,message);
         alert.setTitle(title);
         alert.setHeaderText(title);
         alert.setResizable(true);
         alert.show();
//        a.setContentText(content);
      } // ErrorBox

}