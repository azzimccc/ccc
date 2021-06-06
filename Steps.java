import java.util.ArrayList;
/**
 * @author (MKA)
 * @version 1.0
 * @date 29th Sept 2019
 */
public class Steps
{
    private ArrayList<Rule> ruleList;
    private ArrayList<Rel> BDWlist;
    private ArrayList<Rel> BWlist;
    private ArrayList<Rel> FDBlist;
    private ArrayList<Rel> DEOlist;
    private ArrayList<Rel> EOlist;
    private ArrayList<Rel> FBlist;
    private ArrayList<Rel> FBSlist;
    
    private ArrayList<String> nullNT;
    private ArrayList<String> T;
    private ArrayList<String> NT;
    private ArrayList<Oper> f1;     //First{1}
    private ArrayList<Oper> f2;     //First{2}
    private ArrayList<Oper> f3;     //FOL{3}
    private ArrayList<Oper> f4;     //SEL{4}
    
    String output = "";
    public Steps(ArrayList<Rule> _ruleList)
    {
        ruleList = _ruleList;
        nullNT = new ArrayList<String>();
        NT = new ArrayList<String>();
        T = new ArrayList<String>();
        
        //Step 1
        output += "Step 1 \n";
        for(Rule r : ruleList)
        {
            String[] _r = r.getRule().split(" ");
            
            for(int i=0; i < _r.length; i++)
            {
                if(_r[i].equals("null"))
                    continue;
                if(_r[i].equals(_r[i].toLowerCase()))
                {
                    if(!T.contains(_r[i]))
                        T.add(_r[i]);
                }
                else
                    if(!NT.contains(_r[i]))
                        NT.add(_r[i]);
            }

            if(r.isNullableRule())
            {
                //System.out.println("Step 1");
                //System.out.println("Nullable Non Terminal : " + r.getLeft());
                output += "Nullable Non Terminal : " + r.getLeft() + "\n";
                nullNT.add(r.getLeft());
            }
        }
        if(nullNT.isEmpty())
            output += "Nullable Non Terminal : none" + "\n";
        Step2();
        printRel(BDWlist, "Step 2 : BDW");
        Step3();
        printRel(BWlist, "Step 3 : BW");
        Step4();
        printOper(f1, "Step 4 : First");
        Step5();
        printOper(f2, "Step 5 : First");
        
        if(!nullNT.isEmpty())
        {
            Step6();
            printRel(FDBlist, "Step 6 : FDB");
            Step7();
            printRel(DEOlist, "Step 7 : DEO");
            Step8();
            printRel(EOlist, "Step 8 : EO");
            Step9();
            printRel(FBlist, "Step 9 : FB");
            Step10();
            printRel(FBSlist, "Step 10 : FB \nFor starting Non-Terminal");
            Step11();
            printOper(f3, "Step 11 : Follow");
        }
        Step12();
        printOper(f4, "Step 12 : Selection Set");
    }
    
    public void Step2()
    {
        BDWlist = new ArrayList<Rel>();
        for(Rule r : ruleList)
        {
            String _left = r.getLeft();
            String[] _right = r.getRight().split(" ");
            
            for(int i=0; i < _right.length; i++)
            {
                String s = _right[i];
                if(!r.getRight().contains("null"))
                {
                    Rel rel = new Rel(_left, s, "BDW");
                    if(!checkRelList(BDWlist, rel))
                        BDWlist.add(rel);
                        
                    if(isNullable(s))
                    {
                        if(i+1 < _right.length)
                        {
                            Rel _rel = new Rel(_left, _right[i+1], "BDW");
                            if(!checkRelList(BDWlist, _rel))
                                BDWlist.add(_rel);
                        }
                        continue;
                    }
                    else
                        break;
                }
            }
            
        }
    }
    
    private void Step3()
    {
        //int x = 0;
        int size = BDWlist.size();
        BWlist = copyRel(BDWlist, "BW");
        
        //Reflect
        for(int x = 0 ; x < size; x++)
        {
            for(int y = size-1; y > -1; y--)
            {
                if(BDWlist.get(x).getRight().equals(BDWlist.get(y).getLeft()))
                {
                    Rel _r = new Rel(BDWlist.get(x).getLeft(), BDWlist.get(y).getRight(), "BW");
                    if(!checkRelList(BWlist, _r))
                        BWlist.add(_r);
                }
            }
        }
        
        //Trans
        for(String s: NT)
        {
            Rel rel = new Rel(s, s, "BW");
            if(!checkRelList(BWlist, rel))
                BWlist.add(rel);
        }
        for(String s : T)
        {
            Rel rel = new Rel(s, s, "BW");
            
            if(!checkRelList(BWlist, rel))
                BWlist.add(rel);
        }
    }
    
    private void Step4()
    {
        f1 = new ArrayList<Oper>();
        ArrayList<String> check = new ArrayList<String>();
        Oper o;
        for(Rel r : BWlist)
        {
            String _token = r.getLeft();
            String _value = r.getRight();
            if(_value.equals(_value.toLowerCase()))
            {
                if(!check.contains(_token))
                {
                    check.add(_token);
                    f1.add(new Oper(_token, "First", _value));
                }
                else
                {
                    for(int i=0; i < f1.size(); i++)
                    {
                        if(f1.get(i).getToken().equals(_token))
                            f1.get(i).addValue(_value);
                    }
                }
            }
        }
    }
    
    public void Step5()
    {
        int c = 0;
        f2 = new ArrayList<Oper>();
        String tempValue = "";
        for(Rule r : ruleList)
        {
            String[] _r = r.getRule().split(" ");
            tempValue = "";
            for(int i=0; i < _r.length; i++)
            {
                if(nullNT.contains(_r[i]))
                {
                    if(i + 1 > _r.length)
                    {
                        tempValue += findOper(f1, _r[i+1]);
                    }
                }
                else
                {
                    tempValue += findOper(f1, _r[i]);
                    break;
                }
            }
            f2.add(new Oper(r.getRight(), "First", tempValue));
            //f2.add(new Oper(_r.right(), "First",)
        }
    }
    
    private void Step6()
    {
        /*
         * Its only for non terminal
         */
        FDBlist = new ArrayList<Rel>();
        for(Rule r : ruleList)
        {
            String[] _right = r.getRight().split(" ");
            int fdb = 0;
            for(int i=0; i < _right.length; i++)
            {
                String right = _right[i];
                if(right.equals(right.toUpperCase()))
                {
                    if(i + 1 < _right.length)
                    {
                        String nextRight = _right[i+1];
                        FDBlist.add(new Rel(right, nextRight, "FDB"));
                    }
                }
            }
        }
    }
    
    public void Step7()
    {
        DEOlist = new ArrayList<Rel>();
        for(Rule r : ruleList)
        {
            String[] _ur = r.getRight().split(" ");
            //Rel newRule = new Rel(_r[_r.length - 1], r.getLeft(), "DEO");
            String[] _r = reverseStringArray(_ur);
            for(String rString : _r)
            {
                if(rString.equals("null"))
                    break;
                    
                Rel newRel = new Rel(rString, r.getLeft(), "DEO");
                if(!checkRelList(DEOlist, newRel))
                    DEOlist.add(newRel);
                    
                if(isNullable(rString))
                    continue;
                else
                    break;
            }
        }
    }
    
    public void Step8()
    {
        int size = DEOlist.size();
        EOlist = copyRel(DEOlist, "EO");
        
        //Reflect
        ///*
        for(int x = 0 ; x < size; x++)
        {
            for(int y = size-1; y > -1; y--)
            {
                if(DEOlist.get(x).getRight().equals(DEOlist.get(y).getLeft()))
                {
                    Rel _r = new Rel(DEOlist.get(x).getLeft(), DEOlist.get(y).getRight(), "EO");
                    if(!checkRelList(EOlist, _r))
                    {
                        EOlist.add(_r);
                    }
                }
            }
        }
        //*/
        ///*
        //Trans
        for(String s: NT)
        {
            Rel rel = new Rel(s, s, "EO");
            if(!checkRelList(EOlist, rel))
                EOlist.add(rel);
        }
        for(String s : T)
        {
            Rel rel = new Rel(s, s, "EO");
            if(!checkRelList(EOlist, rel))
                EOlist.add(rel);
        }
        //*/
    }
    public void Step9()
    {
        /*
         * Copy EO that end with first FDB
         * Copy BW that start with end FDB
         * EO + FDB + BW
         */
        FBlist = new ArrayList<Rel>();
        ArrayList<Rel> tempList = new ArrayList<Rel>();
        for(Rel _r : EOlist)
        {
            for(Rel _t : FDBlist)
            {
                if(_r.getRight().equals(_t.getLeft()))
                {
                    Rel tmp = new Rel(_r.getLeft(), _t.getRight(), "-");
                    if(!checkRelList(tempList, tmp))
                        tempList.add(tmp);
                }
            }
        }
        
        for(Rel _r : BWlist)
        {
            for(Rel _t : tempList)
            {
                if(_r.getLeft().equals(_t.getRight()))
                {
                    Rel _fb = new Rel(_t.getLeft(), _r.getRight(), "FB");
                    if(!checkRelList(FBlist, _fb))
                        FBlist.add(_fb);
                }
            }
        }
        //printRel(tempList, "Temporary");
    }
    /*
    public void Step9()
    {   
        FBlist = new ArrayList<Rel>();
        ArrayList<Rel> tempList = new ArrayList<Rel>();
        for(Rel fdb : FDBlist)
        {
            Rel tempRel = new Rel("-");
            for(Rel _eo : EOlist)
            {
                if(fdb.getLeft().equals(_eo.getRight()))
                {
                    tempRel.setLeft(_eo.getLeft());
                    tempRel.setRight(fdb.getRight());
                    if(!tempList.contains(tempRel))
                        tempList.add(tempRel);
                }
            }
            for(Rel _bw : BWlist)
            {
                for(Rel tmp : tempList)
                {
                    if(tmp.getRight().equals(_bw.getLeft()))
                    {
                        Rel _fb = new Rel(tmp.getLeft(), _bw.getRight(), "FB");
                        if(!FBlist.contains(_fb))
                            FBlist.add(_fb);
                    }
                }
            }
        }
        printRel(tempList, "Temporary");
    }
    //*/
    public void Step10()
    {
        /*
         * Use EOlist
         */
        FBSlist = new ArrayList<Rel>();
        for(Rel s : EOlist)
        {
            //Find S on the Right of EO and terminal on the left of EO examples A EO S into A FB ↲
            if((s.getRight().equals("S")) && (s.getLeft().equals(s.getLeft().toUpperCase())))
            {
                Rel rel = new Rel(s.getLeft(), "↲", "FB");
                if(!FBSlist.contains(rel))
                    FBSlist.add(rel);
            }
        }
    }
    
    public void Step11()
    {
        f3 = new ArrayList<Oper>();
        ArrayList<String> check = new ArrayList<String>();
        for(Rel s : FBlist)
        {
            //Find NT on the left and terminal on the right
            if(isNullable(s.getLeft()) && s.getRight().equals(s.getRight().toLowerCase()))
            {
                if(!check.contains(s.getLeft()))
                {
                    check.add(s.getLeft());
                    f3.add(new Oper(s.getLeft(), "FOL", s.getRight())); 
                }
                else
                {
                    for(int i=0; i < f3.size(); i++)
                    {
                        if(f3.get(i).getToken().equals(s.getLeft()))
                            f3.get(i).addValue(s.getRight());
                    }
                }
            }
        }
    }
    
    public void Step12()
    {
        f4 = new ArrayList<Oper>();
        int i=1;
        Oper newO = new Oper();
        for(Oper o : f2)
        {
            if(o.getToken().equals("null"))
            {
                for(Oper _o : f3)
                {
                    if(_o.getToken().equals(ruleList.get(i-1).getLeft()))
                    {
                         newO = new Oper(i+"", "SEL", _o.toString());
                    }
                }
            }
            else
            {
                newO = new Oper(i+"" , "SEL", o.toString());
            }
            i++;
            f4.add(newO);
        }
    }
    
    //Aux Function
    private boolean checkRelList(ArrayList<Rel> list, Rel r)
    {
        if(!list.isEmpty())
        {
            for(Rel _r : list)
            {
                if(_r.checkEqual(r))
                    return true;
            }
        }
        return false;
    }
    private boolean isNullable(String t)
    {
        if((!(nullNT.isEmpty())) && nullNT.contains(t))
        {
            return true;
        }
        return false;
    }
    private String findOper(ArrayList<Oper> list, String k)
    {
        for(Oper o : list)
        {
            if(o.getToken().equals(k))
                return o.getValue();
        }
        return "";
    }
    
    private ArrayList<Rel> copyRel(ArrayList<Rel> _rlist, String newR)
    {
        ArrayList<Rel> rlist = new ArrayList<Rel>();
        for(Rel r : _rlist)
        {
            rlist.add(new Rel(r.getLeft(), r.getRight(), newR));
        }
        return rlist;
    }
    
    public void printRel(ArrayList<Rel> list, String tag)
    {
        //System.out.println(tag + " : " + list.get(0).getRel());
        output += "\n" + tag + "\n";
        for(Rel r : list)
        {
            //System.out.println(r.toString());
            output += r.toString() + "\n";
        }
    }
    
    public void printOper(ArrayList<Oper> list, String tag)
    {
        //System.out.println(tag + " : " + list.get(0).getKey());
        output += "\n" + tag + "\n";
        for(Oper o : list)
        {
            //System.out.println(o.toString());
            output += o.toString() + "\n";
        }
    }
    
    public String[] reverseStringArray(String[] inputArray) 
    {
        for (int left = 0, right = inputArray.length - 1; left < right; left++, right--) 
        {
            // swap the values at the left and right indices
            String temp = inputArray[left];
            inputArray[left]  = inputArray[right];
            inputArray[right] = temp;
        }
        return inputArray;
    }
    
    public String getOutput()
    {
        return output;
    }
}
