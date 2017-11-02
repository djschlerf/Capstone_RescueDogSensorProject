//  Everett Clary, Rescue Dog Server CSCE 470 Capstone project with Dave Schlerf and Jacob Wingerd
// 11/01/2017

import java.io.*;
import java.net.*;
import java.sql.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
// NOTE SAVE TEXT FILES AS TYPE .JS

class RescueDogServer {

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
    
    public static void main(String argv[]) throws Exception {

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
            bw.newLine();

// prepare SQL statement to return all dog transactions from DB
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from dog_transactions");

// for each transaction record dependent on trans type, output properly formatted lat/long, image, appropriate meta data 
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getInt(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
                if (rs.getInt(3) == 4) // Trans Type is PING
                {
                    bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_paw.png\",\"event\":\"Heartbeat\",\"time\":\"" + rs.getString(7) + "\"}");
                    bw.newLine();
                } else if (rs.getInt(3) == 1) // Trans Type is SOUND
                {
                    bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_sound.png\",\"event\":\"Sound\",\"time\":\"" + rs.getString(7) + "\"}");
                    bw.newLine();
                } else if (rs.getInt(3) == 2) // Trans Type is BITE1
                {
                    bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact1.png\",\"event\":\"Contact 1\",\"time\":\"" + rs.getString(7) + "\"}");
                    bw.newLine();
                } else if (rs.getInt(3) == 3) // Trans Type is BITE2
                {
                    bw.write("{\"type\":\"Feature\",\"properties\":{\"place\":\"Disaster Area X\",\"url\":\"http://students.uaa.alaska.edu/capstone/images/schlerf_contact2.png\",\"event\":\"Contact 1\",\"time\":\"" + rs.getString(7) + "\"}");
                    bw.newLine();
                }   // next line is common to all transaction types
                bw.write(",\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + rs.getString(5) + "," + rs.getString(4) + "]},\"id\":\"Dog-" + rs.getInt(2) + "\"}");
                if (rs.isLast() != true) {
                    bw.write(",");
                } else {
                    bw.write("]});");
                }
// testing of selected data    
// bw.write(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));
                bw.newLine();
            }
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
            System.out.println("Connecting to server:"+server+" and port:"+port);
            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            System.out.println("Setting Login Info.");
            ftpClient.login(user, pass);
            showServerReply(ftpClient);
            System.out.println("Entering Local Passive Mode");
            ftpClient.enterLocalPassiveMode();
            showServerReply(ftpClient);
            System.out.println("Setting File Type Binary File Type");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            showServerReply(ftpClient);
            System.out.println("Changing Working Directory to: //students//capstone//data");
            ftpClient.changeWorkingDirectory("//students//capstone//data");
            showServerReply(ftpClient);
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
            else System.out.println("The first file upload NOT successfull.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

////////////////////////////////*/  Receiving Pi Input Section
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        String[] myStringArray;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rescue_dog?autoReconnect=true&useSSL=false", "rescuedog", "rescuedog");

        // loop listening for TCP socket connections from PI forever
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient
                    = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            myStringArray = clientSentence.split("\\|\\|");
            // data string from Pi is delimited by || (two vertical bars) but must be escaped with two slashes //
            System.out.println("Received: " + clientSentence);

            // Select last transaction ID for last inputted Pi transaction then increment by 1 to create next transaction
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(trans_id) from dog_transactions");
            rs.next();
            int nextTrans = rs.getInt(1) + 1;
            String TransType = "0";
            if (myStringArray[2].equals("PING")) {
                TransType = "4";
            } else if (myStringArray[2].equals("BITE1")) {
                TransType = "2";
            } else if (myStringArray[2].equals("BITE2")) {
                TransType = "3";
            } else if (myStringArray[2].equals("SOUND")) {
                TransType = "1";
            }

            // mSA[0] is DogID, mSA[1] is PiNum, mSA [2] is TransType, mSA[3] is DTTM, mSA[4] is Lat, mSA[5] is Long, mSA[6] is Elevation, mSA[7] is Temp, mSA[8] is Text
            // System.out.println("INSERT INTO rescue_dog.dog_transactions VALUES ("+nextTrans+","+myStringArray[0]+","+TransType+",'"+myStringArray[3]+"',"+myStringArray[4]+", "+myStringArray[5]+","+myStringArray[6]+","+myStringArray[7]+",'Heartbeat');");
            stmt.executeUpdate("INSERT INTO rescue_dog.dog_transactions VALUES (" + nextTrans + "," + myStringArray[0] + "," + TransType + ",'" + myStringArray[3] + "'," + myStringArray[4] + ", " + myStringArray[5] + "," + myStringArray[6] + "," + myStringArray[7] + ",'Heartbeat');");

            //  Write to screen results of string split
            // for(String w:myStringArray) System.out.println("Part: " + w);
        }
    }
}
