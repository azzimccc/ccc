import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.Font;
/**
 * Write a description of class RuleTextField here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RuleTextField extends JTextField
{
    public RuleTextField(int cols)
    {
        super(cols);
        Font newTextFieldFont=new Font(getFont().getName(),getFont().getStyle(),24);
        setFont(newTextFieldFont);
    }
    
    protected Document createDefaultModel() 
    {
         return new UpperCaseDocument();
    }
 
    static class UpperCaseDocument extends PlainDocument 
    {
        boolean first = true;
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException 
        {
            /*
            if (str == null) 
            {
                return;
            }
            char[] upper = str.toCharArray();
            for (int i = 0; i < upper.length; i++) {
                upper[i] = Character.toUpperCase(upper[i]);
            }
            super.insertString(offs, new String(upper), a);
            //*/
            //String _str = "";
            if(str == null)
            {
                return;
            }
            if(str.equals("0"))
                str = "null";
            if(str.equals(">") || str.equals("."))
                str = "→";
            if(first && str.equals(str.toUpperCase()) && str != null)
            {
                str = str + " → "; 
                first = false;
            }
            else
                if(!str.equals(" "))
                    str = str + " ";
            //System.out.println(str);
            super.insertString(offs, str, a);
            //*/
        }
    }
}
