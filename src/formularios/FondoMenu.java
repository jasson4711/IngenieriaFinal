/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.image.BufferedImage;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;
/**
 *
 * @author PC
 */
public class FondoMenu implements Border{

    private BufferedImage image;

    public FondoMenu(BufferedImage image) {
        this.image = image;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int x0 = x + (width - image.getWidth()) / 2;
        int y0 = y + (height - image.getHeight()) / 2;
        g.drawImage(image, x0, y0, null);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
