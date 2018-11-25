package fr.inria.astor.approaches.rcapr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import fr.inria.astor.core.entities.VariantValidationResult;

;

public class RCValidation implements VariantValidationResult {
	
	boolean  abscorforall = true;
	boolean relcorforall  = true;
	boolean strictforone = false;
	boolean strictRC=false; 
	

	 
	public int isAC(int []tab0,int []tab1,int []tab2)
	{ //the evolution of the working directory
		boolean  abscorforall = true;
		boolean relcorforall  = true;
		boolean strictforone = false;
		boolean strictRC=false; 
		
	      for(int i=0;relcorforall && i<tab0.length;i++)
	         
	      {      
	                   
	            abscorforall = abscorforall && abscor(tab0[i],tab2[i]);
	            
	            relcorforall = relcorforall && relcor(tab2[i], tab1[i],tab0[i]);
	            
	            strictforone = strictforone || strict(tab2[i],tab1[i],tab0[i]);
	            strictRC=strictforone &&  relcorforall;
	            //if(!relcorforall)break;
	           	            	
	        }     
	      //just printing to clarify
			
				if(abscorforall) return 1;
				else if(strictRC) return 0;
				return -2;
			
	}	
	
		
	public boolean isSuccessful()
	{ 
		return (abscorforall);
	}
		
	/****here we use deterministic speci: correct program****/
	boolean Rdet (int s, int sprime)
	{/////Pprime(); // modifies s, preserves sprime
	return (sprime==s);}

	//boolean Rnondet (String s, String sprime)
	//{//Pprime(); // modifies s, preserves sprime
	//return EQ(s, sprime);} // for some equivalence relation EQ
	// Examples of EQ relations: if the state includes several variables,
	// say x, y, z, then take EQ(s,sprime) <=> (x==xprime && y==yprime).
	// E.g.
	/*bool EQ (s, sprime)
	{ return (x==xprime) && (y==yprime);} // but not z==zprime
	// if the output state includes only one variables, then partition its
	// values into equivalence classes. For example, if the set of values
	// is {a,b,c}, then let {a,b} be one class and {c} be another. E.g*/

	/*bool EQ (int s, int sprime)
	{if (s==0) {return (sprime==0);}
	else {return (sprime==1) || (sprime==2);}}

	bool domR() {
		return 1;
	}*/
	//the oracle code
	
	/**oracle for AC***/
	boolean abscor (int s, int sprime)
	{//stype inits; inits=s; candidate(); // modifies s, preserves inits
	return (! domR() || Rdet(s,sprime));}

	
	/**oracle for RC***/
	boolean relcor(int c,int b, int s)
	{//stype inits; inits=s; base(); // modifies s, presaerves inits
	boolean abscorbase = (! domR() || Rdet(b, s));
	//inits=s; candidate(); // modifies s, preserves inits
	boolean abscorcandidate = (! domR() || Rdet(c, s));
	return (! abscorbase || abscorcandidate);}

	
	/**oracle for SRC***/
	boolean strict (int c,int b, int s)
	{//stype inits; inits=s; base(); // modifies s, presaerves inits
	boolean abscorbase = (! domR() || Rdet(b, s));
	//inits=s; candidate(); // modifies s, preserves inits
	boolean abscorcandidate = (! domR() || Rdet(c, s));
	return (! abscorbase && abscorcandidate);}
	boolean domR() {
		return true;
	}


	

}