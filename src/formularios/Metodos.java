/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.event.KeyEvent;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author USER-11
 */
public class Metodos {
    
    public static boolean verificadorCédula(String ced) {
        int sumaPares = 0, sumaImpares = 0, ds, st, verif, aux;
        int j = 0;
        //Para verificar que la cédula tenga 10 dígitos
        if (ced.length() == 10) {
            //para verificar que se ingresen sólo números
            for (int i = 0; i < ced.length(); i++) {
                if (ced.charAt(i) > '9' || ced.charAt(i) < '0') {
                    return false;
                }
            }
            //Ciclo suma Posiciones pares
            do {
                //convertir el caracter de la posición j en entero
                int x = ((Integer.parseInt(String.valueOf(ced.charAt(j)))) * 2);
                if (x > 9) {
                    aux = x - 9;
                    x = aux;
                }
                sumaImpares = sumaImpares + x;
                j = j + 2;
            } while (j < 9);

            j = 1;
            //Ciclo suma Posiciones Impares
            do {
                //convertir el caracter de la posición j en entero
                int x = Integer.parseInt(String.valueOf(ced.charAt(j)));
                sumaPares = sumaPares + x;
                j = j + 2;
            } while (j < 8);
            st = sumaPares + sumaImpares;
            //verifica si el número es divisible para 10 de ser el caso no 
            //asciende a la decena superior
            if (st % 10 == 0) {
                ds = st;
            } else //calcula la decena superior
            {
                ds = ((st / 10) + 1) * 10;
            }
            //calcula el numero verificador
            verif = ds - st;
            //compara el numero verificador para el último digito de la cédula
            //llamado dígito verificador
            //retorna verdadero en caso de ser iguales, caso contrario retorna falso
            if (verif == (Integer.parseInt(String.valueOf(ced.charAt(9))))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static void validarCamposSoloLetras(KeyEvent evt, JTextField txt, int lon) {
        // TODO add your handling code here:
        char Alfab[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', 'ü', 'Ü', ' '};
        char c = evt.getKeyChar();
        int x = 0;
        int longitud = Alfab.length;

        for (int i = 0; i < longitud; i++) {
            if (c != (Alfab[i])) {
                x += 1;
            }
        }

        if (x == longitud || txt.getText().length() >= lon) {
            evt.consume();
        }
    }
    
    public static void validarCamposSoloNumeros(KeyEvent evt, JTextField txt, int lon) {
        // TODO add your handling code here:
        char Alfab[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char c = evt.getKeyChar();
        int x = 0;
        int longitud = Alfab.length;

        for (int i = 0; i < longitud; i++) {
            if (c != (Alfab[i])) {
                x += 1;
            }
        }

        if (x == longitud || txt.getText().length() >= lon) {
            evt.consume();
        }
    }

    public static void validarLetras(KeyEvent evt, JTextField texto) {
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
        if (texto.getText().length() >= 10) {
            evt.consume();
        }
    }

    public static void validarTelefono(KeyEvent evt, JTextField texto) {
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
        if (texto.getText().length() >= 10) {
            evt.consume();
        }
    }

}
