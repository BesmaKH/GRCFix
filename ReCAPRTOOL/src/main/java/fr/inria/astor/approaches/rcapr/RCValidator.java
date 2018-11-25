package fr.inria.astor.approaches.rcapr;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.astor.core.setup.ProjectRepairFacade;
import fr.inria.astor.core.validation.processbased.LaucherJUnitProcess;
import fr.inria.astor.core.validation.processbased.ProcessValidator;
import fr.inria.astor.core.validation.results.TestResult;

public class RCValidator extends ProcessValidator {
	String failingList;
	
	


	protected Logger log = Logger.getLogger(Thread.currentThread().getName());
     /* constructor of the validator that will parse also the correct program for seek of compraison*/
     public RCValidator()
     {         }
  
    public int [] validate1(ProgramVariant mutatedVariant, ProjectRepairFacade projectFacade,
    		List<String> ts) throws IOException {
    	   
    		int []res=new int[ts.size()];
    		for(int i=0; i<ts.size();i++)res[i]=1;
    		failingList=new String();
   
	try {
	    //parameter
		URL[] bc = createClassPath(mutatedVariant, projectFacade);
		LaucherJUnitProcess testProcessRunner = new LaucherJUnitProcess();
		
		log.debug("-Running  validation");
		long t1 = System.currentTimeMillis();
		
		List<String> testCasesRegression = projectFacade.getProperties().getRegressionTestCases();
		if (testCasesRegression == null || testCasesRegression.isEmpty()) {
			log.error("Any test case for regression testing");
			for(int i=0; i<ts.size();i++)res[i]=0;
		} 
		
        String jvmPath = ConfigurationProperties.getProperty("jvm4testexecution");        
		TestResult tr = testProcessRunner.execute(jvmPath, bc,
				testCasesRegression,ConfigurationProperties.getPropertyInt("tmax1"));
		if (tr == null) {
			log.debug("**The validation 1 have not finished well**");
			for(int i=0; i<ts.size();i++)res[i]=0;
		} 
		List<String> ft =tr.failTest;
		log.info(tr.failures);
		log.info(tr.failTest.size());
	    String s=new String();;
	   //make failing test with 0 entry in the tab res	 
		   for (int i1=0;i1<ft.size();i1++)     
		   {   if(ft.get(i1).startsWith("test"))
		   {   //store the failing list  
			   if(i1>0)
			   failingList=failingList+":"+ft.get(i1).substring(ft.get(i1).indexOf('(')+1,ft.get(i1).indexOf(')'));
			   else
				   failingList=ft.get(i1).substring(ft.get(i1).indexOf('(')+1,ft.get(i1).indexOf(')'));  
		         s =ft.get(i1).substring(0,ft.get(i1).indexOf('('));
		        log.info(s);
		         int d= ts.indexOf(s);
		         res[d]=0;}
		   }
		
	  	  long t2 = System.currentTimeMillis();
		   
				
			 
			 return res;
			
		 
	    

	} catch (MalformedURLException e) {
		e.printStackTrace();
		return null;
	
}

}  
    public String  getF()
    {return failingList;}
    }