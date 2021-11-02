package com.company;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {
        try {
            //establishing server socket and its port
            ServerSocket server = new ServerSocket( 30000 );
            System.out.print("Server Running.");
            TimeUnit.SECONDS.sleep( 2 );
            System.out.print(".");
            TimeUnit.SECONDS.sleep( 2 );
            System.out.print(".");

            //Server connected to Sensor server
            InetAddress ip = InetAddress.getLocalHost();
            Socket ServerToSensor = new Socket(ip, 5000);
            DataInputStream ReadFromSensor = new DataInputStream( ServerToSensor.getInputStream() );
            DataOutputStream WriteToSensor = new DataOutputStream( ServerToSensor.getOutputStream() );

            //to handle drivers requests
            while(true) {
                //connecting server to driver
                Socket DriverToServer = server.accept();
                System.out.println("Driver accepted.");
                DataInputStream ReadFromDriver = new DataInputStream( DriverToServer.getInputStream() );
                DataOutputStream WriteToDriver = new DataOutputStream( DriverToServer.getOutputStream() );
                String Location = "";
                String Destination = "";
                String Route = "";
                String str = "";

                //to check driver requests
                while(true)
                {
                    WriteToDriver.writeUTF( "Hello! This is recommendation system for routes, please enter your location and destination" );
                    Location = ReadFromDriver.readUTF();

                    Destination = ReadFromDriver.readUTF();

                    WriteToSensor.writeUTF( Location );
                    WriteToSensor.writeUTF( Destination );

                    Route = ReadFromSensor.readUTF();


                    WriteToDriver.writeUTF( Route );

                    str = "Do you need any routes? (Yes/No) ";
                    WriteToDriver.writeUTF( str );

                    str = ReadFromDriver.readUTF();
                    WriteToSensor.writeUTF( str );

                    if(str.equalsIgnoreCase( "no" ) ) {
                        WriteToDriver.writeUTF( "Thanks for using our system" );
                        break;
                    }
                }
                ReadFromDriver.close();
                WriteToDriver.close();
                DriverToServer.close();
            }

        } catch (IOException | InterruptedException eX) {
            Logger.getLogger( Server.class.getName() ).log( Level.SEVERE, null, eX );
        }
    }
}
