/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.jCliente;

/**
 *
 * @author UsuarioF
 */
public class servidor {

    public static void main(String[] args) {
        final int PUERTO = 5030;
        byte[] buffer;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Java", "Lenguaje de programacion");
        map.put("Postgres", "Herramienta de base de datos");
        map.put("Carro", "Vehiculo que se usa para movilizarce en tierra");
        map.put("CPU", "Unidad Central de Procesamiento");
        map.put("Teclado", "Parte de un computador");
        map.put("String", "Tipo de dato no numerico ");
        map.put("Int", "Tipo de dato numerico usado en enteros");
        map.put("Ascii", "Estandares de caracteres");
        map.put("HTML", "HyperText Markup Language");
        map.put("MVC", "Modelo vista controlador");
        try {
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
            while (true) {

                buffer = new byte[1024];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                while (true) {

                    socketUDP.receive(peticion);

                    String mensaje = new String(peticion.getData(), 0, peticion.getLength());
                    System.out.println(mensaje);

                    if (mensaje.equals("") || mensaje.matches("[0-9]*")) {
                        String errorVa = "Por favor digite un valor";
                        buffer = new byte[1024];
                        buffer = errorVa.getBytes();
                        int puertoCliente = peticion.getPort();
                        InetAddress direccion = peticion.getAddress();
                        DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
                        socketUDP.send(respuesta);
                        System.out.println("Vacio");
                    } else {
                        boolean salida1 = false;
                        for (HashMap.Entry<String, String> entry : map.entrySet()) {
                            if (entry.getKey().trim().equalsIgnoreCase(mensaje.trim())) {
                                String mensajeSer = "Significado: " + entry.getValue();
                                buffer = new byte[1024];
                                buffer = mensajeSer.getBytes();
                                int puertoCliente = peticion.getPort();
                                InetAddress direccion = peticion.getAddress();
                                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
                                socketUDP.send(respuesta);
                                salida1 = true;
                                System.out.println(mensajeSer);
                            }
                        }
                        if (salida1 == false) {
                            String notFound = "No existe la palabra";
                            System.out.println("hola");
                            buffer = notFound.getBytes();
                            int puertoCliente = peticion.getPort();
                            InetAddress direccion = peticion.getAddress();
                            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
                            socketUDP.send(respuesta);
                            System.out.println(notFound);

                        }

                    }
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
