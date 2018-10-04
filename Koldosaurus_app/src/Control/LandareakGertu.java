/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;

/**
 *
 * @author DM3-2-15
 */
public class LandareakGertu {

    public static ObservableList<Landareak> datuakKargatu() throws IOException {

        ObservableList<Landareak> listia = FXCollections.observableArrayList();

        String line;
        String[] arrs;

        try {
            BufferedReader br = new BufferedReader(new FileReader("Landareak.txt"));
            while ((line = br.readLine()) != null) {
                arrs = line.split("#");
                Landareak land = new Landareak(arrs[0], arrs[1], arrs[2], arrs[3], Boolean.parseBoolean(arrs[4]), arrs[5]);
                listia.add(land);
            }
            return listia;
        } catch (IOException io) {
            System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        }
        return null;
    }

   /* public static void datuakIdatzi(Landareak land) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter("Landareak.txt", true));
            // Write objects to file
            output.printf(land.getName() + "#" + land.getDescription() + "#" + land.getColor() + "#" + land.getSize() + "#" + land.getFlowers() + "#" + land.getCName() + "\r\n");
            output.close();
        } catch (IOException io) {
        }
    }*/

    public static void lista_gorde(ObservableList<Landareak> landare){
        try{
        PrintWriter output = new PrintWriter(new FileWriter("Landareak.txt"));
        for (Landareak land : landare) {
            output.printf(land.getName() + "#" + land.getDescription() + "#" + land.getColor() + "#" + land.getSize().replace(land.getSize().substring(land.getSize().length()-1), "") + "#" + land.getFlowers(3) + "#" + land.getCName() + "\r\n");
        }
        output.close();
        }catch(IOException io){
            
        }
    }
}
