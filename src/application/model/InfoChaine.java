package application.model;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoChaine implements Serializable{
	private static final long serialVersionUID = 3390087210124156739L;
	
	private String nom;
	private HashMap<TypeChaine, Integer> infos;
	
	@SuppressWarnings("unused")
	private Integer infoSackson;
	@SuppressWarnings("unused")
	private Integer infoFusion;
	@SuppressWarnings("unused")
	private Integer infoHydra;
	@SuppressWarnings("unused")
	private Integer infoQuantum;
	@SuppressWarnings("unused")
	private Integer infoPhoenix;
	@SuppressWarnings("unused")
	private Integer infoAmerica;
	@SuppressWarnings("unused")
	private Integer infoZeta;
	
	@JsonCreator
	public InfoChaine(){
		super();
	}
	
	@JsonCreator
	public InfoChaine(@JsonProperty("n")String n){
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

	public void setInfos(HashMap<TypeChaine, Integer> infos) {
		this.infos = infos;
	}

	public void setInfoSackson(Integer infoSackson) {
		this.infoSackson = infoSackson;
	}

	public void setInfoFusion(Integer infoFusion) {
		this.infoFusion = infoFusion;
	}

	public void setInfoHydra(Integer infoHydra) {
		this.infoHydra = infoHydra;
	}

	public void setInfoQuantum(Integer infoQuantum) {
		this.infoQuantum = infoQuantum;
	}

	public void setInfoPhoenix(Integer infoPhoenix) {
		this.infoPhoenix = infoPhoenix;
	}

	public void setInfoAmerica(Integer infoAmerica) {
		this.infoAmerica = infoAmerica;
	}

	public void setInfoZeta(Integer infoZeta) {
		this.infoZeta = infoZeta;
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
