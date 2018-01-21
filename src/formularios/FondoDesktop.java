/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;



import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
 

public class FondoDesktop implements Border{
    public BufferedImage img;
 
    public FondoDesktop(){
        try {
            URL imagePath = new URL(getClass().getResource("/imagenes/fondo2.jpg").toString());
            img = ImageIO.read(imagePath); 
        } catch (Exception ex) {            
        }
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(img, (x + (width - img.getWidth())/2),(y + (height - img.getHeight())/2), null);
    }
 
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
 
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
   }