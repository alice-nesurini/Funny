package parser;

import java.util.List;

// TODO: complete refactoring
public class Scope {

    //TODO: fix like env with enclosing
    private final List<String> ids;
    private final Scope scope;

    public Scope(List<String> ids, Scope superiorScope) {
        this.ids=ids;
        this.scope = superiorScope;
    }

    public boolean contains(String id){
        Scope superior=this;
        while(superior!=null) {
            if(superior.ids.stream().anyMatch(e -> e.equals(id))) return true;
            superior=superior.scope;
        }
        return false;
    }

    public int recursionLevel(){
       int levels=0;
       Scope superior=this;

       while(superior!=null){
           levels++;
           superior=superior.scope;
       }

       return levels;
    }

    public void viewLookupTable(){
        Scope superior=this;
        for(int level=1; superior!=null; level++){
            System.out.println(level+" : "+superior.ids);
            superior=superior.scope;
        }
    }
}
