package fr.inria.astor.approaches.rcapr;
import java.io.BufferedReader;



import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.awt.image.BufferedImage;
//import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.martiansoftware.jsap.JSAPException;
import fr.inria.astor.core.ingredientbased.IngredientBasedRepairApproachImpl;
import fr.inria.astor.core.entities.ModificationPoint;
import fr.inria.astor.core.entities.OperatorInstance;
import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.astor.core.entities.SuspiciousModificationPoint;
import fr.inria.astor.core.entities.VariantValidationResult;
import fr.inria.astor.core.faultlocalization.entity.SuspiciousCode;
import fr.inria.astor.core.ingredientbased.IngredientBasedApproach;
import fr.inria.astor.core.ingredientbased.IngredientBasedRepairApproachImpl;
import fr.inria.astor.core.manipulation.MutationSupporter;
import fr.inria.astor.core.manipulation.bytecode.entities.CompilationResult;
import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.astor.core.setup.ProjectRepairFacade;
import fr.inria.astor.core.solutionsearch.navigation.SuspiciousNavigationValues;
import fr.inria.astor.core.stats.Stats;
import fr.inria.astor.core.stats.Stats.GeneralStatEnum;
import fr.inria.astor.util.TimeUtil;
import fr.inria.main.AstorOutputStatus;
import fr.inria.main.evolution.ExtensionPoints;
/*import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Rasterizer;
import guru.nidi.graphviz.model.Graph;
//to daw the graph
import static guru.nidi.graphviz.model.Factory.*;*/
   public class IterativeSearchEngine1 extends fr.inria.astor.core.ingredientbased.ExhaustiveIngredientBasedEngine  implements IngredientBasedApproach  {
   public long time;
   protected int oracleResult=-2;
   public  boolean AC,SMC;
   public boolean MAxGen;
   public boolean timeout;
   public boolean exhausted; 
   public String failt;
   public static int f;
   int ML=1;
   int MM=0;
   public int []nbmp;
   public String cpy;
   public String cpy1;
   public String newLocation;
   public String oldLocation;
   public static int tt_size;
   public static List <String> ts;
   public int modifPointsAnalyzed=0;
   public int operatorExecuted=0 ;
   List<ProgramVariant> l;
   int totalmodfpoints;
   public int []tab0;
   public int []tab1;
   public int []tab2;
   protected RCValidator programValidator=new RCValidator();
   protected VariantValidationResult validationResult=new RCValidation();
   public ProgramVariant nodesmc;
   //Initialization// for now we use this timeout & max generation or limited by modification point and operator
   int maxMinutes = ConfigurationProperties.getPropertyInt("maxtime");
   int maxGenerations = ConfigurationProperties.
		   getPropertyInt("maxGeneration"); 
   public IterativeSearchEngine1(MutationSupporter mutatorExecutor, ProjectRepairFacade projFacade)
			throws JSAPException {
	     super(mutatorExecutor, projFacade);
	      //this.pluginLoaded = new IngredientBasedPlugInLoader();  
		ConfigurationProperties.properties.setProperty(ExtensionPoints.SUSPICIOUS_NAVIGATION.identifier,
				SuspiciousNavigationValues.INORDER.toString());
			
			}

				     
	@Override
	public void startEvolution() throws Exception {
		if (this.ingredientSpace == null) {
			this.ingredientSpace = IngredientBasedRepairApproachImpl.getIngredientPool(getTargetElementProcessors());
}
		String s=ConfigurationProperties.getProperty("location")+File.separator+ConfigurationProperties.getProperty("srctestfolder");
		//test suite size
		List <String> ts=getMethod (new File(s));
		tt_size=ts.size();
		log.info(tt_size);
		tab0=new int[tt_size];
		nbmp=new int[tt_size];
		// the correct program has no failure
	   for(int i=0; i<tt_size;i++)tab0[i]=1;
       generationsExecuted = 1;
       AC=false;
       SMC=false;
       exhausted=false;
       MAxGen=false;
       timeout=false;
       dateInitEvolution=new Date();
	   getIngredientSpace().defineSpace(originalVariant);
	       
		l=new ArrayList<ProgramVariant>();
            f=0;
	    	
		   //execute the base program that will serve as a component in the oracle	    
		   validationResult = validateInstance1(this.originalVariant,projectFacade,true,ts);
					  l=MutationV(variants.get(0),ts);
					 
			   if(AC || MAxGen || timeout || SMC ) return;
			    else {
		  //multiple mutation	
			    	
			ML++;
			MutationML(l,ts);    
			    
          if(AC || MAxGen || timeout || SMC )return;
          else  exhausted=true;
       }
	}
	   
    
	public void MutationML( List<ProgramVariant> L,List <String> tss) throws Exception
	{	    
	     //multiple mutation for each variant where we applied one mutation in the previous step stored in the list l
		 //save visited variants for multiple mutation if necessary
		 List<ProgramVariant> VRL=l;
		MM=0;
		 for (ProgramVariant k : VRL) {			
		    
			try {
			   
				MutationV(k,tss);
				
				 if(AC || MAxGen || timeout || SMC)return;
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			}
		 MM++;
		   }	
		 
	}
		
	
public List<ProgramVariant> MutationV(ProgramVariant parentVariant,List <String> tss) throws Exception

{   
	int mp=0;int m=0;
	 //save visited variants for multiple mutation if necessary

    //PRINT modification point 
   
	for (ModificationPoint modifPoint : this.getSuspiciousNavigationStrategy()
			.getSortedModificationPointsList(parentVariant.getModificationPoints())) {
    if (ML>1) {if(nbmp[MM]>=mp) { mp++; continue;}}
	modifPointsAnalyzed++;

	log.info("\n MP (" + modifPointsAnalyzed + "/" + parentVariant.getModificationPoints().size()
			+ ") location to modify: " + modifPoint);

	// We create all operators to apply in the modifpoint
	List<OperatorInstance> operatorInstances = createInstancesOfOperators(
			(SuspiciousModificationPoint) modifPoint);

	//log.info("--- List of operators (" + operatorInstances.size() + ") : " + operatorInstances);

	if (operatorInstances == null || operatorInstances.isEmpty())
		continue;

	for (OperatorInstance pointOperation : operatorInstances) {

		operatorExecuted++;
		// We validate the variant after applying the operator
		ProgramVariant solutionVariant = variantFactory.createProgramVariantFromAnother(parentVariant,
				generationsExecuted);
		solutionVariant.getOperations().put(generationsExecuted, Arrays.asList(pointOperation));

		applyNewMutationOperationToSpoonElement(pointOperation);

		log.debug("Operator:\n " + pointOperation);
		 //new variant to add to the list			
		
		try {
			  oracleResult = processCreatedVariant1(solutionVariant,tss);
			  if(oracleResult!=-1) {  if(ML<=1) { saveVariant(solutionVariant); l.add(solutionVariant); nbmp[m]=mp; m++;}}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (oracleResult==1) {AC=true;
		log.info("-Found Solution, child variant #" +solutionVariant .getId());
			this.solutions.add(solutionVariant );
			saveStaticSucessful(solutionVariant .getId(), operatorExecuted);
			nodesmc=solutionVariant;
		     //save location of the solution
		    //oldLocation=ConfigurationProperties.properties.getProperty("location");
			//copy src code
		    String bytecodeOutput = projectFacade.getOutDirWithPrefix(ProgramVariant.DEFAULT_ORIGINAL_VARIANT);
		    log.info(bytecodeOutput);
			int j=solutionVariant .getId();			
			String newst="";
			String []st=bytecodeOutput.split("//");
			for (int i=0; i<st.length-2;i++) newst=st[i]+"/";
			//Save modified bin and src
			cpy=newst+"src/variant-"+j;
			cpy1=newst+"bin/variant-"+j;
			try {
				saveVariant(solutionVariant);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}							
			this.setOutputStatus(AstorOutputStatus.STOP_BY_PATCH_FOUND);
				log.debug(" modpoint analyzed " + modifPointsAnalyzed + ", operators " + operatorExecuted);
									
			return null;
		}
		//we found s more correct program we add it to potential candidate program
		else if(oracleResult==0)
		{    SMC=true;
		     failt=programValidator.failingList;
			log.info("- SRC Found Solution, child variant number "+ solutionVariant .getId());
			this.solutions.add(solutionVariant );
			saveStaticSucessful(solutionVariant .getId(), operatorExecuted);
			saveVariant(solutionVariant);
		    nodesmc=solutionVariant;
		     //save location of the solution
		    //oldLocation=ConfigurationProperties.properties.getProperty("location");
			//copy src code
		    String bytecodeOutput = projectFacade.getOutDirWithPrefix(ProgramVariant.DEFAULT_ORIGINAL_VARIANT);
		    log.info(bytecodeOutput);
			int j=solutionVariant .getId();			
			String newst="";
			String []st=bytecodeOutput.split("//");
			for (int i=0; i<st.length-2;i++) newst=st[i]+"/";
			//Save modified bin and src
			cpy=newst+"src/variant-"+j;
			cpy1=newst+"bin/variant-"+j;
			time=TimeUtil.deltaInMinutes(dateInitEvolution);
				return l;	
				
		}

		//undo the operator to try the next one
		undoOperationToSpoonElement(pointOperation);
		//verify if we can loop			
		   if (!belowMaxTime(dateInitEvolution, maxMinutes)) {timeout=true;
				this.setOutputStatus(AstorOutputStatus.TIME_OUT);

				System.out.println(" number of SRC candiate retained :"+1);
				log.debug("Max time reached");
				return l;
			}

			if (maxGenerations <= operatorExecuted) {MAxGen=true;

				this.setOutputStatus(AstorOutputStatus.MAX_GENERATION);
				log.info("Stop-Max operator Applied " + operatorExecuted);
				log.info("modpoint:" + modifPointsAnalyzed + ":all:" + totalmodfpoints + ":operators:"
						+ operatorExecuted);
				return l;
			}
	}
	mp++;
	}
    
	return l;
}
	



	public int processCreatedVariant1(ProgramVariant programVariant,List <String> tss) throws Exception {

		URL[] originalURL = projectFacade.getClassPathURLforProgramVariant(ProgramVariant.DEFAULT_ORIGINAL_VARIANT);

		CompilationResult compilation = compiler.compile(programVariant, originalURL);

		boolean childCompiles = compilation.compiles();
		programVariant.setCompilation(compilation);

		//storeModifiedModel(programVariant);

		if (ConfigurationProperties.getPropertyBool("saveall")) {
			this.saveVariant(programVariant);
		}

		if (childCompiles) {
				f++;
			log.debug("-The child compiles: id " + programVariant.getId());
			currentStat.increment(GeneralStatEnum.NR_RIGHT_COMPILATIONS);

			validationResult = validateInstance1(programVariant,projectFacade,true,tss);
			int vl=validationResult.isAC(tab0,tab1,tab2);log.info("****+++"+vl);
						return (vl);}
			
			 else  {
			log.debug("-The child does NOT compile: " + programVariant.getId() + ", errors: "
					+ compilation.getErrorList());
			currentStat.increment(GeneralStatEnum.NR_FAILLING_COMPILATIONS);
			// In case that the variant a) does not compile; b) compiles but it's
			// not adequate
			Stats.currentStat.getIngredientsStats().storeIngCounterFromFailingPatch(programVariant.getId());
			return -1;
			
		
			 }
	}
		protected VariantValidationResult validateInstance1(ProgramVariant variant, ProjectRepairFacade projectFacade,boolean tr,List <String> tss) throws ClassNotFoundException, IOException {
			
			
			if (f==0) tab1 = programValidator.validate1(variant, projectFacade,tss);
			else tab2=programValidator.validate1(variant, projectFacade,tss);
			if (validationResult != null) {
				//variant.setIsSolution(validationResult.isSuccessful());
				variant.setValidationResult((validationResult));
			}
			
			return validationResult;
		}
	

private static List <String> getMethod (File directory)

{   List <String> l=new ArrayList <String>();
	BufferedReader br = null;	
    FileReader fr = null;
        
    if (!directory.exists())
    {
        return l;
    }
    File[] files = directory.listFiles();
    for (File file : files)
    {
        if (file.isDirectory())
        {   
          l.addAll(getMethod(file));
        }
        else if (file.getName().endsWith(".java"))
        { 
	try {


	//br = new BufferedReader(new FileReader(FILENAME));
	fr = new FileReader(file);
	br = new BufferedReader(fr);

	String line;

	while ((line = br.readLine()) != null) {
		       String ln=line.trim();
			if (ln.startsWith("public void test")|ln.startsWith("protected void test")||ln.startsWith("private void test")|| ln.startsWith("void test"))
					{String s=ln.substring((ln.indexOf("void")+5)
							,ln.indexOf("("));
					l.add(s);
					
					}
	}
			
	} catch (IOException e) { 
	
	e.printStackTrace();

}
        }
    }
return l;
	
}

public void reset(ProgramVariant programVariant) {

	// We remove previous variants
	this.solutions.clear();
	this.variants.clear();
	// We add the new one //TODO: we could do a for
	this.variants.add(programVariant);
	// The parameter becomes the original
	this.originalVariant = programVariant;
	// Removing patch info
	this.patchInfo.clear();

	this.outputStatus = null;

	this.originalVariant.setId(firstgenerationIndex);

	// Saving bytecode
	String bytecodeOutput = projectFacade.getOutDirWithPrefix(ProgramVariant.DEFAULT_ORIGINAL_VARIANT);
	File variantOutputFile = new File(bytecodeOutput);
	System.out.println(variantOutputFile.getAbsolutePath());
	MutationSupporter.currentSupporter.getOutput().saveByteCode(programVariant.getCompilation(), variantOutputFile);

	this.currentStat = Stats.createStat();
}

public  void copyData(File out,File in,String path) throws IOException
{   
	File[]f=out.listFiles();
	if(f==null)
	{
	 if(out.isFile() && ( out.getName().endsWith(".java")||  out.getName().endsWith(".class")))
	  {  path=path+File.separator+out.getName();
		 File f1= new File(in.getPath()+File.separator+path);
		//log.info(f1.getAbsolutePath());
	   // log.info(out);
		//System.out.println(f1.getAbsolutePath()+"** \n"+out.getPath());
	       if(f1.exists())
	    	f1.delete();
	        f1= new File(in.getPath()+File.separator+path);
	    FileUtils.copyFile(out, f1);
    //reitialize path 
    // path="";
	    
     }
	
	}
	else {
		path=path+File.separator+out.getName();
		//System.out.println(path);
		for (int i=0;i<f.length;i++)
	        copyData(f[i],in,path);
	}
	
	
}

//update the code in the input location

}
