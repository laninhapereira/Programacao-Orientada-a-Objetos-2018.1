package AgendaHibrida;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

abstract class Entry{
	String idEntry;
	boolean favorite;
	
	public Entry(String idEntry) {
		super();
		this.idEntry = idEntry;
		favorite = false;
	}

	public String getIdEntry() {
		return idEntry;
	}

	public void setIdEntry(String idEntry) {
		this.idEntry = idEntry;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public String converte(int qtd){
		String saida = " ";
		for (int i=0; i < qtd; i++)
			saida += "*";
		return saida;
	}

	@Override
	public String toString() {
		if (favorite)
			return "@"+idEntry;
		else
			return "-"+idEntry;
	}
	
}

class TelefoneInvalidoException extends RuntimeException{
	public TelefoneInvalidoException(String number) {
		super("fail: numero " + number + " invalido");
	}
}

class Fone{
	private String id;
	private String number;
	
	public Fone(String id, String number) {
		this.id = id;
		this.number = number;
		validar();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
		validar();
	}
	private void validar() {
		String validos = "0123456789()-.";
		for(int i = 0; i < this.number.length(); i++) {
			boolean achei = false;
			for(int j = 0; j < validos.length(); j++) {
				if(this.number.charAt(i) == validos.charAt(j)) {
					achei = true;
					break;
				}
			}
			if(!achei)
				throw new TelefoneInvalidoException(this.number);
		}
	}
	public String toString() {
		return this.id + ":" + this.number;
	}
}

class Contato extends Entry{
	Repositorio<Fone> fones;
	
	public Contato(String id) {
		super(id);
		fones = new Repositorio<Fone>("fones");
	}
	
	public Repositorio<Fone> getFones() {
		return fones;
	}

	public void setFones(Repositorio<Fone> fones) {
		this.fones = fones;
	}
	
	public void addfone(Fone f) {
		this.fones.add(f.getId(), f);
	}
	public void rfone(String id) {
		this.fones.remove(id);
	}
	public String showfone() {
		String saida = "";
		for (Fone f : fones.getAll())
			saida += f.toString() + "" + "\n";
		return saida;
	}

	public String toString() {
		return super.toString()+fones.toString();
	}
}

class Agenda {
	private Repositorio<Entry> entradas;
	private Repositorio<Entry> favoritos;
	
	public Agenda() {
		entradas = new Repositorio<Entry>("entradas");
		favoritos = new Repositorio<Entry>("favoritos");
	}
	
	public Repositorio<Entry> getEntradas() {
		return entradas;
	}


	public void setEntradas(Repositorio<Entry> entradas) {
		this.entradas = entradas;
	}


	public Repositorio<Entry> getFavoritos() {
		return favoritos;
	}


	public void setFavoritos(Repositorio<Entry> favoritos) {
		this.favoritos = favoritos;
	}


	public void rmEntry(String idEntry) {
		this.entradas.remove(idEntry);
	}
	
	public void favoritar(Entry id) {
		if(!id.isFavorite())
			id.setFavorite(true);
		this.favoritos.add(id.getIdEntry(), id);
		
	}
	
	public String showfavoritos() {
		String saida = "";
		for (Entry t : favoritos.getAll())
			saida += t.toString() + "\n";
		return saida;
	}
	
	public void desfavoritar(Entry id) {
		if(id.isFavorite())
			id.setFavorite(false);
		this.favoritos.remove(id.getIdEntry());
	}
	
	public String show(){
		String saida = "";
		for (Entry t : entradas.getAll()) {
			if (t instanceof Password) {
				int qtd = ((Password) t).getPassword().length();
				saida += t.toString() + t.converte(qtd) + "\n";
			}
			else
				saida+= t.toString() + "\n";
		}
		return saida;
	}
}

class MasterAgenda extends Agenda{
	Agenda agenda;
	String senhaMestre;
	Repositorio<Contato> contatos;
	Repositorio<Note> notas;
	Repositorio<Password> senhas;
	
	public MasterAgenda(Agenda agenda) {
		this.agenda = agenda;
		this.senhaMestre = senhaMestre;
		contatos = new Repositorio<Contato>("contatos");
		notas = new Repositorio<Note>("notas");
		senhas = new Repositorio<Password>("passwords");
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public String getSenhaMestre() {
		return senhaMestre;
	}

	public void setSenhaMestre(String senhaMestre) {
		this.senhaMestre = senhaMestre;
	}

	public Repositorio<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Repositorio<Contato> contatos) {
		this.contatos = contatos;
	}

	public Repositorio<Note> getNotas() {
		return notas;
	}

	public void setNotas(Repositorio<Note> notas) {
		this.notas = notas;
	}

	public Repositorio<Password> getPasswords() {
		return senhas;
	}

	public void setPasswords(Repositorio<Password> senhas) {
		this.senhas = senhas;
	}
	
	public void addnote() {
		for (Entry e : agenda.getEntradas().getAll()) {
			if (e instanceof Contato)
				this.notas.add(e.getIdEntry(), new Note (e.getIdEntry(), ((Note) e).getNota()));
		}
	}
	public void addcontatos() {
		for(Entry e : agenda.getEntradas().getAll()) {
			if (e instanceof Contato)
				this.contatos.add(e.getIdEntry(), new Contato (e.getIdEntry()));
		}
	}
	public void addpassword() {
		for (Entry e : agenda.getEntradas().getAll()) {
			if (e instanceof Password)
				this.senhas.add(e.getIdEntry(), new Password (e.getIdEntry(), ((Password) e).getPassword()));
		}
	}
	public void mostrarSenhas(String password) {
		if (password.equals(senhaMestre)) {
			String saida = "";
			for (Password p : this.senhas.getAll())
				saida += p.toString() + " " + p.getPassword() + "\n";
			System.out.println(saida);
			
		} else {
			
			String sai = "";
			for (Password p : this.senhas.getAll()) {
				int qtd = p.getIdEntry().length();
				sai += p.toString() + " " + p.converte(qtd);
			}
			System.out.println(sai);
		}
	}
	public void mostrarSenhas() {
		String sai = "";
		for (Password p : this.senhas.getAll()) {
			int qtd = p.getIdEntry().length();
			sai += p.toString() + " " + p.converte(qtd);
		}
		System.out.println(sai);
	}
}

class Password extends Entry{
	String password;
	
	public Password(String id, String password) {
		super(id);
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
class Note extends Entry{
	String nota;

	public Note(String id, String nota) {
		super(id);
		this.nota = nota;
	}
	
	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public String toString() {
		return super.toString() + ":" + nota;
	}

	
}

class Controller {
	Agenda agenda;
	Scanner sca;
	MasterAgenda master;
	
	public Controller() {
		agenda = new Agenda();
		sca = new Scanner(System.in);
		master = new MasterAgenda(agenda); 
	}
			
	public String oracle(String line){
		String ui[] = line.split(" ");
		if(ui[0].equals("help"))
			return "addcontato _nome _id:_fone _id:_fone ...\nshow";
		else if(ui[0].equals("addcontato")) {
			agenda.getEntradas().add(ui[1], new Contato(ui[1]));
		}
		else if(ui[0].equals("addnotes")) {
			String texto = "";
			for (int i = 2; i < ui[i].length(); i++)
				texto += ui[i] + "";
			agenda.getEntradas().add(ui[1], new Note (ui[1], texto));
		}
		else if(ui[0].equals("addpass")) {
			agenda.getEntradas().add(ui[1], new Password (ui[1],ui[2]));
		}
		else if(ui[0].equals("addfone")) {
			master.getContatos().get(ui[1]).addfone(new Fone(ui[2], ui[3]));
		}
		else if(ui[0].equals("senhamestre")) {
			master.setSenhaMestre(ui[1]);
		}
		else if(ui[0].equals("showsenhamestre")) {
			master.mostrarSenhas(ui[1]);
		}
		else if(ui[0].equals("showsenhas")) {
			master.addpassword();
			String saida = "";
			for (Password p : master.getPasswords().getAll()) {
				int quantidade = p.getIdEntry().length();
				saida += p.toString() + " " + p.converte(quantidade) + "\n";
			}
			return saida;
		}
		else if(ui[0].equals("showfones")) {
			System.out.println("Usuario: " + master.getContatos().get(ui[1]).getIdEntry());
			System.out.println(master.getContatos().get(ui[1]).showfone());
		}
		else if (ui[0].equals("showContatos")) {
			master.addcontatos();
			String saida = "";
			for (Contato c : master.getContatos().getAll())
				saida += c.toString() + "\n";
			return saida;
		} 
		else if (ui[0].equals("showNotas")) {
			master.addnote();
			String saida = "";
			for (Note n : master.getNotas().getAll())
				saida += n.toString() + "\n";
			return saida;
		} 
		else if (ui[0].equals("rmfone")) 
			master.getContatos().get(ui[1]).rfone(ui[2]);
		else if(ui[0].equals("fav"))// _fav _id
			agenda.favoritar(agenda.getEntradas().get(ui[1]));
		else if(ui[0].equals("desfav"))
			agenda.desfavoritar(agenda.getEntradas().get(ui[1]));
		else if(ui[0].equals("showfavoritos")) {
			System.out.println(agenda.showfavoritos());
		}
		else if(ui[0].equals("showentradas"))
			System.out.println(agenda.show());
		else
			return "comando nao encontrado";
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
            if(line == "" || line.indexOf(0) == ' ')
            	continue;
            //System.out.println(line);
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