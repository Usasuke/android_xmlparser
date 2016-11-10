package com.sasuke.android_xmlparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.text1);

        try {
            InputStream is = getAssets().open("file.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("employee");
            for (int i = 0;i<nList.getLength();i++){
                Node node = nList.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE){
                    Element element2 = (Element) node;
                    tv1.setText(tv1.getText()+"\nName :" + getValue("name",element2)+"\n");
                    tv1.setText(tv1.getText()+"Surname :" + getValue("surname",element2)+"\n");
                    tv1.setText(tv1.getText() + "------------------------------");
                }
            }
        } catch (IOException e) {
            Toast.makeText(this,"File not found",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Toast.makeText(this,"ParserConfigurationException",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (SAXException e) {
            Toast.makeText(this,"SAXException",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private static String getValue (String tag,Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return  node.getNodeValue();
    }
}
