package Whats;

import java.util.ArrayList;
import java.util.Scanner;


class User{
	String id;
	Repositorio<Chat> grupo;
	Repositorio<Mensagem> msg;
	int qtdmensagens = 0;
	
	public User(String id) {
		this.id = id;
		grupo = new Repositorio<Chat> ("Chat");
		msg = new Repositorio<Mensagem> ("msg");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Repositorio<Chat> getGrupo() {
		return grupo;
	}

	public void setGrupo(Repositorio<Chat> grupo) {
		this.grupo = grupo;
	}

	public Repositorio<Mensagem> getMsg() {
		return msg;
	}

	public void setMsg(Repositorio<Mensagem> msg) {
		this.msg = msg;
	}
	
	public Chat getChat(Chat c) {
		for(Chat chat : this.grupo.getAll()) 
			if(chat.getNomechat().equals(c.getNomechat()))
				return c;
		
		throw new RuntimeException("Voce não é membro desse grupo!");
	}
	
	public String mostrarGrupos() {
		String saida =" ";
		for(Chat c : grupo.getAll())
			saida+= c.toString() + " ";
		return saida;
	}
	
	public void mostraMensagens(Chat c) {
		if(this.getChat(c) != null) {
			System.out.println(c.lermensagens(this.getId()));
		}	
	}
	public String unread(Chat c) {
		String saida="";
		for(Mensagem m : c.getMensagens().getAll()) {
			if(!m.getUser().equals(this.id)) {
				if(!m.isLido()) {
				   qtdmensagens++;
				   m.setLido(true);
				}
			}
		}
		saida += qtdmensagens;
		qtdmensagens = 0;
		return saida;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", grupo=" + grupo + ", msg=" + msg + ", qtdmensagens=" + qtdmensagens + "]";
	}
	
}

class Mensagem{
	String id;
	String user;
	String text;
	boolean lido;
	
	public Mensagem(String user, String text) {
		super();
		this.id = id;
		this.user = user;
		this.text = text;
		this.lido = false;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLido() {
		return lido;
	}
	public void setLido(boolean lido) {
		this.lido = lido;
	}
	@Override
	public String toString() {
		return "Mensagem [ userid=" + user + ", text=" + text + "]";
	}
	
}	

class Chat{
	String nomechat;
	Repositorio<User> users;
	Repositorio<Mensagem> mensagens;
	int contnlido = 0;
	
	public Chat(String nomechat) {
		this.nomechat = nomechat;
		users = new Repositorio<User>("users");
		mensagens = new Repositorio<Mensagem>("mensagens");
	}
	public String getNomechat() {
		return nomechat;
	}
	public void setNomechat(String nomechat) {
		this.nomechat = nomechat;
	}
	public Repositorio<User> getUsers() {
		return users;
	}
	public void setUsers(Repositorio<User> users) {
		this.users = users;
	}
	public Repositorio<Mensagem> getMensagens() {
		return mensagens;
	}
	public void setMensagens(Repositorio<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
	
	public void addaogrupo(User u) {
		this.users.add(u.getId(), u);
		u.getGrupo().add(this.getNomechat(), new Chat(this.getNomechat()));
	}
	public String mostrarusuarios() {
		String saida =" ";
		for(User u : users.getAll())
			saida+=u.toString() + " ";
		return saida;
	}
	public void escreverMensagem(Mensagem msg) {
		for (User u : this.users.getAll()) {
			u.getGrupo().get(this.getNomechat()).getMensagens().add(msg.getId(), msg);
		}
		this.mensagens.add(msg.getId(), msg);
	}
	
	public String lermensagens(String s) {
		String saida = "";
    	for(Mensagem m : this.mensagens.getAll()) {
    		if(!s.equals(m.getUser())) {
    		saida += m;
    		saida += "";
    		}
    	}
    	return saida;
    }
	
	public String toString() {
		return nomechat;
	}
	
}

class Controller{
	Scanner sca;
	Repositorio<User> usuarios;
	Repositorio<Chat> grupos;
	int inmensagem = 0;
	
	public Controller() {
		sca = new Scanner (System.in);
		usuarios = new Repositorio <User> ("usuarios");
	}
	
	public String oracle(String line){
		
        String ui[] = line.split(" ");

        if(ui[0].equals("help"))
            return "adduser, showusers, criargrupo...";
        else if (ui[0].equals("adduser")) 
			usuarios.add(ui[1], new User(ui[1]));
        else if(ui[0].equals("showusers")) {
        	String saida = "";
        	for (User user : usuarios.getAll()) 
        		saida += user.toString() + "\n";
        	return saida;	
        }
        else if(ui[0].equals("criargrupo")) {
        	usuarios.get(ui[1]).getGrupo().add(ui[2], new Chat(ui[2]));
		    grupos.add(ui[2],new Chat(ui[2]));
		    grupos.get(ui[2]).users.add(ui[1], usuarios.get(ui[1]));
        }
        else if(ui[0].equals("showGruposUmaPessoa")) {
        	System.out.println(usuarios.get(ui[1]).mostrarGrupos());
		}
        else if(ui[0].equals("addaogrupo")) {
        	usuarios.get(ui[1]).getGrupo().get(ui[2]).addaogrupo(usuarios.get(ui[3]));
			grupos.get(ui[2]).users.add(ui[3], usuarios.get(ui[3]));
        }
        else if(ui[0].equals("showGrupos")) {
			String saida = "";
			for(Chat c : grupos.getAll())
				saida += c.toString();
			return saida;
		}
        else if(ui[0].equals("mostrarpessoasGrupo")) {
        	System.out.println(grupos.get(ui[1]).mostrarusuarios());
        }
        else if(ui[0].equals("sairgrupo")) {
    			usuarios.get(ui[1]).getGrupo().remove(grupos.get(ui[2]).getNomechat());
    			grupos.get(ui[2]).getUsers().remove(ui[1]);
    		
        }
        else if(ui[0].equals("mandarmensagem")) {
        	String entrada = "";
        	User usuario = 	usuarios.get(ui[1]);
        	for(int i = 3 ; i<ui.length; i++) {
        		entrada += ui[i];
        		entrada += " "; 
        	}
        	 grupos.get(ui[1]).escreverMensagem(new Mensagem(""+usuarios.get(ui[2]).getId(), entrada));
        	
        }
        else if(ui[0].equals("lermensagens")) {
        	System.out.println(usuarios.get(ui[1]).getGrupo().get(ui[2]).lermensagens(usuarios.get(ui[1]).getId()));
		}
        
        else if(ui[0].equals("mensagensgrupo")) {
			String saida = "";
			for(Mensagem m : grupos.get(ui[1]).mensagens.getAll())
				saida += m + " ";
			return saida;
		}
//
        else if(ui[0].equals("unread")) {
			String saida="";
			for(Chat c : usuarios.get(ui[1]).getGrupo().getAll()) {
				saida+= c.getNomechat() + "(" + usuarios.get(ui[1]).unread(c)+ ")";
			}
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