package laboratorio8;

public class TestB {
	public static void main(String[] args) {
    	BTreeGeneric<Integer> enteros = new BTreeGeneric<Integer>(2); //Insertando el grado minimo del arbol
    	int[] numValues = {5,49,69,87,1,100};
    	
    	
    	
        System.out.println("ARBOL B GENERICO");
        System.out.println("1.- Arbol de enteros");
        System.out.print("\tValores agregados: ");
        for (int i : numValues) {
        	enteros.add(i);  
	        System.out.print(i +" ");
		}
    	
    	System.out.println("\n\tNúmero de elementos del arbol: " + enteros.size());

        System.out.print("\tEstructura 'trasversal' del arbol: ");
        enteros.traverse();
        System.out.println("\n");
        System.out.println("\tBusqueda");
        for (int i = 0; i < 6; i++) {
			System.out.println("\tElemento " + i +" buscado en el arbol: " +enteros.search(i));
		}
        
        System.out.println("\n");
        System.out.println("\tEliminacion");
		System.out.print("\tElemento " + 	enteros.remove(5) +" eliminado: ");
		enteros.traverse();
        System.out.print("\n\t");
        enteros.remove(13);
        System.out.print("\t");
        enteros.traverse();
		System.out.print("\n\tElemento " + 	enteros.remove(69) +" eliminado: ");
		enteros.traverse();

		System.out.println("\n");
	    System.out.println("\tBorrar todo el arbol");
	    enteros.clear();
	    System.out.println("\tRaiz del arbol: " + enteros.get());
        System.out.println();

		BTreeGeneric<String> cadenas = new BTreeGeneric<String>(2); //Insertando el grado minimo del arbol
    	String[] stringValues = {"a","c","e","b","d"};
    	String[] busqueda = {"d","i","o","e","z"};

    	
    	System.out.println("2.- Arbol de cadenas");
        System.out.print("\tValores agregados: ");
        for (String i : stringValues) {
        	cadenas.add(i);  
	        System.out.print(i +" ");
		}
    	
        System.out.println("\n\tNúmero de elementos del arbol: " + cadenas.size());
        System.out.print("\tEstructura 'trasversal' del arbol: ");
        cadenas.traverse();
        System.out.println("\n");
        System.out.println("\tBusqueda");
        for (String i : busqueda) {
			System.out.println("\tElemento " + i +" buscado en el arbol: " +cadenas.search(i));
		}
        
        System.out.println("\n");
        System.out.println("\tEliminacion");
		System.out.print("\tElemento " + cadenas.remove("d") +" eliminado: ");
		cadenas.traverse();
        System.out.print("\n\t");
        cadenas.remove("p");
        System.out.print("\t");
        cadenas.traverse();
		System.out.print("\n\tElemento " + 	cadenas.remove("a") +" eliminado: ");
		cadenas.traverse();
		
		System.out.println("\n");
	    System.out.println("\tBorrar todo el arbol");
	    cadenas.clear();
	    System.out.println("\tRaiz del arbol: " + cadenas.get());


    }
}
