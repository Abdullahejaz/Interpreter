import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

/*@author Abdullah Ejaz*/



public class InterpreterP {

    static String lex;
    static int col =0;
    static InputStreamReader reader;
    static Stack<String> items = new Stack<String>();
    public static ArrayList<Variables> variable;
    static String var;
    static String token = "";
    static String variabll = "";



    public InterpreterP() {
        this.variable = new ArrayList<Variables>();
    }



    public static void addNewVariable(){

        variable.add(0, Variables.createVariable("a", "T"));
        variable.add(1, Variables.createVariable("b", "F"));
        variable.add(2, Variables.createVariable("c", "T"));
        variable.add(3, Variables.createVariable("d", "F"));
        variable.add(4, Variables.createVariable("e", "T"));
    }



    public void printVariables(){
        System.out.println("Table of Variables");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i=0; i< this.variable.size(); i++){
            System.out.println((i+1) + ". " + this.variable.get(i).getVar() + " -> " + this.variable.get(i).getValue());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    private static String findVariable(String var){
        for (int i =0; i < variable.size(); i++) {
            Variables vari = variable.get(i);
            if (vari.getVar().equals(var)){
                return vari.getValue();
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return "The variable does not exist in the table.";
    }



    public static void main(String[] args){

        InterpreterP interpreter = new InterpreterP();
        InterpreterP.addNewVariable();
        interpreter.printVariables();


            try{
            reader = new FileReader("./src/main/resources/input_file.txt");
            lex = getTokens();
            if (P())
            {
                System.out.println("Last element on the stack is : " + items);
                if(items.pop() == "T")

                System.out.println("Valid Boolean Expression");
                else
                    System.out.println("Invalid Boolean Expression");
            }
            else{
                System.out.println("Last element on the stack is : " + items);
                if(items.pop() == "T")
                    System.out.println("Valid Boolean Expression");
                else
                System.out.println("Invalid Boolean Expression");

            }

        }
        catch (FileNotFoundException ex){
            System.out.println(ex);
        }
    }

    private static boolean P(){

        if (D()){

            if (B()){
                return true;
            }
            else{
                return false;
            }
        }
        else{return false;}
    }

    private static boolean D(){

        if (lex.equals("#"))
        {
            lex = getTokens();
            if (lex.equals(token))
            {
                String value = findVariable(token);

                if (value == "T"){
                    items.push("T");
                    lex = getTokens();
                }
                else if (value == "F"){
                    items.push("F");
                    lex = getTokens();
                }
                else{
                    System.out.println("Invalid variable. Variable not found in the list");
                }

                lex = getTokens();
                if (lex.equals(":"))
                {
                    lex = getTokens();
                    if (lex.equals("="))
                    {
                        lex = getTokens();
                        if(V())
                        {
                            lex = getTokens();

                            if(lex.equals(";"))
                            {
                                lex = getTokens();
                                if(D())
                                {
                                    return true;
                                }
                                else
                                {
                                    return false;
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("~") || lex.equals("T") || lex.equals("F") || lex.equals("a") || lex.equals("("))
        {
            return true;
        }
        else
        {

            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean V()
    {

        if (lex.equals("T"))
        {
            variabll = "T";
            return true;
        }
        else if (lex.equals("F"))
        {
            //items.push("F");
            //lex = getTokens();
            variabll = "F";
            return true;
        }
        else
        {
            System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }


    private static boolean B()
    {
        if (IT())
        {
            if (lex.equals("."))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean IT()
    {

        if (OT())
        {
            if (IT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean IT_Tail()
    {

        String t1, t2;
        if (lex.equals("->"))
        {
            lex = getTokens();
            if (OT())
            {
                t2 = items.pop();
                t1 = items.pop();
                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("T") && t2.equals("F"))
                    items.push("F");
                else
                    items.push("T");

                if (IT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean OT()
    {

        if (AT())
        {
            if (OT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean OT_Tail()
    {

        String t1, t2;
        if (lex.equals("v"))
        {
            lex = getTokens();
            if (AT())
            {
                t2 = items.pop();
                t1 = items.pop();

                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("F") && t2.equals("F"))
                    items.push("F");
                else
                    items.push("T");

                if (OT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("->") || lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean AT()
    {

        if (L())
        {
            if (AT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean AT_Tail()
    {

        String t1, t2;
        if (lex.equals("^"))
        {
            lex = getTokens();
            if (L())
            {
                t2 = items.pop();
                t1 = items.pop();

                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("T") && t2.equals("T"))
                    items.push("T");
                else
                    items.push("F");
                if (AT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("v") || lex.equals("->") || lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean L()
    {

        if (lex.equals("~"))
        {
            lex = getTokens();
            if (L())
            {
                String token = items.pop();

                if (token.equals("T"))
                    items.push("F");
                else
                    items.push
                            ("T");
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (A())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean A()
    {

        if (lex.equals("T"))
        {
            items.push("T");
            lex = getTokens();
            return true;
        }
        else if (lex.equals("F"))
        {
            items.push("F");
            lex = getTokens();
            return true;
        }
        else if (lex.equals("("))
        {
            lex = getTokens();
            if (IT())
            {
                if (lex.equals(")"))
                {
                    lex = getTokens();
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static String getTokens()
    {

        int i = 0;

        char readChar;

        try
        {

            while ((i = reader.read()) != -1)
            {
                readChar = (char)i;
                ++col;
                if (readChar == '-')
                {
                    token = "-";
                    i = reader.read();
                    ++col;
                    readChar = (char)i;
                    token = token + readChar;
                    break;
                }
                else if (readChar == ' ')
                {
                    continue;
                }
                else
                {
                    token = "" + readChar;
                    break;

                }

            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }


        return token;
    }


}


