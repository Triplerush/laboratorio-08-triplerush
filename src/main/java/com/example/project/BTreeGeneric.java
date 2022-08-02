package laboratorio8;

import java.util.Vector;

public class BTreeGeneric<E extends Comparable<? super E>> {
	private BNodeGeneric<E> root;
	private int MinDeg;
	private int size;// Número de elementos del arbol

	// Constructor
	public BTreeGeneric(int deg) {
		this.root = null;
		this.MinDeg = deg;
		this.size = 0;
	}

	public void traverse() {//Metodo para imprimir el arbol segun la guia
		if (root != null) 
			root.traverse();
	}

	public BNodeGeneric<E> get() {
		return root;
	}
	
	public int size() {
		return this.size;
	}

	//Metodos para insertar el valor en el arbol
	public boolean add(E value) {
		if (search(value)) {
			return false;// No se pudo agregar el valor repetido
		} else {
			insert(value);
			size++;
			return true;// Se agrego el valor
		}
	}
	
	private void insert(E key) {
		if (root == null) {
			root = new BNodeGeneric<E>(MinDeg, true);
			root.getKeys().add(key);
			root.setNumKeys(1);
		} else {
			if (root.getNumKeys() == 2 * MinDeg - 1) {
				BNodeGeneric<E> node = new BNodeGeneric<E>(MinDeg, false);
				node.getChildren().add(root);
				node.dividir(0, root);

				int i = 0;
				if (node.getKeys().get(0).compareTo(key) < 0)
					i++;
				insertNotFull(key, node.getChildren().get(i));

				root = node;
			} else
				insertNotFull(key, root);
		}
	}

	private void insertNotFull(E key, BNodeGeneric<E> node) {
		int i = node.getNumKeys() - 1;

		if (node.isHoja()) {
			while (i >= 0 && node.getKeys().get(i).compareTo(key) > 0) {
				node.getKeys().add(i + 1, node.getKeys().get(i));
				i--;
			}
			if (i + 1 >= node.getKeys().size())
				node.getKeys().add(i + 1, key);
			else
				node.getKeys().set(i + 1, key);

			node.setNumKeys(node.getNumKeys() + 1);

		} else {

			while (i >= 0 && node.getKeys().get(i).compareTo(key) > 0)
				i--;
			
			if (node.getChildren().get(i + 1).getNumKeys() == 2 * node.getMinGrado() - 1) {
				node.dividir(i + 1, node.getChildren().get(i + 1));

				if (node.getKeys().get(i + 1).compareTo(key) < 0)
					i++;
			}
			insertNotFull(key, node.getChildren().get(i + 1));
		}
	}
	
	// Metodos para buscar si exite el valor en el arbol
	public boolean search(E value) {
		if (this.root != null) {
			return search(value, root);
		}
		return false;
	}

	private boolean search(E data, BNodeGeneric<E> node) {
		Vector<E> keys = node.getKeys();
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).compareTo(data) == 0) {
				return true;
			} else if (keys.get(i).compareTo(data) > 0) {
				if (node.isHoja())
					return false;
				else
					return search(data, node.getChildren().get(i));
			}
		}

		if (node.isHoja())
			return false;
		else
			return search(data, node.getChildren().get(keys.size()));
	}

	//Metodos para remover el elemento del arbol
	public E remove(E value) {
		if (root == null) {// El arbol esta vacio
			return null;
		}

		remove(value,root);
		if (root.getNumKeys() == 0) { 
			if (root.isHoja())
				root = null;
			else
				root = root.getChildren().get(0);
		}
		return value;
	}
	
	public void remove(E value,BNodeGeneric<E> node){
        int idx = node.search(value);
        if (idx < node.getNumKeys() && node.getKeys().get(idx) == value){ 
            if (node.isHoja()) 
                removeDeHoja(idx,node);
            else 
            	removeNoHoja(idx,node);
        }
        else{
            if(node.isHoja()){ 
                System.out.print("El valor "+value+" no se encuentra en el arbol");
                return;
            }
            
            if(node.getChildren().get(idx).getNumKeys() < node.getMinGrado())
                node.llenar(idx);
            
            if(value.compareTo(node.getKeys().get(node.getNumKeys()-1))>0)
               remove(value, node.getChildren().get(idx+1));
            else
               remove(value, node.getChildren().get(idx));
        }
    }
	
    public void removeNoHoja(int idx,BNodeGeneric<E> node){

        E value = node.getKeys().get(idx);

        if (node.getChildren().get(idx).getNumKeys() >= node.getMinGrado()){
            E pred = node.getPred(idx);
            node.getKeys().set(idx, pred);
            remove(pred,node.getChildren().get(idx));
        }
        
        else if (node.getChildren().get(idx+1).getNumKeys() >= node.getMinGrado()){
            E succ = node.getSucc(idx);
            node.getKeys().set(idx, succ);
            remove(succ,node.getChildren().get(idx+1));
        }
        
        else{
            node.unir(idx);
            remove(value,node.getChildren().get(idx));
        }
    }
    
    public void removeDeHoja(int idx,BNodeGeneric<E> node){
        for (int i = idx +1;i < node.getNumKeys();++i)
			node.getKeys().set(i-1, node.getKeys().get(i));
        node.setNumKeys(node.getNumKeys()-1);
        size--;
    }
    
    //Metodo que "elimina" el arbol
	public void clear() {
		this.root = null;
		this.size = 0;
	}
}