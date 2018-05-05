package Agiota;

import java.util.ArrayList;
import java.util.Scanner;

class Cliente {
	
	public String apelido;
	public String nome;
	public String esta = "vivo";
	public float saldo = 0;
	
	public Cliente (String apelido, String nome) {
		this.apelido = apelido;
		this.nome = nome;
	}
	
	public String toString(){
		return " "+nome+" "+apelido+" "+esta;
	}
	
	public String Saldo() {
		return "nome: "+nome+"saldo: "+saldo;
	}
	
}

public class Sistema{
	public float saldo = 0;
	public float dinheiro = 0;
	public int id_transacao;
	public int id = 0;
	
	ArrayList<Cliente> clientes;
	ArrayList<Transacao> transacoes;
	
	public Sistema (float dinheiro) {
		this.dinheiro = dinheiro;
		clientes = new ArrayList<Cliente>();
		transacoes =  new ArrayList<Transacao>();
		saldo = saldo+dinheiro;
	}
	
	public String toString () {
		return "Sistema iniciado com: "+saldo+" "+clientes.toString();
	}
	
	public void CadastrarCliente(String nome, String apelido) {
		for (Cliente c : clientes) {
			if (c.nome.equals(nome))
				throw new RuntimeException("Pessoa já cadastrada!");
			
		clientes.add(new Cliente(apelido, nome));	
		}
	}
	
	public void CadastrarDivida(String nome, float valor) {
		if (valor <= saldo) {
			saldo = saldo + valor;
			
			this.transacoes.add(new Transacao(nome, valor, id));
			id ++;
			return;
		} 
		else {
			throw new RuntimeException ("Saldo inferior ao dinheiro requerido!");
		}
			
	}
	
	public void Emprestimo (String nome, float valor) {
		for (Cliente c : clientes) {
			if (c.nome.equals(nome) ) { 
				c.saldo = valor + c.saldo;
				this.CadastrarDivida(nome, valor);
		        return;
			}
		}
		
		throw new RuntimeException("Pessoa ou saldo invalido!");
	}
	
	public String MostrarClientes () {
		String s = " ";
		for(int i = 0; i < clientes.size(); i++) 
	    	   s += ""+ this.clientes.get(i).Saldo();
	    	return s;
	}
	
	public void MostrarDeterminadoCliente (String nome) {
		int i = 0;
		for (Cliente c : clientes) {
			if (c.nome.equals(nome)) {
				System.out.println(c.Saldo());
				while(transacoes.get(i).nome.equals(nome)) {
					 System.out.println(transacoes.get(i).toString());
	                   i++; 
				}
			}
			
			return;
			
		}
		
		throw new RuntimeException("Cliente não encontrado!");
		
	}
	
	public String MostrarTransacoes () {
		return " " + transacoes.toString();
	}
	
	public void PagarDebito (String nome, float valor) {
		for (Cliente c : clientes) {
			if (c.nome.equals(nome)) {
				if (c.saldo < 0) {
					c.saldo = saldo + valor;
					dinheiro = dinheiro + valor;
					this.transacoes.add(new Transacao(nome, valor, id));
					id++;
					return;
				}
			}
		}
		
		throw new RuntimeException("Cliente não encontrado!");
		
	}
	
	public void MatarCliente(String nome) {
		for( int i = 0; i < clientes.size(); i++) {
			if(clientes.get(i).nome.equals(nome)) {
				this.clientes.remove(clientes.get(i));
				ApagarDividas(nome);
		        return;
			}
		}
		
		throw new RuntimeException("Cliente não encontrado!");
		
	}
	
	public void ApagarDividas (String nome) {
		for (int i = 0; i < transacoes.size(); i++) {
			if(transacoes.get(i).nome.equals(nome)) {
				this.transacoes.remove(transacoes.get(i));
			    i--;
			}
		}
	}
	
}


class Transacao {
	public String nome;
	public float valorDebito;
	public int id = 0;
	public float total = 0;
	
	public Transacao(String nome, float valor, int id) {
		this.nome = nome;
		this.valorDebito = valor;
		this.id = id;
		this.total = valorDebito + total;
	}
	
	public String toString() {
		return "id:"+id+" nome:"+nome+" valor:"+valorDebito;
	}
	
}

class Controller {
	Sistema sis;
	Scanner sca;
	    
	    
	public Controller() {
		sca = new Scanner(System.in);
		
	}

	public String query(String line) {
		String[] ui = line.split(" ");
		
	    if (ui[0].equals("Iniciar"))
			sis = new Sistema(Float.parseFloat(ui[1]));
	    else if (ui[0].equals("Mostrar"))
			return " " + sis;
	    else if(ui[0].equals("CadastrarCliente"))
	    	sis.CadastrarCliente(ui[1],ui[2]);
	    else if(ui[0].equals("MostrarTrasacoes"))
	    	return "" + sis.MostrarTransacoes() ;
	    else if(ui[0].equals("Emprestimo"))
	    	sis.Emprestimo(ui[1],Float.parseFloat(ui[2]));
	    else if(ui[0].equals("MostrarClientes"))
	    	return ""+ sis.MostrarClientes();
	    else if(ui[0].equals("MostrarDeterminadoCliente"))
	        sis.MostrarDeterminadoCliente(ui[1]);
	    else if(ui[0].equals("ReceberDebito"))
	    	sis.PagarDebito(ui[1],Float.parseFloat(ui[2]));
	    else if(ui[0].equals("MatarCliente"))
	    	sis.MatarCliente(ui[1]);
	    else
	    	return " Comando invalido";
		return "done";
	}

	public void shell() {
		while (true) {
			String line = sca.nextLine();
			try {
				System.out.println(query(line));
			} catch (RuntimeException re) {
				System.out.println(re.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
        Controller c = new Controller();
        c.shell();
    }
  
}
