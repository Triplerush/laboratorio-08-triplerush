package laboratorio8;
import java.util.Vector;

class BNodeGeneric<E> {
	private Vector<E> keys;
	private Vector<BNodeGeneric<E>> children;
	private int minGrado; 
	private int num; 
	private boolean hoja; 
	
	//Constructor
	BNodeGeneric(int grado, boolean hoja ) {
		this.minGrado = grado;
		this.keys = new Vector(2*minGrado-1);
		this.children = new Vector(2*minGrado);
		this.hoja = hoja;
		this.num = 0;
	}
	
	public int search(E key){
    	for (int i = 0; i < keys.size(); i++) {
			if(keys.get(i) == key) {
				return i;
			}
		}
    	return 0;
    }

    public void dividir(int i ,BNodeGeneric<E> node){
    	BNodeGeneric<E> nodeAux = new BNodeGeneric<E>(node.minGrado,node.hoja);
    	nodeAux.num = minGrado - 1;
        for (int j = 0; j < minGrado-1; j++) {
            nodeAux.keys.add(node.keys.get(j+minGrado));
        }
        
        if (!node.hoja){
            for (int j = 0; j < minGrado; j++) 
            	nodeAux.children.add(node.children.get(j+minGrado));
            
        }
        
        node.num = minGrado-1;

        for (int j = num; j >= i+1; j--) {
        	if (j + 1 >= children.size())
	        	children.add(j+1, children.get(j));
			else
	        	children.set(j+1, children.get(j));

        }
        
    	if (i + 1 >= children.size())
            children.add(i+1, nodeAux);
		else
	        children.set(i+1, nodeAux);

        for (int j = num-1;j >= i;j--) {
        	if (j + 1 >= keys.size())
            	keys.add(j+1, keys.get(j));
			else
	        	keys.set(j+1, keys.get(j));
        } 
        
        if (i >= keys.size())
            keys.add(i, node.keys.get(minGrado-1));
		else
	        keys.set(i, node.keys.get(minGrado-1));

        num = num + 1;

    }
    
    public void traverse(){//Metodo para imprimir el arbol segun la guia
        int i;
        for (i = 0; i< num; i++){
            if (!hoja)
                children.get(i).traverse();
            System.out.print(keys.get(i)+" ");
        }

        if (!hoja){
            children.get(i).traverse();
        }
    }
       
    public void llenar(int idx){

        if (idx != 0 && children.get(idx-1).num >= minGrado)
        	prestarseAnterior(idx);
        else if (idx != num && children.get(idx+1).num >= minGrado)
        	prestarsePosterior(idx);
        else{
            if (idx != num)
            	unir(idx);
            else
            	unir(idx-1);
        }
    }

    public void prestarseAnterior(int idx){

        BNodeGeneric<E> hijo = children.get(idx);
        BNodeGeneric<E> hermano = children.get(idx-1);

        for (int i = hijo.num-1; i >= 0; --i)
        	hijo.keys.set(i+1, hijo.keys.get(i));

        if (!hijo.hoja){ 
            for (int i = hijo.num; i >= 0; --i)
            	hijo.children.set(i+1, hijo.children.get(i));
        }

        hijo.keys.add(keys.get(idx-1));
        if (!hijo.hoja) 
        	hijo.children.add(hermano.children.get(hermano.num));

        keys.set(idx-1, hermano.keys.get(hermano.num-1));
        hijo.num += 1;
        hermano.num -= 1;
    }

    public void prestarsePosterior(int idx){

    	BNodeGeneric<E> hijo = children.get(idx);
        BNodeGeneric<E> hermano = children.get(idx+1);

        hijo.keys.set(hijo.num,keys.get(idx));

        if (!hijo.hoja)
        	hijo.children.add(hermano.children.get(0));

        keys.set(idx, hermano.keys.get(0));

        for (int i = 1; i < hermano.num; ++i)
        	hermano.keys.set(i-1, hermano.keys.get(i));

        if (!hermano.hoja){
            for (int i= 1; i <= hermano.num;++i)
            	hermano.children.set(i-1, hermano.children.get(i));
        }
        hijo.num += 1;
        hermano.num -= 1;
    }

    public void unir(int idx){

    	BNodeGeneric<E> hijo = children.get(idx);
        BNodeGeneric<E> hermano = children.get(idx+1);

        hijo.keys.set(minGrado-1,keys.get(idx));

        for (int i =0 ; i< hermano.num; ++i)
        	hijo.keys.set(i+minGrado, hermano.keys.get(i));

        if (!hijo.hoja){
            for (int i = 0;i <= hermano.num; ++i)
            	hijo.children.set(i+minGrado, hermano.children.get(i));
        }

        for (int i = idx+1; i<num; ++i)
        	keys.set(i-1, keys.get(i));
        for (int i = idx+2;i<=num;++i)
        	children.set(i-1, children.get(i));

        hijo.num += hermano.num + 1;
        num--;
    }
    
    public E getPred(int idx){ 

        BNodeGeneric<E> cur = children.get(idx);
        while (!cur.hoja)
            cur = cur.children.get(cur.num);
        return cur.keys.get(cur.num-1);
    }

    public E getSucc(int idx){ 
    	
        BNodeGeneric<E> cur = children.get(idx+1);
        while (!cur.hoja)
            cur = cur.children.get(0);
        return cur.keys.get(0);
    }
	
    public Vector<E> getKeys() {
		return this.keys;
	}
    
    public Vector<BNodeGeneric<E>> getChildren() {
		return this.children;
	}
    
    public boolean isHoja() {
  		return this.hoja;
  	}
    
    public int getMinGrado() {
  		return this.minGrado;
  	}
    
    public int getNumKeys()  {
  		return this.num;
  	}
    
    public void setNumKeys(int a) {
		this.num = a;
	}
    

}
