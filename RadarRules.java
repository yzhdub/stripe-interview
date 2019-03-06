/*
Task: 
	Basic Block:
		In:["CHARGE: card_country=US&currency=USD&amount=150&ip_country=CA","BLOCK:amount > 100",]
    	Out: 0
    Compound Block:
    	In: ["CHARGE: card_country=US&currency=USD&amount=150&ip_country=CA","ALLOW:amount<100","BLOCK:card_country != ip_country AND amount > 100",]
		Out: 0	
*/

public class RadarRules{
	public static void main(String[] args) {
		String[] values = "CHARGE: card_country=US&currency=USD&amount=150&ip_country=CA,ALLOW:amount<100,BLOCK:card_country != ip_country AND amount > 100,".split(",");
	    String[] data = values[0].split("&");
	    Charge charge = new Charge();
		charge.setValues(data[0].substring(data[0].indexOf("=")+1, data[0].length()),
			data[1].substring(data[1].indexOf("=")+1, data[1].length()),
			Integer.parseInt(data[2].substring(data[2].indexOf("=")+1, data[2].length())),
			data[3].substring(data[3].indexOf("=")+1, data[3].length()));
        int threshold = Integer.parseInt(values[1].substring(values[1].indexOf("amount")+7, values[1].length()));
        
        if(values[1].toLowerCase().contains("allow")){
        	if(threshold > charge.getAmount()){
        		if(values.length >= 2){
        			if(values[2].contains(charge.getCardCountry() + " != " + charge.getIpCountry() + " AND ")){
        				if(!charge.getCardCountry().equals(charge.getIpCountry()) && charge.getAmount() > Integer.parseInt(values[2].replaceAll("[^0-9]", ""))){
        					if(values[2].toLowerCase().contains("block")){
        						System.out.println(0);
        					}else if(values[2].toLowerCase().contains("allow")){
        						System.out.println(1);
        					}
        				}
        			}
        		}
        		
        	}
        	else if(values[1].toLowerCase().contains("<")){
        		if(charge.getAmount() > threshold){
        		    System.out.println(1);
        		}
        	}else if(values[1].toLowerCase().contains("==")){
        		if(charge.getAmount() == threshold){
        			System.out.println(1);
        		}
        	}else if(values[1].toLowerCase().contains("!=")){
        		if(charge.getAmount() != threshold){
        			System.out.println(1);
        		}	
        	}
        }else if(values[1].toLowerCase().contains("block")){
        	if(values[1].toLowerCase().contains(">")){
        		if(charge.getAmount() > threshold){
        			System.out.println(0);
        		}
        	}else if(values[1].toLowerCase().contains("<")){
        		if(charge.getAmount() < threshold){
        		    System.out.println(0);
        		}
        	}else if(values[1].toLowerCase().contains("==")){
        		if(charge.getAmount() == threshold){
        			System.out.println(0);
        		}
        	}else if(values[1].toLowerCase().contains("!=")){
        		if(charge.getAmount() != threshold){
        			System.out.println(0);
        		}	
        	}
        }
		
	    			
	}

}
class Charge{
	private String card_country;
	private String currency;
	private int amount;
	private String ip_country;

	public Charge (){
	}

	public void setValues(String card_country, String currency, int amount, String ip_country){
		this.card_country = card_country;
		this.currency = currency;
		this.amount = amount;
		this.ip_country  = ip_country;
	}

	public String getCardCountry(){
		return this.card_country;
	}

	public String getIpCountry(){
		return this.ip_country;
	}

	public int getAmount(){
		return this.amount;
	}
}
