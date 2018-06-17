package agencia;

import java.util.Scanner;

class Cliente implements Comparable<Cliente>{
	private String idcliente;
	Repositorio<Conta> contas;
	private String password;
	static int numero = 1;
	
	public Cliente(String idcliente, String password){
		this.idcliente = idcliente;
		this.password = password;
		this.contas = new Repositorio<Conta> ("contas");
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
	
	public String getIdcliente() {
		return idcliente;
	}
	
	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}
	
	public Repositorio<Conta> getContas(){
		return contas;	
	}
	
	public String toString() {
		return "Cliente: " + idcliente;
	}
	
	public void addconta(){
			this.contas.add("numero" , new Conta(numero));
		}
		
	public int compareTo(Cliente other) {
		return this.idcliente.compareTo(other.idcliente);
	}
		
}

class Conta {
	public static int ultIdConta = 1;
	
	private int numero;
	private float saldo;
	private Repositorio<Operacao> extrato;
	private boolean ativa;
	int auxcont;
	
	public Conta(int numero){
		this.numero  = numero;
		this.saldo   += saldo;
		this.extrato = new Repositorio<Operacao>(null);
		this.ativa   = true;
	}
	
	public float getSaldo() {
		return saldo;
	}
	
	public float setSaldo(float saldo) {
		return this.saldo = saldo;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public Repositorio<Operacao> getExtrato(){
		return extrato;
	}
	
	public boolean isAtiva() {
		return ativa;
	}
	
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	public void sacar(float valor){
		if (ativa) {
			if(valor <0 && valor > saldo) 
				throw new RuntimeException("Valor" + valor + "negativo");
			this.setSaldo (saldo -= valor);
			this.extrato.add(" " + auxcont, new Operacao ("sacar", valor));
			auxcont++;
			return;
		
		}	
		throw new RuntimeException("Conta não ativa");
}
	public void depositar(float valor){
		if (ativa) {
			if(valor < 0)
				throw new RuntimeException("fail: valor " + valor + "menor que 0");
			this.extrato.add(" " + auxcont , new Operacao("depositar", valor));
			this.saldo += valor;
			auxcont++;
			return;
		}
		
		throw new RuntimeException ("Conta não ativa");
}
	
	
	public void transferir(float valor, Conta other){
		if(this.ativa){
			if (valor < 0) 
				throw new RuntimeException("valor:" + valor + "menor que 0");
			this.extrato.add("" + auxcont , new Operacao ("transferencia", valor));	
			this.saldo -= valor;
			other.saldo += valor;
			auxcont++;
			return;
			}
		
		throw new RuntimeException("A conta destino está inativa");
		}
		

	public void encerrar(){
		this.ativa = false;
	}

}

class Operacao {
	private String descricao;
	private float valor;
	private float saldoParcial = 0;
	
	public Operacao(String descricao, float valor){
		this.descricao = descricao;
		this.valor = valor;
		this.saldoParcial += valor;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getSaldoParcial() {
		return saldoParcial;
	}

	public void setSaldoParcial(float saldoParcial) {
		this.saldoParcial = saldoParcial;
	}

	public String toString(){
		return "" + descricao + " " + valor + " " + saldoParcial;
	}
}

class GerenciadorDeLogin {

	private Cliente user;
	private Repositorio<Cliente> usuarios;
	
	public GerenciadorDeLogin(Repositorio<Cliente> usuarios) {
		this.usuarios = usuarios;
		user = null;
	}
	
	public void Login(String usuario, String password) {
		if ( user != null )
			throw new RuntimeException ("Já existe alguém logado");
		if (!usuarios.get(usuario).matchPassword(password))
			throw new RuntimeException ("Senha inválida ou pessoa não encontrada");
		this.user= usuarios.get(usuario);	
	}
	
	public void Logout() {
		if(user != null)
			throw new RuntimeException("Não há ninguém logado");
		System.out.println("ok");
		this.user = null;
	}
	
	public Cliente getUser() {
		if(user == null)
			throw new RuntimeException("não há ninguém logado");
		return user;
	}
	
}

class Controller {
	Repositorio<Cliente> usuarios;
	Repositorio<Conta> contas;
	Scanner sca;
	GerenciadorDeLogin ger;
     int numero = 1;
     int aux = 0;
	    
public Controller() {
	sca = new Scanner(System.in);
	usuarios = new Repositorio<Cliente>("username");
	ger = new GerenciadorDeLogin(usuarios);
	contas = new Repositorio<Conta>("conta");
}

public String query(String line) {
	String[] ui = line.split(" ");

	if (ui[0].equals("addCliente"))
		usuarios.add(ui[1], new Cliente(ui[1], ui[2]));
	else if (ui[0].equals("showUsers")) {
		String saida = "";
		for(Cliente us : usuarios.getAll())
			saida += us.getIdcliente() + "\n";
		return saida;
	}
	else if(ui[0].equals("loginCliente")) {
		ger.Login(ui[1], ui[2]);
	    ger.getUser().contas.add(""+ numero , new Conta(numero));
	    numero++;
        aux++;
	}
	else if(ui[0].equals("depositar"))
	    ger.getUser().contas.get(ui[1]).depositar(Float.parseFloat(ui[2]));	
	else if(ui[0].equals("sacar"))
		ger.getUser().contas.get(ui[1]).sacar(Float.parseFloat(ui[2]));
	else if(ui[0].equals("encerrar")) {
		ger.getUser().contas.get(ui[1]).encerrar();
		aux--;
	}
	else if(ui[0].equals("saldo"))
		System.out.println(""+ger.getUser().contas.get(ui[1]).getSaldo());
	else if(ui[0].equals("addConta")) {
	if(aux > 1)
		throw new RuntimeException("fail: você possui o numero maximo de contas ativa");
	ger.getUser().contas.add("" + numero, new Conta(numero));
	numero++;
	aux++;
	}
	else if(ui[0].equals("transferir"))
		ger.getUser().contas.get(ui[1]).transferir(Float.parseFloat(ui[2]), usuarios.get(ui[3]).contas.get(ui[4]));
	    
	else if(ui[0].equals("logout"))
		ger.Logout();
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
