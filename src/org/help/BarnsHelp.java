package org.help;


import java.io.File;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */

public class BarnsHelp
{
    HelpSet hs;
    HelpBroker hb;
    public BarnsHelp(String nomInd)
    {
        String helpHS = "Help"+File.separator+"Master.hs";
        File fil=new File(helpHS);

        ClassLoader cl = getMyLoader();
        try
        {
            URL hsURL = fil.toURI().toURL();
            hs = new HelpSet(cl, hsURL);
        }
        catch (Exception ee)
        {
            System.out.println( "HelpSet " + ee.getMessage());
            System.out.println("HelpSet "+ helpHS +" not found");
            return;
        }
        hb = hs.createHelpBroker();
    }
    public HelpBroker obtHelpBroker()
    {
        return hb;
    }
    private ClassLoader getMyLoader() 
    {
        ClassLoader back;
        back = this.getClass().getClassLoader();
        return back;
    }
}
