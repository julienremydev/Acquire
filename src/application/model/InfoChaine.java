package application.model;

import java.io.Serializable;
import java.util.HashMap;

public class InfoChaine implements Serializable{
	private static final long serialVersionUID = 3390087210124156739L;
	
	private String nom;
	private HashMap<TypeChaine, Integer> infos;
	
	private Integer infoSackson;
	private Integer infoFusion;
	private Integer infoHydra;
	private Integer infoQuantum;
	private Integer infoPhoenix;
	private Integer infoAmerica;
	private Integer infoZeta;
	
	public InfoChaine(String n){
		this.nom = n;
		infos = new HashMap<>();
		this.infos.put(TypeChaine.SACKSON, 0);
		this.infos.put(TypeChaine.AMERICA, 0);
		this.infos.put(TypeChaine.FUSION, 0);
		this.infos.put(TypeChaine.PHOENIX, 0);
		this.infos.put(TypeChaine.HYDRA, 0);
		this.infos.put(TypeChaine.QUANTUM, 0);
		this.infos.put(TypeChaine.ZETA, 0);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public HashMap<TypeChaine, Integer> getInfos() {
		return infos;
	}

	public Integer getInfoSackson() {
		return this.infos.get(TypeChaine.SACKSON);
	}

	public Integer getInfoFusion() {
		return this.infos.get(TypeChaine.FUSION);
	}

	public Integer getInfoHydra() {
		return this.infos.get(TypeChaine.HYDRA);
	}

	public Integer getInfoQuantum() {
		return this.infos.get(TypeChaine.QUANTUM);
	}

	public Integer getInfoPhoenix() {
		return this.infos.get(TypeChaine.PHOENIX);
	}

	public Integer getInfoAmerica() {
		return this.infos.get(TypeChaine.AMERICA);
	}
	
	public Integer getInfoZeta() {
		return this.infos.get(TypeChaine.ZETA);
	}
}
