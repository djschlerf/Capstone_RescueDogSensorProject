/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RescueDog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient; 
/**
 *
 * @author gato4_000
 */
public class PushFTPforDogPath implements Runnable {
    
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            } // end for Server Reply
        } // end if replies != null
    } // end showServer Reply
    
    @Override public void run(){
        
        try {

    // Connect to local mySQL database
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rescue_dog?autoReconnect=true&useSSL=false", "rescuedog", "rescuedog");

    // DataOutputStream does unicode support and wrote with 2 characters per actual character
        FileWriter file;
        BufferedWriter bw = null;
        file = new FileWriter("C:\\Users\\gato4_000\\test_data.js");
        bw = new BufferedWriter(file);

    // Begin inserted .js file supporting google maps
        bw.write("eqfeed_callback({\"type\":\"FeatureCollection\",\"features\":[");
        //bw.newLine();

    // prepare SQL statement to return all dog transactions from DB
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from dog_transactions");

    // for each transaction record dependent on trans type, output properly formatted lat/long, image, appropriate meta data 
        while (rs.next()) {
            // Write data below from recordset to screen
            // System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getInt(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7)+ " " + rs.getString(8)+ " " + rs.getString(9));
            if (rs.getInt(3) == 4) // Trans Type is PING
            {
                bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_paw.png\",\"event\":\"Heartbeat\",\"time\":\"" + rs.getString(4) + "\"}");
                //bw.newLine();
            } else if (rs.getInt(3) == 1) // Trans Type is SOUND
            {
                bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_sound.png\",\"event\":\"Sound\",\"time\":\"" + rs.getString(4) + "\"}");
                //bw.newLine();
            } else if (rs.getInt(3) == 2) // Trans Type is BITE1
            {
                bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact1.png\",\"event\":\"Contact 1\",\"time\":\"" + rs.getString(4) + "\"}");
                //bw.newLine();
            } else if (rs.getInt(3) == 3) // Trans Type is BITE2
            {
                bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact2.png\",\"event\":\"Contact 1\",\"time\":\"" + rs.getString(4) + "\"}");
                //bw.newLine();
            }   // next line is common to all transaction types
            bw.write(",\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + rs.getString(6) + "," + rs.getString(5) + "]},\"id\":\"Dog-" + rs.getInt(2) + "\"}");
            if (rs.isLast() != true) {
                bw.write(",");
            } else {
                bw.write("]});");
            } // end else
            // testing of selected data    
            // bw.write(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));
            bw.newLine();
        } // end while (rs.next())
        bw.close();   // close writing to file
    // con.close();  // close database connection
            } catch (Exception e) {
                System.out.println(e);
            }
    ////////////////////////////////  FTP Section

            String server = "webftp.uaa.alaska.edu";
            int port = 21;
            String user = "etclary";
            String pass = "Ig88yoda";

            FTPClient ftpClient = new FTPClient();
            try {
                // System.out.println("Connecting to server:"+server+" and port:"+port);
                ftpClient.connect(server, port);
                // showServerReply(ftpClient);
                // System.out.println("Setting Login Info.");
                ftpClient.login(user, pass);
                // showServerReply(ftpClient);
                // System.out.println("Entering Local Passive Mode");
                ftpClient.enterLocalPassiveMode();
                // showServerReply(ftpClient);
                // System.out.println("Setting File Type Binary File Type");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                // showServerReply(ftpClient);
                // System.out.println("Changing Working Directory to: //students//capstone//data");
                ftpClient.changeWorkingDirectory("//students//capstone//data");
                // showServerReply(ftpClient);
                // APPROACH #1: uploads first file using an InputStream
                File firstLocalFile = new File("C:\\Users\\gato4_000\\test_data.js");

                String firstRemoteFile = "test_data.js";
                InputStream inputStream = new FileInputStream(firstLocalFile);

                // System.out.println("Start uploading first file");
                boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("The first file is uploaded successfully.");
                }
                else System.out.println("The first file upload NOT successfull.");
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            } // end catch (IOException ex)
    }  // end void run()
} // end PushFTPforDogPath
