//package RescueDogServer;
import java.io.*;
import java.net.*;
import java.sql.*;  
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
// NOTE SAVE TEXT FILES AS TYPE .JS
class RescueDogServer 
{
 public static void main(String argv[]) throws Exception {

try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rescue_dog?autoReconnect=true&useSSL=false","rescuedog","rescuedog");  

// DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("C:\\Users\\gato4_000\\file.txt"));
// DataOutputStream does unicode support and wrote with 2 characters per actual character
FileWriter file; 
BufferedWriter bw = null;
file = new FileWriter("C:\\Users\\gato4_000\\test_data.js");
bw = new BufferedWriter(file);
bw.write("eqfeed_callback({\"type\":\"FeatureCollection\",\"features\":[");
bw.newLine();
//        bw.write("Hello World!");

Statement stmt=con.createStatement();  
ResultSet rs=stmt.executeQuery("select * from dog_transactions");  
while(rs.next())
    {
    System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));  
    if (rs.getInt(3) == 4) // Trans Type is PING
            {
            bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_paw.png\",\"event\":\"Heartbeat\",\"time\":\""+rs.getString(7)+"\"}");
            bw.newLine();
            }
    if (rs.getInt(3) == 1) // Trans Type is SOUND
            {
            bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_sound.png\",\"event\":\"Sound\",\"time\":\""+rs.getString(7)+"\"}");
            bw.newLine();
            }
    if (rs.getInt(3) == 2) // Trans Type is BITE1
            {
            bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact1.png\",\"event\":\"Contact 1\",\"time\":\""+rs.getString(7)+"\"}");
            bw.newLine();
            }   
    if (rs.getInt(3) == 3) // Trans Type is BITE2
            {
            bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact2.png\",\"event\":\"Contact 1\",\"time\":\""+rs.getString(7)+"\"}");
            bw.newLine();
            }   
    bw.write(",\"geometry\":{\"type\":\"Point\",\"coordinates\":["+rs.getString(5)+","+rs.getString(4)+"]},\"id\":\"Dog-"+rs.getInt(2)+"\"}");
    if (rs.isLast() != true) bw.write(","); else bw.write("]});");
    // bw.write(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));
    bw.newLine();
    }
bw.close();   // close writing to file
// con.close();  // close database connection
}catch(Exception e){ System.out.println(e);}  
/*////////////////////////////////  FTP Section

String server = "webftp.uaa.alaska.edu/students/capstone/data";
        int port = 21;
        String user = "lpetc";
        String pass = "Ig88yoda";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, null);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("C:\\Users\\gato4_000\\test_data.js");
 
            String firstRemoteFile = "test_data.js";
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
} catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } 
*/
/////////////////////////////////  Receiving Pi Input Section

  String clientSentence;
  String capitalizedSentence;
  ServerSocket welcomeSocket = new ServerSocket(6789); 
  String[] myStringArray;
  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rescue_dog?autoReconnect=true&useSSL=false","rescuedog","rescuedog");  

  while (true) {
   Socket connectionSocket = welcomeSocket.accept();
   BufferedReader inFromClient =
    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
   DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
   clientSentence = inFromClient.readLine();
   myStringArray = clientSentence.split("\\|\\|");
   
   System.out.println("Received: " + clientSentence);
   
   Statement stmt=con.createStatement();  
   ResultSet rs=stmt.executeQuery("select max(trans_id) from dog_transactions");
   rs.next();
   int nextTrans = rs.getInt(1) +1;
   String TransType = "0";
   if (myStringArray[2].equals("SOUND")) TransType = "1";
   if (myStringArray[2].equals("BITE1")) TransType = "2";
   if (myStringArray[2].equals("BITE2")) TransType = "3";
   if (myStringArray[2].equals("PING")) TransType = "4";
   
   // mSA[0] is DogID, mSA[1] is PiNum, mSA [2] is TransType, mSA[3] is DTTM, mSA[4] is Lat, mSA[5] is Long, mSA[6] is Elevation, mSA[7] is Temp, mSA[8] is Text
   
   System.out.println("INSERT INTO rescue_dog.dog_transactions VALUES ("+nextTrans+","+myStringArray[0]+","+TransType+",'"+myStringArray[3]+"',"+myStringArray[4]+", "+myStringArray[5]+","+myStringArray[6]+","+myStringArray[7]+",'Heartbeat');");
   stmt.executeUpdate("INSERT INTO rescue_dog.dog_transactions VALUES ("+nextTrans+","+myStringArray[0]+","+TransType+",'"+myStringArray[3]+"',"+myStringArray[4]+", "+myStringArray[5]+","+myStringArray[6]+","+myStringArray[7]+",'Heartbeat');");
   
  // INSERT INTO rescue_dog.dog_transactions VALUES (49,1,1,61.188871, -149.820151,'Input from a mic array','2017-10-08 10:49:30');
   
   for(String w:myStringArray) System.out.println("Part: " + w);

  }
 }
}