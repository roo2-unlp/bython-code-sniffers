package oo2.redictado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import oo2.redictado.LongMethodCodeSniffer.*;

/* IMPORTAR SNIFFER */

public class LongMethodTest {
    
    /* DECLARAR SNIFFER */
    LongMethodCodeSniffer codeSniffer;

    @BeforeEach
    public void setUp(){
        codeSniffer = new LongMethodCodeSniffer();
    }

    @Test
    public void testSuperaLineasYCaracteres(){
        String code = """
        def llamadas(origen, destino, duracion, tipo){
            llamada.setOrigen(origen);
            llamada.setDestino(destino);
            llamada.setDuracion(duracion);
            if (duracion > 100) {
                llamada.setExtra(50);
            } else {
                llamada.setExtra(0);
            }
            llamada.impuestos(origen, destino);
            llamada.calcular_costo(tipo);
            llamada.calcular_credito();
            llamadas.add(get_llamada());
        }
        """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks()); /*daria stink porque excede los caracteres y lineas permitidos*/
    }

    @Test
    public void testSuperaLineas(){
        String code = 
            """
            def resultado(){
                res + alfajor.p; 
                res + cocacola.p;
                res + galletitas.p; 
                res + barradecereal.p;
                res + medialuna.p; 
                res + cereales.p;
                res + gomitas.p;
                res + saladix.p;
                res + chicle.p;
                res + mentos.p; 
                res + oblea.p;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks()); /*daria stink porque se excede de las lineas permitidas*/
    }

    @Test
    public void testSuperaCaracteres(){

        String code = 
        """
        def algo(vehiculo1, vehiculo2){
            if ((vehiculo1.getPeso() > vehiculo2.getPeso() and vehiculo1.getTamanio() > vehiculo2.getTamanio)
                and (vehiculo1.getVelocidadPromedio() < vehiculo2.getVelocidadPromedio())){
                return true;
            }
            if ((vehiculo1.getCantPuertas() > vehiculo2.getCantPuertas()) and (vehiculo1.getEspacioBaul() > vehiculo2.getEspacioBaul())
                and (vehiculo1.getCantidadLitrosNafta() > vehiculo2.getCantidadLitrosNafta())){
                return true;
            }
            if ((vehiculo1.getCantidadCaballosFuerza() < vehiculo2.getCantidadCaballosFuerza()) and (vehiculo1.getVelocidadMaxima()
                < vehiculo2.getVelocidadMaxima())) {
                return true;    
            }
            
            return false;
        }
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks()); /*daria stink porque se excede de los caracteres maximos*/
    }

    @Test
    public void testNoEsMetodo(){

        String code = """
        monitor1 = 20000;
        monitor2 = 10000;
        mouse = 5000;
        teclado = 7000;
        procesador = 20000;
        placaVideo = 30000;
        ram = 15000;
        fuente = 10000;
        gabinete = 17000;
        coolers = 3000;
        resultado = monitor1 + monitor2 + mouse + teclado + procesador + placaVideo + ram + fuente + gabinete + coolers;
        """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks()); /*no daria stink porque no esta dentro de un metodo, por eso es false*/
    }

    @Test
    public void testCorrecto(){

        String code =
        """
        
        def sumar (a,b,c){
            return a + b + c;
        }
        
        """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks()); /*no daria stink alguno por eso es false*/
    }



}
