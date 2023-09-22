# Testo esercizio

L'obiettivo dell'esercizio è quello di realizzare una classe Java che
permetta la gestione (molto semplificata) di una catena di pizzerie.

### La classe Pizzeria

L'esercizio consiste nel creare una classe `Pizzeria` che rappresenta un singolo locale. Ogni locale è caratterizzato da un *nome* (assegnato attraverso il costruttore, si veda l'esempio in `main`), un *incasso* che rappresenta l'incasso totale (inizialmente 0) e da un contatore del numero di pizze vendute (inizialmente zero).

Inoltre dentro la classe deve essere definita una variabile

```java
static HashMap<String, List<String>> menu;
```
che rappresenta il menu delle pizzerie. E' una variabile di 
classe in quanto tutte le pizzerie hanno lo stesso menu. Il menu consiste in 
una mappa che ad ogni nome di pizza associa la
lista dei suoi ingredienti. Quindi le chiavi sono stringhe (i nomi delle
pizze) e i valori sono liste di stringhe (gli ingredienti).

La variabile `menu` deve essere inizializzata (ovviamente una volta sola)
leggendo i dati dal file di testo `pizze.txt`. Ogni riga di tale file contiene
una voce del menu: la prima parola è il nome della pizza seguito dal simbolo `:`, le parole successive sono gli ingredienti. Ad esempio:

```
  Margherita:pomodoro mozzarella basilico
  Vulcano:pomodoro mozzarella salame melanzane peperoni
  etc etc 
```

L'inizializzazione del menu deve essere svolta automaticamente dalla classe utilizzando uno [static initializer](https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html) (non lo abbiamo fatto a lezione ma è molto semplice da usare). *Non cambiate il nome e il tipo di questa variabile altrimenti i test non funzionano.*


### Metodi della classe

Oltre alle variabili indicate sopra (ed eventualmente altre che riterrete utili), la classe `Pizzeria` deve contenere i seguenti metodi (ed eventualmente altri che ritenete utili):


```java
public String toString();
```
Questo metodo deve restituire una stringa che indica il nome della pizzeria, il numero di pizze vendute e l'incasso totale di una pizzeria. Ad esempio se la pizzeria `p` di nome 'Funicolì' ha venduto 7 pizze e incassato 59 euro `p.toString()` deve restituire la stringa  `Funicolì: Vendute 7 Incasso 59`. In fase di debugging potrebbe essere comodo per voi aggiugnere altre informazioni alla stringa ma al momento della consegna eliminatele.

---

```java
void aggiornaScorte(String nomeIng, int q);
```
Questo metodo serve per aggiungere alle scorte di ingredienti della pizzera
`q` unità dell'ingrediente `nomeIng`. Ad esempio, se la pizzeria `napoli` possiede 3 unità di pomodoro, dopo l'esecuzione dell'istruzione 
```java
napoli.aggiornaScorte("pomodoro", 10);
```
la pizzeria `napoli` conterrà 13 unità di pomodoro. (Nota: evidentemente ogni locale deve tenere traccia in una qualche variabile d'istanza del numero di unità di ogni ingrediente). Si noti che qualsiasi stringa può rappresentare un ingrediente non solamente quelle che appaiono nel menu (ad esempio `xy99z` è perfettamente accettabile come ingrediente).  


---

```java
void ordinaPizza(String nome) throws IngredienteMancante, PizzaSconosciuta
```	
Questo metodo ordina la pizza `nome` alla pizzeria `this`. Ad esempio l'istruzione
```java
napoli.ordinaPizza("Margherita")
``` 
esegue l'ordine di una pizza Margherita alla pizzeria `napoli`.
I possibili esiti di questo metodo sono i seguenti:
  1. se il nome della pizza non è nel menu viene lanciata un'eccezione
     `PizzaSconosciuta`,
  2. se il nome della pizza è nel menu si deve controllare che nella pizzeria esista almeno
     una unità di ognuno degli ingredienti della pizza. Ad esempio se viene
     ordinata una Margherita nelle scorte ci devono essere almeno una unità di 
     pomodoro, una di mozzarella e una di basilico. Se questo non si verifica
     viene lanciata un'eccezione `IngredienteMancante`, 
  3. se gli ingredienti sono disponibili l'ordine ha successo. La
     disponibilità di ognuno degli ingredienti usati dalla pizza deve essere 
     diminuita di uno, il numero di pizze vendute deve essere aumentato 
     di uno, e l'incasso della pizzeria deve essere aumentato del prezzo della pizza che per semplicità si considera pari a 5 euro più un euro per ogni ingrediente (quindi la Margherita costa 8, la
     pizza Vulcano costa 10, etc.)


### Testing

Dopo aver creato la classe `Pizzeria` dovete eseguire alcuni test predefiniti (li trovate sulla desrta sotto Tools->Tests) che sostanzialmente verificano che il menu venga letto correttamente e che il metodo `ordina()` lanci correttamente le eccezioni `PizzaSconosciuta` e `IngredienteMancante` (che ricordo dovete definire voi). Notate che i risultati dei test vengono mostrati nella Console. 

 
Dopo che i test sono stati tutti superati, si completi il metodo `main` della classe `Pizzeria` in modo che:
  1. Vengano create 5 pizzerie
  2. Ad ogni pizzera viene fornita una dotazione iniziale di ingredienti
  3. Ad ogni pizzeria vengano ordinate 16 pizze scelte a caso
  4. Viene stampato un rapporto contenente il numero di ordini che hanno avuto successo, il numero di ordini falliti per `PizzaSconosciuta`, e il numero di ordini falliti per `IngredienteMancante`  
  5. Viene stampato l'elenco dei locali, ordinato per numero di pizze vendute decrescente, e a parità di pizze vendute per incasso decrescente. 

Il metodo `main` contiene già il codice dei punti 1 e 2 e la generazione degli ordini per il punto 3; fa parte dell'esercizio completare i punti da 3 a 5. Se non viene modificata la generazione degli ordini (e l'inizializzazione dei numeri casuali), il metodo `main` correttamente completato dovrebbe produre un output simile al seguente:
```
--- Stampa locali appena creati
Funicolì: Vendute 0 Incasso 0
Fornaccio: Vendute 0 Incasso 0
Bella Napoli: Vendute 0 Incasso 0
Positano: Vendute 0 Incasso 0
Montino: Vendute 0 Incasso 0
-------------------
--- Aggiungo scorte di ingredienti
--- Eseguo ordini random
--- Report ordinazioni
Ordini completi: 53
Sconosciute: 12
Ingrediente mancante: 15
--- Stampa locali ordinati per vendite e incasso
Bella Napoli: Vendute 12 Incasso 102
Positano: Vendute 12 Incasso 99
Fornaccio: Vendute 10 Incasso 86
Montino: Vendute 10 Incasso 85
Funicolì: Vendute 9 Incasso 82
-------------------
```

Se invece si fanno ordinare 26 pizze ad ogni locale modificando il `16` in `26` nella linea 
```java
for(int i=0;i<16;i++) {
``` 
del `main`, l'output corrispondente dovrebbe essere:
```
--- Stampa locali appena creati
Funicolì: Vendute 0 Incasso 0
Fornaccio: Vendute 0 Incasso 0
Bella Napoli: Vendute 0 Incasso 0
Positano: Vendute 0 Incasso 0
Montino: Vendute 0 Incasso 0
-------------------
--- Aggiungo scorte di ingredienti
--- Eseguo ordini random
--- Report ordinazioni
Ordini completi: 67
Sconosciute: 17
Ingrediente mancante: 46
--- Stampa locali ordinati per vendite e incasso
Bella Napoli: Vendute 15 Incasso 127
Fornaccio: Vendute 14 Incasso 121
Montino: Vendute 14 Incasso 116
Funicolì: Vendute 12 Incasso 105
Positano: Vendute 12 Incasso 99
-------------------
```
(notiamo che essendoci più ordini, molti di essi falliscono per un ingrediente mancante).

Per ottenere la sufficienza il vostro esercizio deve superare tutti i test e produrre un output come quello ripostato sopra (attenzione all'ordinamento dei locali), ma sarà testato anche su altre istanze e verrà valutata anche l'organizzazione del codice. Per la correzione verranno utilizzati tool automatici per la rilevazione di plagiarismo nel codice. 
