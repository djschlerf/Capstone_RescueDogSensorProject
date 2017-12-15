package RescueDog;
//  Everett Clary, Rescue Dog Server CSCE 470 Capstone project with Dave Schlerf and Jacob Wingerd
//  11/01/2017

import java.io.*;
import java.net.*; 
import java.sql.*;
import java.lang.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
// NOTE SAVE TEXT FILES AS TYPE .JS

class RescueDogServer {


public static void main(String argv[]) throws Exception {

        // New thread process spawn here, PushFTPforDogPath
        Thread t = new Thread(new PushFTPforDogPath());
        t.start();
        ////////////////////////////////*/  Receiving Pi Input Section
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        String[] myStringArray;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rescue_dog?autoReconnect=true&useSSL=false", "rescuedog", "rescuedog");
        // loop listening for TCP socket connections from PI forever
        while (true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Socket Accepted!");
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
                System.out.println("INSERT INTO rescue_dog.dog_transactions VALUES ("+nextTrans+","+myStringArray[0]+","+TransType+",'"+myStringArray[3]+"',"+myStringArray[4]+", "+myStringArray[5]+","+myStringArray[6]+","+myStringArray[7]+",'Heartbeat');");
                stmt.executeUpdate("INSERT INTO rescue_dog.dog_transactions VALUES (" + nextTrans + "," + myStringArray[0] + "," + TransType + ",'" + myStringArray[3] + "'," + myStringArray[4] + ", " + myStringArray[5] + "," + myStringArray[6] + "," + myStringArray[7] + ",'Heartbeat');");

                //  Write to screen results of string split
                // for(String w:myStringArray) System.out.println("Part: " + w);
            } // end try
            catch (Exception e) {
                 FileWriter fstream=new FileWriter("exception.txt");
                 BufferedWriter out=new BufferedWriter(fstream);
                 out.write(e.toString());
                 out.close();
            } // end catch (Exception e)
        }
    }
}
