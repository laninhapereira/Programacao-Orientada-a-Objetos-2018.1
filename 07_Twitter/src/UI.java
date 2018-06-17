import java.util.ArrayList;
import java.util.Scanner;

class Tweet{
	int id_tw;
	String username;
	String sentimento; // é para colocar como se sente no momento, ex: com raiva, fome, etc. Assistia uma serie q tinha uma rede social assim e achei legal colocar 
	String msg; // e aqui o pq 
	ArrayList <String> likes;
	private boolean isLike;
	private boolean isLido;
	ArrayList<String> qtdlikes;
	User u;
	
	public Tweet(int idTw, User u, String sentimento, String msg) {
		this.id_tw = idTw;
		this.u = u;
		this.sentimento = sentimento;
		this.msg = msg;
		this.isLido = false;
		this.isLike = false;
		qtdlikes = new ArrayList<String>();
	}

	public int getId_tw() {
		return id_tw;
	}

	public void setId_tw(int id_tw) {
		this.id_tw = id_tw;
	}
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSentimento() {
		return sentimento;
	}

	public void setTitulo(String sentimento) {
		this.sentimento = sentimento;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<String> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public boolean isLido() {
		return isLido;
	}

	public void setLido(boolean isLido) {
		this.isLido = isLido;
	}

	public ArrayList<String> getQtdlikes() {
		return qtdlikes;
	}

	public void setQtdlikes(ArrayList<String> qtdlikes) {
		this.qtdlikes = qtdlikes;
	}

	public void DarLike(String nome){
		this.qtdlikes.add(username);
	}
	
	public String mostrarLikes() {
		String saida = " ";
		for(int i =0; i < qtdlikes.size(); i++) {
			saida += " " + qtdlikes.get(i) + "\n";
		}
		return saida;
	}
	
	public String toString() {
		return " " + id_tw + ": "+ u + " " + sentimento + " " + msg;
	}
}

class User{
	private String username;
	private Repositorio<User> seguidores;
	private Repositorio<User> seguidos;
	private Repositorio<Tweet> mytweets;
	private Repositorio<Tweet> timeline;
	Tweet msg;
	int countTw = 0;
 	
	public User(String username) {
		this.username = username;
		seguidores = new Repositorio<User> ("seguidores");
		seguidos = new Repositorio<User> ("seguidos");
		mytweets = new Repositorio<Tweet> ("mytweets");
		timeline = new Repositorio<Tweet> ("timeline");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Repositorio<User> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Repositorio<User> seguidores) {
		this.seguidores = seguidores;
	}

	public Repositorio<User> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(Repositorio<User> seguidos) {
		this.seguidos = seguidos;
	}

	public Repositorio<Tweet> getMytweets() {
		return mytweets;
	}

	public void setMytweets(Repositorio<Tweet> mytweets) {
		this.mytweets = mytweets;
	}

	public Repositorio<Tweet> getTimeline() {
		return timeline;
	}

	public void setTimeline(Repositorio<Tweet> timeline) {
		this.timeline = timeline;
	}

	public void Seguir(User user) {
		user.seguidos.add(this.getUsername(), new User(this.getUsername()));
		this.seguidores.add(user.getUsername(), user);
	}
	
	public void Twittar(Tweet tw) {
		this.mytweets.add(" " + tw.getId_tw(), tw);
	}
	
	public void DarLike(int id_tw) {
		for(Tweet s : timeline.getAll()) {
			if(s.getId_tw() == id_tw) {
				if (!s.isLike()) {
					s.setLike(true);
					return;
				}
			}
		}
		throw new RuntimeException("Não é possivel dar like nesse tweet");
	}
	
	public String showSeguidores() {
		String saida = "";
		for (User seguidores : seguidores.getAll())
			saida += seguidores + " ";
		return saida;
	}
	
	public String showSeguidos() {
		String saida = "";
		for (User seguidos : seguidos.getAll())
			saida += seguidos + "";
		return saida;
	}
	public String showMytweets() {
    	String saida = " ";  
    	for(Tweet s : mytweets.getAll()) {
    		saida += " " + s.getId_tw() + s.getUsername() + ": " +s.getSentimento() + ": "+s.getMsg() + "\n"; 
    	}
    	return saida;
    }
	public void addTweet(Tweet t) {
		this.timeline.add(""+t.getId_tw(), t);
	
	}
	
	public String toString() {
		return username + " ";
	}
	
}


class GeradorTwitter{
	Repositorio<Tweet> r_tw;
	
	public GeradorTwitter() {
		r_tw = new Repositorio<Tweet>("r_tw");
	}
	
	public Repositorio<Tweet> getR_tw() {
		return r_tw;
	}
	
	public void setR_tw(Repositorio<Tweet> r_tw) {
		this.r_tw = r_tw;
	}
	
	public void CriarTweet (Tweet tw){
		this.r_tw.add(""+tw.getId_tw(), tw);
	}
	
	public String showTweets() {
		String saida = "";
		for(Tweet tw : r_tw.getAll())
			saida += tw.toString() + "\n";
		return saida;
	}
	
}


class Controller{
	Repositorio<User> usuarios;
	GeradorTwitter ger;
	int contTw = 0;
	int numTw = 1;
	Scanner sca;
	
	public Controller() {
		sca = new Scanner (System.in);
		usuarios = new Repositorio <User> ("username");
		ger = new GeradorTwitter();
	}
	
	public String oracle(String line){
		
        String ui[] = line.split(" ");

        if(ui[0].equals("help"))
            return "addUser, showusers, seguir\n" + 
                   "twitttar, darlike, showlikes, seguidores, seguidos\n" + 
                   "timeline, mytweets";
        else if(ui[0].equals("adduser"))
        	usuarios.add(ui[1], new User(ui[1]));
        else if(ui[0].equals("showusers")) {
        	String saida = "";
        	for (User user : usuarios.getAll()) 
        		saida += user.toString() + "Segudiores " + user.showSeguidores() + "Seguidos " +user.showSeguidos() + "\n";
        	return saida;	
        }
        else if(ui[0].equals("seguir")) {
        	usuarios.get(ui[2]).Seguir(usuarios.get(ui[1]));
        }
        else if(ui[0].equals("twittar")) {
        	String texto = "";
 	       	for(int i = 3 ; i<ui.length; i++)
 	       		texto += ui[i] + " ";
 	       
 	       	for(User s : usuarios.get(ui[1]).getSeguidores().getAll()) 
 	       		s.addTweet(new Tweet(numTw, usuarios.get(ui[1]), ui[2], texto));
            
 	       		usuarios.get(ui[1]).Twittar(new Tweet(numTw, usuarios.get(ui[1]), ui[2], texto));      
 	       		ger.CriarTweet(new Tweet(numTw, usuarios.get(ui[1]), ui[2],texto));  
            numTw++;
        }
        else if(ui[0].equals("darlike")) {
        	usuarios.get(ui[1]).DarLike(Integer.parseInt(ui[2]));
			ger.getR_tw().get(ui[2]).DarLike(usuarios.get(ui[1]).getUsername());
        }
        
        else if(ui[0].equals("showlikes")) {
			System.out.println(" " + ger.getR_tw().get(ui[1]).mostrarLikes());
		}
        
        else if(ui[0].equals("seguidores")) {
        	String saida = "";
        	for(User user : usuarios.getAll())
        		saida += user.getUsername() + "\n";
        	return saida;
        }
//
        else if(ui[0].equals("seguidos")) {
        	System.out.println(usuarios.get(ui[1]).showSeguidos());
        }
        
        else if(ui[0].equals("timeline")) {
        	System.out.println(ger.showTweets());
        }
        else if(ui[0].equals("mytweets")) {
        	System.out.println(usuarios.get(ui[1]).showMytweets());
        }
        else
            return "comando invalido";
        return "done";
    }
}

public class UI {
    //cria um objeto scan para ler strings do teclado
    static Scanner scan = new Scanner(System.in);
    
    //aplica um tab e retorna o texto tabulado com dois espaços
    static private String tab(String text){
        return "  " + String.join("\n  ", text.split("\n"));
    }
    
    public static void main(String[] args) {
        Controller cont = new Controller();
        System.out.println("Digite um comando ou help:");
        while(true){
            String line = scan.nextLine();
            try {
                //se não der problema, faz a pergunta e mostra a resposta
                System.out.println(tab(cont.oracle(line)));
            }catch(Exception e) {
                //se der problema, mostre o erro que deu
                System.out.println(tab(e.getMessage()));
            }
        }
    }
}
