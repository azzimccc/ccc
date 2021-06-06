import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
public class CSC569CNG extends JFrame implements Runnable
{
    private static final int WIDTH = 860;
    private static final int HEIGHT = 500;
    
    URL url;
    Icon bg;
    
    //JPanel Object Declaration
    JPanel panel = new JPanel();
    JScrollPane scrollPane;
    JScrollPane _scrollPane;
    JLabel bgGif;
    //RuleTextField textField = new RuleTextField(20);
    JTextArea display;
    JButton generate = new JButton("Generate");
    JButton addButton = new JButton("Add rule");
    JButton clearButton = new JButton("Clear");
    JLabel prompt1 = new JLabel("Please use capital letter for Non terminal e.g. : S → A b ");
    JLabel prompt2 = new JLabel("Please use 0 (zero) for null terminal e.g : A → 0 ");
    JLabel prompt3 = new JLabel("Please use > for arrow");
    
    JLabel tm = new JLabel("@Khairul Azzim 2020");
    ArrayList<RuleTextField> textFields = new ArrayList<RuleTextField>();
    private ArrayList<Rule> rules = new ArrayList<Rule>();
    int xValue = 100;
    int yValue = 60;
    int wValue = 300;
    int hValue = 50;
    
    int ruleCounter = 0;
    //Main Function
    /**
    * CSC569 Cheat Engine
    * @Author: ....
    * @Date: 24 / 10 / 19
    */
    public static void main(String arg[])
    {
        CSC569CNG cng = new CSC569CNG(); 
        Thread cngThread = new Thread(cng);
        cngThread.start();
    }
    @Override
    public void run(){}
    //Default Constructor
    public CSC569CNG()
    {
        //Setup for GUI
        super("CSC569 : LL1 Cheat NG");
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setMinimumSize(new Dimension(WIDTH,HEIGHT));
        setMaximumSize(new Dimension(WIDTH,HEIGHT));    
        setLocation(100,100);//Make Window appears on different place
        setResizable(false);
        panel.setLayout(null);//Null because no Layout help needed
        scrollPane = new JScrollPane(panel);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        display = new JTextArea(16, 58);
        //display.setBounds(550, 0, 300, 480);
        _scrollPane = new JScrollPane(display);
        _scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        _scrollPane.setBounds(550, 0, 280, 480);
        
        //textField.setBounds(xValue, yValue, wValue, hValue);
        addButton.setBounds(450, 260, 100, 30);
        prompt1.setBounds(100, 10, 350, 10);
        prompt2.setBounds(100, 20, 350, 10);
        prompt3.setBounds(100, 30, 350, 10);
        tm.setBounds(0,430, 350, 10); 
        generate.setBounds(450,300,100,30);
        clearButton.setBounds(450,340,100,30);
        addRule();
        panel.add(prompt1);
        panel.add(prompt2);
        panel.add(prompt3);
        panel.add(textFields.get(ruleCounter));
        panel.add(addButton);
        panel.add(generate);
        panel.add(clearButton);
        panel.add(tm);
        //panel.add(display);
        panel.add(_scrollPane);
        //add(panel);
        add(scrollPane);
        
        buttonFunction();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void buttonFunction()
    {
        //Search Button
        generate.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource() == generate)  
                {
                    for(RuleTextField rtf : textFields)
                    {
                        rules.add(new Rule(rtf.getText()));
                    }
                    Steps step = new Steps(rules);
                    
                    display.setText(step.getOutput());
                }
            }
        });
        //Addbutton
        addButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource() == addButton)  
                {
                    ruleCounter++;
                    //System.out.println("Add rule " + ruleCounter);
                    addRule();
                    panel.add(textFields.get(ruleCounter));
                    
                    panel.repaint();
                    scrollPane.repaint();
                }
            }
        });
        //Clear Button
        clearButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource() == clearButton)  
                {
                    for(RuleTextField rtf : textFields)
                    {
                       panel.remove(rtf);
                    }
                    rules.clear();
                    textFields.clear();
                    
                    //System.out.println("Size : " + textFields.size());
                    display.setText("");
                    ruleCounter = 0;
                    //addRule();
                    addRule();
                    panel.add(textFields.get(ruleCounter));
                    panel.repaint();
                    panel.revalidate();
                    scrollPane.repaint();
                }
            }
        });
    }
    
    public void addRule()
    {
        RuleTextField temp = new RuleTextField(20);
        temp.setBounds(xValue, yValue + (50 * ruleCounter), wValue, hValue);
        textFields.add(temp);
    }
}