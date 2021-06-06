
/**
 * Write a description of class Oper here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Oper
{
    private String token;   //Inside the { }
    private String key;     //e.g First
    private String value;  //terminal
    
    public Oper(String _token, String _key, String _value)
    {
        token = _token;
        key = _key;
        value = _value;
    }
    
    public Oper()
    {
        this("","","");
    }
    
    public void addValue(String _value)
    {
        value = value + ", " + _value;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public String getKey()
    {
        return key + "{ }";
    }
    
    public String toString()
    {
        return key + "{" + token + "} = {" + value + "}";  
    }
}
