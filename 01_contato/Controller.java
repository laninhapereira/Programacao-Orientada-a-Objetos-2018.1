package Contato;

import java.util.ArrayList;
import java.util.Scanner;

class Contato{
	String nome;
	ArrayList<Telefone> telefones;
	
	public Contato(String nome) {
		this.nome = nome;
		telefones = new ArrayList<Telefone>();
	}
	
	public String toString(){
		return nome;
	}
	
	public void addtelefone(Telefone t) {
		for (Telefone fone : this.telefones) {
			if (fone.id.equals(t.id))
				throw new RuntimeException("Não é possível adicionar um telefone!");
		}
		this.telefones.add(t);
	}
	public String showtelefone() {
		String saida = "";
		for (Telefone t : this.telefones) {
			saida += t + "\n";
		}
		return saida;
	}
	public void rmtelefone(String id) {
		for (int i = 0; i < telefones.size(); i++) {
			if (telefones.get(i).id.equals(id)) {
				telefones.remove(telefones.get(i));
			}
		}
	}
}

class Telefone{
	String id;
	String numero;
	
	public Telefone(String id, String numero) {
		this.id = id;
		this.numero = numero;
	}

	public String toString() {
		String saida = "";
		if(id != null)
			saida +=  id + " " + numero;
		else
			saida += "";
		return saida;
	}
	
}

public class Controller {
	Contato c;
	Scanner sca;
	
	public Controller(){
	sca = new Scanner(System.in);
	}
	
	public String oracle(String line) {
		String[] ui = line.split(" ");
		
	    if (ui[0].equals("init"))
			c = new Contato(ui[1]);
	    else if (ui[0].equals("show"))
			return "" + c + c.showtelefone();
		else if (ui[0].equals("addFone"))
			c.addtelefone(new Telefone(ui[1],ui[2]));
		else if (ui[0].equals("rmFone"))
			c.rmtelefone(ui[1]);
		return "done";
	}
}

class IO {
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


