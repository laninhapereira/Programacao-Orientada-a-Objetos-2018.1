import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Anotacao{
	String titulo;
	String texto;
	public Anotacao(String titulo, String texto) {
		this.titulo = titulo;
		this.texto = texto;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String toString() {
		return titulo + ":" + texto;
	}
}

class User implements Comparable<User>{
	private String password;
	private String username;
	public Repositorio <Anotacao> anotacao;
	
	public Repositorio<Anotacao> getAnotacao() {
		return anotacao;
	}
	public void setAnotacao(Repositorio<Anotacao> anotacao) {
		this.anotacao = anotacao;
	}
	public User(String username, String password) {
		this.password = password;
		this.username = username;
		anotacao = new Repositorio<Anotacao> (username);
		
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
	public String getUsername() {
		return username;
	}
	public String toString() {
		return username + " " + password;
	}
	public int compareTo(User o) {
		return this.username.compareTo(o.username);
	}
}

class GerenciadorDeLogin{
	private Repositorio<User> usuarios;
	private User user;
	
	public GerenciadorDeLogin(Repositorio<User> usuarios) {
		this.usuarios = usuarios;
		user = null;
	}
	
	void login(String username, String senha){
		if(user != null)
			throw new RuntimeException("fail: ja existe alguem logado");
		if(!usuarios.get(username).matchPassword(senha))
			throw new RuntimeException("fail: password invalido");
		this.user = usuarios.get(username);
	}
	
	void logout(){
		if(user == null)
			throw new RuntimeException("fail: ninguem logado");
		user = null;
	}
	
	public User getUser(){
		if(user == null)
			throw new RuntimeException("fail: ninguem logado");
		return user;
	}
}

class Controller{
	Repositorio<User> usuarios;
	GerenciadorDeLogin gerLogin;
	Repositorio<Anotacao> anotacao;
	
	public Controller() {
		usuarios = new Repositorio<User>("usuario");
		anotacao = new Repositorio<Anotacao>("anotacao");
		gerLogin = new GerenciadorDeLogin(usuarios);
	}

    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help"))
            return "lougout, addUser _username _password, login _username _password\n" + 
                   "showUser, addAnotacao, changePass _old _new, showUser\n" + 
                   "rmAnotacao, showAnotacao";
        if (ui[0].equals("addUser"))
			usuarios.add(ui[1], new User(ui[1], ui[2]));
        else if(ui[0].equals("login"))
	    	gerLogin.login(ui[1], ui[2]);
        else if(ui[0].equals("logout"))
        	gerLogin.logout();
        else if(ui[0].equals("showUser"))
        	return "" + gerLogin.getUser();
        else if(ui[0].equals("changePass")) {
        	User user = gerLogin.getUser();
        	if(user.matchPassword(ui[1]))
        		user.setPassword(ui[2]);
        }
        else if(ui[0].equals("addAnotacao")) {
		    String texto = " ";
		    for(int i = 2 ; i<ui.length; i++)
		    	texto += ui[i] + "";
		    gerLogin.getUser().anotacao.add(ui[1], new Anotacao(ui[1],texto)); 	
		}
        else if(ui[0].equals("rmAnotacao"))
			gerLogin.getUser().anotacao.remove(ui[1]);
        else if(ui[0].equals("rmAccount")) {
        	String nome = gerLogin.getUser().getUsername();
        	usuarios.remove(nome);
        	gerLogin.logout();
        }
        
        else if(ui[0].equals("showAnotacao")) {
        	ArrayList<User> users = usuarios.getAll();
        	String saida = " ";
        	for(User user : users)
        		saida += user.getUsername() + " " + user.getAnotacao() + "\n";
        	return saida;
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
