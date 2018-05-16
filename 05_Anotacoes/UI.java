package Atv5Anotacoes;

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
	public Repositorio <Anotacao> notas;
	
	public Repositorio<Anotacao> getNotas() {
		return notas;
	}
	public void setNotas(Repositorio<Anotacao> notas) {
		this.notas = notas;
	}
	public User(String username, String password) {
		this.password = password;
		this.username = username;
		notas = new Repositorio<Anotacao>(username);
		
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
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return username + ":" + password;
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
	
	public void Login(String username, String senha){
		if(user != null)
			throw new RuntimeException("fail: ja existe alguem logado");
		if(!usuarios.get(username).matchPassword(senha))
			throw new RuntimeException("fail: password invalido");
		this.user = usuarios.get(username);
	}
	
	public void Logout(){
		if(user == null)
			throw new RuntimeException("fail: ninguem logado");
		this.user = null;
	}
	
	public User getUser(){
		if(user == null)
			throw new RuntimeException("fail: ninguem logado");
		return user;
	}
}

class Controller{
	Repositorio<User> usuarios;
	Repositorio<Anotacao> notas;
	Scanner sca;
	GerenciadorDeLogin ger;
		
	public Controller() {
		
		sca = new Scanner(System.in);
		usuarios = new Repositorio<User>("usuario");
		ger = new GerenciadorDeLogin(usuarios);
		notas = new Repositorio<Anotacao>("notas");
	}

	    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
	public String oracle(String line){
		String ui[] = line.split(" ");

	    if(ui[0].equals("help"))
	    	return "add_user, login_user, show_user, add_anotacao, show_anotacao, logout_user, att_password, rm_anotacao.";
	    
	    if (ui[0].equals("add_user"))
			usuarios.add(ui[1], new User(ui[1], ui[2]));
	    else if(ui[0].equals("login_user"))
	    	ger.Login(ui[1], ui[2]);
		else if (ui[0].equals("showUsers")) {
			String saida = "";
			for(User us : usuarios.getAll())
				saida += us.getUsername() + "\n";
			return saida;
		}
		else if(ui[0].equals("add_anotacao")) {
		    String texto = " ";
		    for(int i = 2 ; i<ui.length; i++)
		    	texto += ui[i] + "";
		    ger.getUser().notas.add(ui[1], new Anotacao(ui[1],texto)); 	
		}
		else if (ui[0].equals("att_password")) {
			if (ger.getUser().matchPassword(ui[1]))
				ger.getUser().setPassword(ui[2]);
		}
		else if(ui[0].equals("show_anotacao")) {
			String saida = " ";
		for(User u : usuarios.getAll())
				saida += u.getNotas() + "\n";
			return saida;
			}
	    else if(ui[0].equals("logout_user"))
	    	ger.Logout();
	    else if(ui[0].equals("rm_anotacao"))
			ger.getUser().notas.remove(ui[1]);
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