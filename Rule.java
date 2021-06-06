
/**
 * Write a description of class Rule here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rule
{
    private String left;       //Left
    private String right;      //Right
    private boolean isNullableRule = false;
    
    public Rule(String _rule)
    {
        left = _rule.split("→")[0].trim();
        //System.out.println("Left : " + left);
        right = _rule.split("→")[1].trim();
        //System.out.println("Right : " + right);
        
        //left = _rule.split(">")[0].trim();
        //right = _rule.split(">")[1].trim();
        
        if(right.contains("null"))
            isNullableRule = true;
    }
    
    public String getLeft()
    {
        return left;
    }
    public String getRight()
    {
        return right;
    }
    public boolean isNullableRule()
    {
        return isNullableRule;
    }
    public String getRule()
    {
        return left + " " + right;
    }
    
    public String toString()
    {
        return left + " -> " + right; 
    }
}
