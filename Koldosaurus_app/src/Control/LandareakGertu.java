/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 *
 * @author DM3-2-15
 */
public class LandareakGertu {

    public static ObservableList<Landareak> fitxategiaAukeratu(File aukeratua) {

        String ext = aukeratua.getName().substring(aukeratua.getName().length() - 4);
        try {
            if (ext.equals(".txt")) {
                return datuakKargatu(aukeratua);
            } else if (ext.equals(".xml")) {
                return datuakKargatuxml(aukeratua);
            }
        } catch (IOException ex) {
            Logger.getLogger(LandareakGertu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void datuakGordeFitxategia(ObservableList<Landareak> land, File aukeratua) {
        String ext = aukeratua.getName().substring(aukeratua.getName().length() - 4);
        if (ext.equals(".txt")) {
            lista_gorde(land, aukeratua);
        } else if (ext.equals(".xml")) {
            lista_gordexml(land, aukeratua);
        }
    }

    public static ObservableList<Landareak> datuakKargatu(File aukeratua) throws IOException {

        ObservableList<Landareak> listia = FXCollections.observableArrayList();

        String line;
        String[] arrs;

        try {
            BufferedReader br = new BufferedReader(new FileReader(aukeratua));
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

    public static void lista_gorde(ObservableList<Landareak> landare, File aukeratua) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(aukeratua));
            for (Landareak land : landare) {
                output.printf(land.getName() + "#" + land.getDescription() + "#" + land.getColor() + "#" + land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), "") + "#" + land.getFlowers(3) + "#" + land.getCName() + "\r\n");
            }
            output.close();
        } catch (IOException io) {

        }
    }

    public static ObservableList<Landareak> datuakKargatuxml(File aukeratua) throws IOException {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(aukeratua);
            NodeList nList = doc.getElementsByTagName("Landarea");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element eElement = (Element) nList.item(temp);
                Landareak landare = new Landareak(eElement.getElementsByTagName("Name").item(0).getTextContent(),
                        eElement.getElementsByTagName("Description").item(0).getTextContent(),
                        eElement.getElementsByTagName("Color").item(0).getTextContent(),
                        eElement.getElementsByTagName("Size").item(0).getTextContent(),
                        Boolean.parseBoolean(eElement.getElementsByTagName("Flowers").item(0).getTextContent()),
                        eElement.getElementsByTagName("CName").item(0).getTextContent());
                listia.add(landare);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listia;
    }

    public static void lista_gordexml(ObservableList<Landareak> landare, File aukeratua) {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
//
            // definimos el elemento raíz del documento
            Element eRaiz = doc.createElement("Landareak");
            doc.appendChild(eRaiz);
            for (Landareak land : landare) {
                Element eLandare = doc.createElement("Landarea");
                eRaiz.appendChild(eLandare);

                Element eName = doc.createElement("Name");
                eName.appendChild(doc.createTextNode(land.getName()));
                eLandare.appendChild(eName);

                Element eDescription = doc.createElement("Description");
                eDescription.appendChild(doc.createTextNode(land.getDescription()));
                eLandare.appendChild(eDescription);

                Element eColor = doc.createElement("Color");
                eColor.appendChild(doc.createTextNode(land.getColor()));
                eLandare.appendChild(eColor);

                Element eSize = doc.createElement("Size");
                eSize.appendChild(doc.createTextNode(land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), "")));
                eLandare.appendChild(eSize);

                Element eFlowers = doc.createElement("Flowers");
                eFlowers.appendChild(doc.createTextNode(land.getFlowers()));
                eLandare.appendChild(eFlowers);

                Element eCName = doc.createElement("CName");
                eCName.appendChild(doc.createTextNode(land.getCName()));
                eLandare.appendChild(eCName);
            }
            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(aukeratua);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
