import java.util.*;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
Classe che descrive una pizzeria, e le sue vendite.
*/
public class Pizzeria implements Comparable<Pizzeria>{

	// DICHIARAZIONE DELLE VARIABILI
	//------------------------------
	
	String nome;
	Integer incasso;
	Integer contatore;

	//Mappa ad ogni nome di pizza la lista dei suoi ingredienti
	static HashMap<String, List<String>> menu = new HashMap();
	static {
		//legge da pizze.txt e inizializza la hashmap
		String filename = "pizze.txt";
		String pizza;
		//linescanner prende una riga alla volta
		try(Scanner linescanner = new Scanner(new FileReader(filename))) {
			while(linescanner.hasNextLine()) {
				//una linea significa una pizza con i suoi ingredienti
				String line = linescanner.nextLine();
				//scan invece spezzetta la singola riga
				Scanner scan = new Scanner(line);
				scan.useDelimiter(":");
				pizza = scan.next();
				scan.skip(":");
				scan.useDelimiter(" ");
				ArrayList<String> ingredientiPizza = new ArrayList<String>();
				while(scan.hasNext()){
					String ing = scan.next();
					ingredientiPizza.add(ing);
				}
				menu.put(pizza, ingredientiPizza);
			}
		}
		catch(FileNotFoundException e) {
      System.err.println("File "+filename+" non trovato.");
    	}
		catch(InputMismatchException e) {
			System.err.println("Errore dati nel file");
		}
		catch(NoSuchElementException e) {
			System.out.println("--- Termina lettura file");
		}
	}

	//struttura dati per contenere gli ingredienti e la loro
	//disponibilità
	HashMap<String, Integer> ingredienti;
	
	// I METODI
	//-----------------

	/**costruttore
	*/
	Pizzeria(String nome){
		this.nome = nome;
		this.incasso = 0;
		this.contatore = 0;
		this.ingredienti = new HashMap();
	}

	/**
	Restituisce una stringa che contiene il nome
	della pizzeria, il numero di pizze vendute e l'incasso totale.
	*/
	public String toString(){
		return String.format("%s %d %d", this.nome, this.contatore, this.incasso);
	}

	/** Comparatore: serve ad ordinare le pizze per vendite decrescenti, poi per incassi decrescenti.
	*/
	public int compareTo(Pizzeria b){
		if (this.contatore < b.contatore) return 1;
		else if(this.contatore > b.contatore) return -1;
		else if(this.incasso < b.incasso) return 1;
		else return -1;
	}

	/**
	Aggiorna la lista degli ingredienti, aumentando della quantità q la disponibilità di uno specifico ingrediente.

	La struttura dati ingredienti è una mappa che contiene il nome di un ingrediente e la sua disponibilità.

	@param nomeIng nome dell'ingrediente di cui si vuole aumentare la quantità.
	@param q indica la quantità di ingrediente acquisita.
	*/
	public void aggiornaScorte(String nomeIng, int q){
		if(q < 0){ 
			return;
		}
		if(ingredienti.containsKey(nomeIng)){
			Integer nuovoValore = ingredienti.get(nomeIng) + q;
			ingredienti.replace(nomeIng, nuovoValore);
		}
		else{
			ingredienti.put(nomeIng, q);
		}
	}

	/**
	Ordina una pizza e aggiorna lo stato della pizzeria.

	Se la pizza esiste nel menù, e la pizzeria ha tutti gli ingredienti necessari per prepararla, si diminuisce di uno la disponibilità di tutti gli ingredienti utilizzati, si calcola l'incasso e si aumenta il contatore delle vendite.
	
	@param nome è il nome della pizza che il cliente vuole ordinare
	@throws IngredienteMancante se manca almeno un ingrediente per preparare la pizza
	@throws PizzaSconosciuta se la pizza non è presente nel menù
	(il menù è una hashmap che usa come chiavi i nomi delle pizze).
	*/
	public void ordinaPizza(String nome) throws IngredienteMancante, PizzaSconosciuta {
		//Esito 1: il nome della pizza non è nel menù
		if(menu.isEmpty() || menu.containsKey(nome) == false){
			throw new PizzaSconosciuta(nome);
		}
		//controlliamo che nella pizzeria esista almeno una unità di ognuno degli ingredienti della pizza.
		List<String> necessari = menu.get(nome);
		for(String ingrediente : necessari){
			if(! ingredienti.containsKey(ingrediente)){
				//Esito 2: non ci sono tutti gli ingredienti
				throw new IngredienteMancante(ingrediente);
			}
		}
		//Esito 3: L'ordine ha successo
		// la disponibilità di ognuno degli ingredienti usati
		// viene diminuita di uno.
		for(String ingrediente : necessari){
			Integer disponibilita = ingredienti.get(ingrediente);
			if(disponibilita <= 1){
				ingredienti.remove(ingrediente);
			}
			else{
				ingredienti.replace(ingrediente, disponibilita-1);
			}
		}
		//si aggiorna l'incasso e il contatore.
		this.incasso = this.incasso + 5 + necessari.size();
		this.contatore++;
	}

	/**
	Crea un array di pizzerie, aggiunge scorte ingredienti, esegue ordini random, genera report ordinazioni, stampa locali ordinati per vendite e incasso.
	@param args
  */
	public static void main(String[] args) 
	{
    // inzializzazione numeri casuali
		Random r = new Random(333);   // inizializza numeri casuali con seed 333
				
		// ==== 1.  creazione locali  NON MODIFICARE
		String nomi[] = {"Funicolì", "Fornaccio", "Bella Napoli", "Positano", "Montino"};
		List<Pizzeria> locali = new  ArrayList<Pizzeria>();
		for(String n : nomi) 
			locali.add(new Pizzeria(n));
		// stampa elenco locali
		System.out.println("--- Stampa locali appena creati");
		for(Pizzeria loc : locali)
			System.out.println(loc);
		System.out.println("-------------------");

		
		// ==== 2.  inizializza scorte  NON MODIFICARE
		System.out.println("--- Aggiungo scorte di ingredienti");
		String inglist[] = {"pomodoro","cipolla","melanzane","salame","funghi","mozzarella",
												"peperoni", "carciofini","basilico", "prosciutto", "burrata"};
		for(Pizzeria loc: locali) {
			for(String ing: inglist) 
				// per ogni ingrediente aggiunge tra 5 e 10 unità ad ogni locale
			  loc.aggiornaScorte(ing, 5 +  r.nextInt(6));
			loc.aggiornaScorte("pomodoro", 7);    // 7 unità extra di pomodoro e mozzarella
		  loc.aggiornaScorte("mozzarella", 7);
		}

		
		// ==== 3.  ordina 16 pizze da ogni pizzeria 
		System.out.println("--- Eseguo ordini random");
		// nota: la Bismark non è in menù e non ci sono gli ingredienti per la Burrata
		// questo garantisce il lancio delle eccezioni PizzaSconosciuta e IngredienteMancante
		String pizze[] = {"Margherita", "Diavola", "Melanzane", "Quattro Stagioni", "Burrata",
		                "Prosciutto e Funghi", "Prosciutto", "Bismark", "Vulcano", "Pugliese"};

		
		// esegui 16 ordini per ogni pizzeria
		Integer ordiniriusciti = 0;
		Integer pizzesconosciute = 0;
		Integer ingredientemancante = 0;
		for(int i=0;i<26;i++) {
			for(Pizzeria loc: locali) {
				// sceglie un nome di pizza a caso nell'array pizze[]
				String nomepizza = pizze[r.nextInt(pizze.length)];    // non modificare questa riga
				//parte modificata
				try{
					loc.ordinaPizza(nomepizza);
					ordiniriusciti++;
				}
				catch(PizzaSconosciuta e){
				  //System.err.println(e);
					pizzesconosciute++;
				}
				catch(IngredienteMancante e){
					//System.err.println(e);
					ingredientemancante++;
				}
			}
		}

		// ==== 4. stampa esito ordinazioni
		System.out.println("--- Report ordinazioni");
		System.out.println("Ordini completi: "+ ordiniriusciti);
		System.out.println("Sconosciute: " + pizzesconosciute);
		System.out.println("Ingrediente mancante: " + ingredientemancante);
		
		// ==== 5. stampa locali ordinati per incasso
		//ordino e stampo
		System.out.println("--- Stampa locali ordinati per vendite e incasso");
		Collections.sort(locali, null);  
		for(Pizzeria loc : locali)
			System.out.println(loc.nome + ": Vendute "+ loc.contatore + " Incasso " + loc.incasso);

		
		System.out.println("-------------------");
	}
	
}

class IngredienteMancante extends RuntimeException {
    IngredienteMancante(String e) {super(e);}
}		

class PizzaSconosciuta extends RuntimeException {
    PizzaSconosciuta(String e) {super(e);}
}