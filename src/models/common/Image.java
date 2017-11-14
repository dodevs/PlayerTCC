package models.common;

import com.google.gson.annotations.SerializedName;

public class Image {
	
	/*
	 * Aqui ta o serializable, pq o Gson molda JSON em cima dessas classe, e pra isso o nome dos atributos
	 * precisa ser igual, só q, variavel "#text" no java nao dá
	 */
	@SerializedName("#text") 
	public String text;
	
	public String size;

}
