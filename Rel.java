
/**
 * Write a description of class Rel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rel
{
    private String left;
    private String rel;
    private String right;
    
    public Rel(String _left, String _right, String _rel)
    {
        left = _left.trim();
        right = _right.trim();
        rel = _rel.trim();
    }
    public Rel(String _rel)
    {
        this("","",_rel);
    }
    public void setLeft(String _left)
    {
        left = _left.trim();
    }
    public void setRight(String _right)
    {
        right = _right.trim();
    }
    public String getLeft()
    {
        return left;
    }
    public String getRight()
    {
        return right;
    }
    public String getRel()
    {
        return rel;
    }
    
    public boolean checkEqual(Rel r)
    {
        if(this.right.equals(r.getRight()) &&
            this.left.equals(r.getLeft()) &&
            this.rel.equals(r.getRel()))
        {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return (left + " " + rel + " " + right).trim();
    }
}
