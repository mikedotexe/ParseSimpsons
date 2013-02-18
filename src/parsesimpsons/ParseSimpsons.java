/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parsesimpsons;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Mike
 */
public class ParseSimpsons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String title="";
        int number=0;
        String epsummary="";
        Scanner inputFile;
        episode currentEp;
        ArrayList<episode> everyEpisode = new ArrayList();
        ArrayList<String> summary = new ArrayList();
        ArrayList<String> clean = new ArrayList();
        String line;
        String delims = "wgTitle";
        String delimsb = "\"";
        
        File dir = new File("./simpsons");
        String files[] = dir.list();
        System.out.println("files below");
        for (String html : files){
            if ("weird".equals(html)) continue;
            System.out.println();
            System.out.println("CURRENT FILE: "+html);
            inputFile = new Scanner(new FileInputStream("./simpsons/"+html));

            while (inputFile.hasNextLine()){
                line = inputFile.nextLine();
                boolean a = line.matches("(?i).*wgTitle.*");
                if (a){
                    String[] tokens = line.split(delims);
                    tokens = tokens[1].split(delimsb);
                    title = tokens[2];
                    //System.out.println(tokens[2]);
                }
                boolean b = line.matches("(?i).*<th>Episode&#160;no.</th>");
                if (b){
                    line = inputFile.nextLine();
                    String eppy = removeHTML(line);
                    number = Integer.parseInt(eppy);
                }
                boolean c = line.matches("(?i).*</small></center>");
                //boolean d = line.matches("(?i).*<table class=\"metadata .*");
                //if (c || d){
                boolean e=false;
                if (c){
                    //System.out.println("found it");
                    for (int i=0; i<7; i++){
                        line = inputFile.nextLine();
                    }
                    while (!"<table id=\"toc\" class=\"toc\">".equals(line) || e){
                        summary.add(line);
                        if (inputFile.hasNextLine()){
                            line = inputFile.nextLine();
                        }
                        else{
                            break;
                        }
                        e = line.matches("(?i)<h2><span class=\"editsection\">.*");
                        if (e) break;
                        //if ("A_Star_Is_Torn.htm".equals(html)){
                            //System.out.println("WHERE"+line);
                        //}
                    }
                }
            }
            Iterator it = summary.iterator();
            String fine="";
            while (it.hasNext()){
                line = (String) it.next();
                clean.add(removeHTML(line));
                fine += removeHTML(line);
            }


            System.out.println("EPISODE DETAILS:");
            System.out.println(title);
            System.out.println(number);
            it = clean.iterator();
            while (it.hasNext()){
                line = (String) it.next();
                System.out.println(line);        
            }

            //System.out.println("working?");
            inputFile.close();
            
            // Call constructor
            //currentEp = new episode(title, number, clean);
            currentEp = new episode(removeHTML(title), number, fine);
            everyEpisode.add(currentEp);
            summary.clear();
            clean.clear();
        }
        PrintWriter outputFile = new PrintWriter(new FileWriter("simpsons.xml", false));
        PrintWriter outputSummary = new PrintWriter(new FileWriter("summaries.xml", false));
        Iterator it = everyEpisode.iterator();
        episode reuse;
        while (it.hasNext()){
            reuse = (episode) it.next();
            reuse.writeConditional(outputFile);
            reuse.writeSummary(outputSummary);
        }
        outputFile.close();
        outputSummary.close();
        
        
        it = everyEpisode.iterator();
            
    }
    
    static String removeHTML(String dirty){
        String nohtml0 = dirty.toString().replaceAll("\\<.*?>|[.*]","");
        String clean0 = nohtml0.toString().replaceAll("\\[.*?]","");        
        clean0 = clean0.toString().replaceAll("\\&#.*;","");        
        clean0 = clean0.toString().replaceAll("\\/","");        
        clean0 = clean0.toString().replaceAll("\\\"","");        
        clean0 = clean0.toString().replaceAll("\\\'","");        
        return clean0;
    }
}
