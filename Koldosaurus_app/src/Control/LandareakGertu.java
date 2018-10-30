/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

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
            } else if (ext.equals("json")) {
                listaKargatuStringJSON(aukeratua);
                return listaKargatuJson(aukeratua);//listaKargatuStringJSON(aukeratua);
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
        } else if (ext.equals("json")) {
            listaGordeJson(land, aukeratua);
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
                /*land.setSize(land.getSize().substring(0, land.getSize().length() - 1));
                System.out.println(land.getSize());*/
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

    public static void listaGordeJson(ObservableList<Landareak> landare, File aukeratua) {
        JsonArrayBuilder aBuilder = Json.createArrayBuilder();
        try {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Landareak land : landare) {
                builder.add("Name", land.getName());
                builder.add("Description", land.getDescription());
                builder.add("Color", land.getColor());
                builder.add("Size", land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), ""));
                builder.add("Flowers", land.getFlowers());
                builder.add("CName", land.getCName());
                JsonObject landarea = builder.build();
                aBuilder.add(landarea);
            }
            JsonArray landareak = aBuilder.build();
            JsonWriter jsn = Json.createWriter(new FileOutputStream(aukeratua));
            jsn.writeArray(landareak);
            jsn.close();
        } catch (FileNotFoundException r) {
            System.out.println("kkkkk");
        }

    }

    public static ObservableList<Landareak> listaKargatuJson(File aukeratua) {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        try {
            JsonReader reader = Json.createReader(new FileInputStream(aukeratua));
            JsonArray landareak = reader.readArray();
            reader.close();

            for (int i = 0; i < landareak.size(); i++) {
                JsonObject n = landareak.getJsonObject(i);
                Landareak land = new Landareak();
                land.setName(n.getString("Name"));
                land.setDescription(n.getString("Description"));
                land.setColor(n.getString("Color"));
                land.setSize(n.getString("Size"));
                if (n.getString("Flowers").equals("yes")) {
                    land.setFlowers(true);
                } else {
                    land.setFlowers(false);
                }
                land.setCName(n.getString("CName"));
                listia.add(land);
            }
        } catch (FileNotFoundException e) {

        }
        return listia;
    }
    //Hemendik aurrera json reader eta writer stream erabiliz

    public static ObservableList<Landareak> listaKargatuStringJSON(File aukeratua) throws FileNotFoundException {//Falta implementar al codigo
        JsonParser parser = Json.createParser(new FileInputStream(aukeratua));
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        ArrayList<String> identifier = new ArrayList<String>();
        boolean a = true;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    System.out.println(event.toString());
                    if (event.toString().equals("END_OBJECT")) {
                        if (identifier.get(4).equals("yes")) {
                            a = true;
                        } else {
                            a = false;
                        }
                        Landareak land = new Landareak(identifier.get(0), identifier.get(1), identifier.get(2), identifier.get(3), a, identifier.get(5));
                        listia.add(land);
                    } else if (event.toString().equals("START_OBJECT")) {
                        identifier.clear();
                    }
                    break;
                case KEY_NAME:
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    identifier.add(parser.getString());
                    break;
            }
        }
        return listia;
    }

    //Hemen dago eredua idazteko stream moduan
    /*FileWriter writer = new FileWriter("test.txt");
JsonGenerator gen = Json.createGenerator(writer);
gen.writeStartObject()
   .write("firstName", "Duke")
   .write("lastName", "Java")
   .write("age", 18)
   .write("streetAddress", "100 Internet Dr")
   .write("city", "JavaTown")
   .write("state", "JA")
   .write("postalCode", "12345")
   .writeStartArray("phoneNumbers")
      .writeStartObject()
         .write("type", "mobile")
         .write("number", "111-111-1111")
      .writeEnd()
      .writeStartObject()
         .write("type", "home")
         .write("number", "222-222-2222")
      .writeEnd()
   .writeEnd()
.writeEnd();
gen.close();*/
    public static void DatuakGordeJSONStream(ObservableList<Landareak> listia, File aukeratua) throws IOException {
        FileWriter writer = new FileWriter(aukeratua);
        JsonGenerator gen = Json.createGenerator(writer);
        int i = 0;
        try {
            while (true) {
                gen.writeStartObject()
                        .write("Name", listia.get(i).getName())
                        .write("Description", listia.get(i).getDescription())
                        .write("Color", listia.get(i).getColor())
                        .write("Size", listia.get(i).getSize())
                        .write("Flowers", listia.get(i).getFlowers())
                        .write("CName", listia.get(i).getCName())
                        .writeEnd();
                i++;
            }
        } catch (Exception e) {
            gen.close();
        }
    }
}
