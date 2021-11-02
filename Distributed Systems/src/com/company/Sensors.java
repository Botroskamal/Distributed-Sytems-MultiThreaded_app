package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensors {
    public static void main(String[] args){
        try {
            //establishing sensor server socket and its port
            ServerSocket sensor = new ServerSocket( 5000 );
            System.out.print( "Sensors Running." );
            TimeUnit.SECONDS.sleep( 2 );
            System.out.print( "." );
            TimeUnit.SECONDS.sleep( 2 );
            System.out.print( "." );

            while(true) {
                //connecting sensor socket to server socket
                Socket SensorToServer = sensor.accept();
                DataInputStream ReadFromServer = new DataInputStream( SensorToServer.getInputStream() );
                DataOutputStream WriteToServer = new DataOutputStream( SensorToServer.getOutputStream() );
                String Location = "";
                String Destination = "";
                String Route = "";

                while(true){
                    Location = ReadFromServer.readUTF();
                    Destination = ReadFromServer.readUTF();

                    Route = "drive along for 500m then turn right and drive for 200m and turn around and drive for 50m";
                    WriteToServer.writeUTF( Route );
                }
            }
        } catch(IOException | InterruptedException eX){
            Logger.getLogger( Server.class.getName() ).log( Level.SEVERE, null, eX );
        }
    }
}
