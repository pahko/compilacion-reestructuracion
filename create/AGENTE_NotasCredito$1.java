// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_NotasCredito.java

package create;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

// Referenced classes of package create:
//            Fosar_Agente_NotasCredito, AGENTE_NotasCredito, Fosar_Agente

class AGENTE_NotasCredito$1 extends AGENTE_NotasCredito
    implements Observer
{

    public void update(Observable unObservable, Object dato)
    {
        AGENTE_NotasCredito.access$000(AGENTE_NotasCredito.this).setText(dato.toString());
        System.out.println(dato);
        System.out.println((new StringBuilder()).append("SEG : ").append(seg).toString());
        if(FACTURA && (seg == 0 || seg == 10 || seg == 20 || seg == 30 || seg == 40 || seg == 50))
            try
            {
                if(!FACTURANDO)
                {
                    FACTURANDO = true;
                    Fosar_Agente_NotasCredito fosa;
                    try
                    {
                        fosa = new Fosar_Agente_NotasCredito();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    FACTURANDO = false;
                }
                System.gc();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        if(seg == 0)
        {
            FACTURA = false;
            System.out.println("BORRANDO ....");
            System.out.println("Datos Borrados !!!");
            System.out.println("Copiando ..... ");
            System.out.println("Copia Finalizada !!!");
            FACTURA = true;
        }
        if(seg >= 60)
        {
            seg = 0;
            System.exit(0);
        } else
        {
            seg++;
        }
    }

    private Fosar_Agente fosa;
    final AGENTE_NotasCredito this$0;

    AGENTE_NotasCredito$1()
    {
    	super();
    	this$0 = AGENTE_NotasCredito.this;
        
    }
}
