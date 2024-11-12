package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AlternativeClassesSniffer.AlternativeClassesSniffer;

public class AlternativeClassesTest {
    AlternativeClassesSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new AlternativeClassesSniffer();
    }

    // true = detecta mal olor. Cumple con el mal olor. Diferente interfaz 
    // false = no cumple. Es deci, misma interfaz 

    // Caso 1: Dos clases con el mismo polimorficas,con mismos métodos y parametros identicos.
    // este caso debe retornar false, es decir que al ser identicios no presetna el mal olor.
    @Test
    public void testEsPolimorfico() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse() {
                          mover.adelante();
                    }
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                        mover.adelante();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 2: Dos clases con métodos similares pero una de las clases tiene un 
    // metodo adicional, por lo que es un mal olor
    // por lo que este test devuelve true
    @Test
    public void testClaseConMasMetodos() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse() {
                          mover.adelante();
                    }
                    def Sentarse(){
                        mover.sentarse();
                    }
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                        mover.adelante();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 3: Clases con misma cantidad de métodos, parametros pero con distinto nombres
    //en los métodos
    // devuelve true porque se llaman distinto
    @Test
    public void testClaseMetodosDistintosMismosParametros() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def maullar(volumen) {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 4: Método con mismos nombres 
    //contenido diferente (no hay mal olor)
    @Test
    public void testMetodosYParametrosIgualesConContenidoDiferentes() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def ladrar(volumen) {
                        print("Ladrando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 5:La segunda clase tiene mas métodos que la primera
    //retorna true
    @Test
    public void testMetodosConNombresDiferentesPeroParametrosIguales() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse() {
                          mover.adelante();
                    }
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                        mover.adelante();
                    }
                    def saltar(mover) {
                        mover.saltar();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }



     // Caso 7: Métodos con nombres diferentes, parámetros diferentes
     @Test
     public void testMetodosConNombresDiferentesParametrosDiferentesYContenidoIgual() {
         String code = """
                 class Perro {
                     def ladrar(volume) {
                         print("Ladrando");
                     }
                 }
                 class Gato {
                     def maullar(potencia) {
                         print("Ladrando");
                     }
                 }
                 """;
         AromaReport report = new AromaReport(code);
         codeSniffer.sniff(code, report);

         assertTrue(report.stinks()); // Debe detectar mal olor
    }

    @Test
public void testMasDeDosClasesPolimorficas() {
    String code = """
            class Perro {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            class Gato {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            class Pajaro {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            """;
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); // No debe hacer nada si hay más de dos clases
}

}
