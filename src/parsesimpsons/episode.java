/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsesimpsons;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mike
 */
public class episode {
    protected String title;
    protected int number;
    //protected ArrayList<String> summary;
    protected String summary;
    
    //public episode(String title, int number, ArrayList<String> summary){
    public episode(String title, int number, String summary){
        //this.title = title;
        this.title = title.toString().replaceAll("\\\\","");;
        this.number = number;
        this.summary = summary;
    }
    
    public void writeConditional(PrintWriter output){
        String NL = System.getProperty("line.separator");
        output.print("<condition name=\"episode\" value=\""+number+"\">"+title+"</condition>"+NL);
        // <condition name="episode" value="1">Simpsons Roasting on an Open Fire</condition>
    }
    public void writeSummary(PrintWriter output){
        String NL = System.getProperty("line.separator");
        String first = "<condition name=\"episode\" value=\""+number+"\">";
        String last = "</condition>";

        output.print(first);
        output.print(summary);
        output.print(last+NL);
    }
    
}
