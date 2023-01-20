/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.jCliente;

/**
 *
 * @author UsuarioF
 */
public class cliente {

    public static void main(String[] args) {
        final int PUERTO = 5030;
        byte[] buffer;
         jCliente jcliente = new jCliente();

        Scanner teclado = new Scanner(System.in);
        try {

            InetAddress direccion = InetAddress.getByName("localhost");
            while (true) {
                try (
                    DatagramSocket socketUDP = new DatagramSocket()) {
                    System.out.println("Ingrese una palabra");
                    String mensaje  = teclado.nextLine();
                    buffer = new byte[1024];
                    buffer = mensaje.trim().getBytes();
                    DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccion, PUERTO);
                    socketUDP.send(pregunta);

                    buffer = new byte[1024];
                    DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                    socketUDP.receive(peticion);
                    mensaje = new String(peticion.getData());
                    System.out.println(mensaje);
                }

            }

        } catch (SocketException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
