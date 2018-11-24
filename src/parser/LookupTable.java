package parser;

import java.util.List;

public class LookupTable{

    //TODO: fix like env with enclosing
    private final List<String> ids;
    private final LookupTable lookupTable;

    public LookupTable(List<String> ids, LookupTable superiorLookupTable) {
        this.ids=ids;
        this.lookupTable=superiorLookupTable;
    }

    public boolean contains(String id){
        LookupTable superior=this;
        while(superior!=null) {
            if(superior.ids.stream().anyMatch(e -> e.equals(id))) return true;
            superior=superior.lookupTable;
        }
        return false;
    }

    public int recursionLevel(){
       int levels=0;
       LookupTable superior=this;

       while(superior!=null){
           levels++;
           superior=superior.lookupTable;
       }

       return levels;
    }

    public void viewLookupTable(){
        LookupTable superior=this;
        for(int level=1; superior!=null; level++){
            System.out.println(level+" : "+superior.ids);
            superior=superior.lookupTable;
        }
    }
}
