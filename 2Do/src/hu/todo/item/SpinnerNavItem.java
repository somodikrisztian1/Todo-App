package hu.todo.item;

// a spinner egy elemét reprezentálja
public class SpinnerNavItem {

	String title;
	String size;
	
    public SpinnerNavItem(String title, String size){
        this.title = title;
        this.size = size;
    }
     
    public String getTitle(){
        return this.title;      
    }
	
    public String getSize() {
		return size;
	}
    
}
