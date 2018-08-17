public class Variables {

     private String var;
     private String value;


    public Variables(String var, String value) {
        this.var = var;
        this.value = value;
    }

    public String getVar() {
        return var;
    }

    public String getValue() {
        return value;
    }

    public static Variables createVariable(String var, String value){
        return new Variables(var, value);
    }

}
