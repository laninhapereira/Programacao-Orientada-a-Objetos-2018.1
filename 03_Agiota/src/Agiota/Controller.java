package Agiota;


import java.util.ArrayList;
import java.util.Scanner;

class Cliente {

	public String clienteid;
	public String nome;
	public float saldo = 0;
	public String estado = "vivo";
	
	public Cliente(String clienteid, String nome) {
		this.clienteid = clienteid;
		this.nome =  nome;
	}
	
	public String toString() {
		return " "+clienteid +":"+ nome+ ":" +estado + "\n";
	}
	
	public String mostrarSaldoClientes() {
		return " Nome:" + nome +" Saldo : " + saldo + "\n";
	}

	
}

class Sistema{
	
	public float saldo = 0;
	public float dinheiro;
	public int id_transacao = 0;
	public int id = 0;
	
	ArrayList<Cliente> clientes;
	ArrayList<Transacao> transacoes;
	
	public Sistema(float dinheiro) {
		this.dinheiro = dinheiro;
		clientes = new ArrayList<Cliente>();
		transacoes =  new ArrayList<Transacao>();
		saldo = saldo + dinheiro;
	}

	public String toString() {
		return "Sistema iniciado com: " + saldo + " " + clientes.toString() + "\n";
	}

	public void cadastrarCliente(String nome, String clienteid) {
		for (Cliente c1 : clientes)
			if (c1.nome.equals(nome))
				throw new RuntimeException("Pessoa já cadastrada!");

		clientes.add(new Cliente(clienteid, nome));
	}


	public void cadastrarDivida(String nome, float valor) {
	   if(valor <= saldo) {
		  saldo = saldo + valor;
	      this.transacoes.add(new Transacao(nome,valor, id));
	      id++;
	      return;
	   }
	   else
		   throw new RuntimeException ("Saldo inferior ao dinheiro requerido!");
	   
	}
	
	public void emprestimo(String nome, float valor) {
		for (Cliente c1 : clientes)
			if (c1.nome.equals(nome) ) { 
				c1.saldo = valor + c1.saldo;
				this.cadastrarDivida(nome, valor);
		        return;
	        }
		
		throw new RuntimeException("Pessoa ou saldo invalido!");
	}
	
	 public String mostrarClientes() {
	    	String saida =  "";
	    	for(int i = 0; i < clientes.size(); i++)
	    	   saida += ""+ this.clientes.get(i).mostrarSaldoClientes();
	    	return saida;
	    }
	 
	 public void mostrarDeterminadoCliente(String nome) {
			int i = 0;
			for (Cliente c : clientes) {
				if (c.nome.equals(nome)) {
					System.out.println(c.mostrarSaldoClientes());
					while(transacoes.get(i).nome.equals(nome)) {
						       System.out.println(transacoes.get(i).toString());
			                   i++;       
					}
				}
			return;
			}
		    throw new RuntimeException("Cliente não encontrado!");
		}

	
	public String mostrarTransacoes() {
			 return "" + transacoes.toString();
	}

 
	public void pagarDebito(String nome, float valor) {
		for (Cliente c : clientes)
			if (c.nome.equals(nome)) {
				if (c.saldo < 0) {
					c.saldo = saldo + valor;
					dinheiro = dinheiro + valor;
					this.transacoes.add(new Transacao(nome, valor, id));
					id++;
					return;
				}
			}

		throw new RuntimeException("Cliente não encontrado!");

	}

	public void matarCliente(String nome) {
		for( int i = 0; i < clientes.size(); i++) {
			if(clientes.get(i).nome.equals(nome)) {
				this.clientes.remove(clientes.get(i));
				apagarDividas(nome);
		        return;
			}
		}
		
		throw new RuntimeException("Cliente não encontrado!");
	}
 
	public void apagarDividas( String nome) {
		for(int i = 0 ; i<transacoes.size(); i++) {
			if(transacoes.get(i).nome.equals(nome))
				this.transacoes.remove(transacoes.get(i));
			    i--;
		}
	}
	
}


class Transacao{

	public String nome;
	public float valordebito;
	public int id = 0;
	public float valortotal = 0;
	
	public Transacao(String nome, float valor, int id) {
		this.nome = nome;
		this.valordebito = valor;
		this.id = id;
		this.valortotal = valordebito + valortotal;
	}
	
	public String toString() {
		return  " id:" + id + " nome:" + nome + " valor:" + valordebito +"\n";
	}

}


	
public class Controller {
	Sistema sis;
	Scanner sca;
	    
	    
	public Controller() {
		sca = new Scanner(System.in);
		
	}

	public String query(String line) {
		String[] ui = line.split(" ");
		
	    if (ui[0].equals("iniciar"))
			sis = new Sistema(Float.parseFloat(ui[1]));
	    else if (ui[0].equals("mostrar"))
			return " " + sis;
	    else if(ui[0].equals("cadastrarCliente"))
	    	sis.cadastrarCliente(ui[1],ui[2]);
	    else if(ui[0].equals("mostrarTrasacoes"))
	    	return "" + sis.mostrarTransacoes() ;
	    else if(ui[0].equals("emprestimo"))
	    	sis.emprestimo(ui[1],Float.parseFloat(ui[2]));
	    else if(ui[0].equals("mostrarClientes"))
	    	return ""+ sis.mostrarClientes();
	    else if(ui[0].equals("mostrarDeterminadoCliente"))
	        sis.mostrarDeterminadoCliente(ui[1]);
	    else if(ui[0].equals("receberDebito"))
	    	sis.pagarDebito(ui[1],Float.parseFloat(ui[2]));
	    else if(ui[0].equals("matarCliente"))
	    	sis.matarCliente(ui[1]);
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