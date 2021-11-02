package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            Socket driver = new Socket( ip, 30000 );
            DataInputStream ReadFromServer = new DataInputStream( driver.getInputStream() );
            DataOutputStream WriteToServer = new DataOutputStream( driver.getOutputStream() );
            while(true) {
                Scanner scan = new Scanner( System.in );
                String str = "";
                str = ReadFromServer.readUTF();
                System.out.println( str );

                System.out.print("Location: ");
                String Location = scan.nextLine();
                WriteToServer.writeUTF( Location );

                System.out.print("Destination: ");
                String Destination = scan.nextLine();
                WriteToServer.writeUTF( Destination );

                String Route = "";
                Route = ReadFromServer.readUTF();
                System.out.println( "The Route is: " + Route );

                str = ReadFromServer.readUTF();
                System.out.printf( str );

                str = scan.nextLine();
                WriteToServer.writeUTF( str );

                if(str.equalsIgnoreCase( "no" ))
                {
                    str = ReadFromServer.readUTF();
                    System.out.printf( str );
                    break;
                }
            }
            ReadFromServer.close();
            WriteToServer.close();
            driver.close();

        }
        catch (IOException eX){
            Logger.getLogger( Driver.class.getName() ).log( Level.SEVERE, null, eX );
        }
    }
}
