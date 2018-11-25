package fr.inria.astor.test.repair.approaches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.junit.Ignore;
import org.junit.Test;

import fr.inria.astor.approaches.rcapr.IterativeSearchEngine1;
import fr.inria.astor.approaches.rcapr.RCValidator;
import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.astor.core.stats.PatchHunkStats;
import fr.inria.astor.core.stats.PatchStat;
import fr.inria.astor.core.stats.PatchStat.HunkStatEnum;
import fr.inria.astor.core.stats.PatchStat.PatchStatEnum;
import fr.inria.astor.core.stats.Stats;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;

public class Testextiter {
		
 

	
	@Ignore
	public void test1MathMf() throws Exception {
		//test case where we combine fault
		int nb=0;
		String mxt="180";
		String mxgn="2000";
	    String nbmodp="3";
	    String fl="";
		List<ProgramVariant> solutions=null ;
	    //file where we save smc node
	    String solLocation="./examples/solution";
	    File sol = new File(solLocation);
	    if(sol.exists()) sol.delete();
	    FileUtils.copyDirectory(new File("./examples/Math_mf"), new File(solLocation));
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		if (out.exists()) 
		out.delete();			
		String[] args = new String[] { "-dependencies", dep, "-failing",
				"org.apache.commons.math.complex.ComplexTest:org.apache.commons.math.analysis.solvers.BrentSolverTest:org.apache.commons.math.analysis.solvers.BisectionSolverTest", "-location",
				new File("./examples/Math_mf").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/main/java/", "-srctestfolder", "/src/test/java/", "-binjavafolder", "/target/classes",
				"-bintestfolder", "/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-stopfirst", "true","-population", "1", "-maxtime", mxt,"-maxgen", mxgn,
				
		};
		boolean AC;
		boolean SMC;
		boolean timeout;
		boolean mxg;
		boolean exh;
		CommandSummary command = new CommandSummary(args);
		command.command.put("-loglevel", "INFO");
		command.command.put("-mode", "custom");
		command.command.put("-customengine",
				IterativeSearchEngine1.class.getCanonicalName());
		command.command.put("-validation", RCValidator.class.getCanonicalName());
		command.command.put("-parameters",

				"maxnumbersolutions:1:runexternalvalidator:true:logtestexecution:true:maxmodificationpoints:"

				+ nbmodp + ":maxsuspcandidates:300:limitbysuspicious:false:parsesourcefromoriginal:true");
		
		do {
					
		System.out.println(Arrays.toString(args));
		main1.execute(command.flat());
		
		//get result from the previous iteration
		AC= ((IterativeSearchEngine1)main1.getEngine()).AC;
		SMC= ((IterativeSearchEngine1)main1.getEngine()).SMC;
		timeout= ((IterativeSearchEngine1)main1.getEngine()).timeout;
		mxg= ((IterativeSearchEngine1)main1.getEngine()).MAxGen;
		exh=((IterativeSearchEngine1)main1.getEngine()).exhausted;
		fl=((IterativeSearchEngine1)main1.getEngine()).failt;
		String cps=((IterativeSearchEngine1)main1.getEngine()).cpy;
		String cpb=((IterativeSearchEngine1)main1.getEngine()).cpy1;
		int nbop=((IterativeSearchEngine1)main1.getEngine()).operatorExecuted;
		int g= Integer.parseInt(mxgn)-nbop;
		    if(AC || SMC)
		    {
		    	//find the modified File and update src and bin of the original project & save solution:
            	
	           	 Stats s=main1.getEngine().getCurrentStat();
	           	 PatchStat patchstats = main1.getEngine().getPatchInfo().get(0);

	   			Stats stats = Stats.getCurrentStat();

	   			assertNotNull(stats);

	   			assertEquals(1, main1.getEngine().getPatchInfo().size());

	   			List<PatchHunkStats> hunksApi = (List<PatchHunkStats>) patchstats.getStats().get(PatchStatEnum.HUNKS);

	   			assertNotNull(hunksApi);

	   			PatchHunkStats hunkStats = hunksApi.get(0);
	   			String mpath = hunkStats.getStats().get(HunkStatEnum.MODIFIED_FILE_PATH).toString();
	   			String []st=mpath.split("//");
	   			String newst="";
	   			for (int i=0; i<st.length-2;i++) newst=st[i]+"/";	
	   			
	   		    String sb= mpath.substring(mpath.indexOf("/org"));
	   		    String sb1=sb.substring(0,sb.indexOf(".java"));
	   		    sb1=sb1+".class";
	   		    FileUtils.copyFile(new File(cps+sb),new File(solLocation+"/src/main/java"+sb));
	   			FileUtils.copyFile(new File(cpb+sb1),new File(solLocation+"/target/classes"+sb1));
	   			solutions = main1.getEngine().getSolutions();
	   			
		    }
			
		if(SMC) {  	       
	      			nb++;
	      		
		    //new argument
		    out = new File(ConfigurationProperties.getProperty("workingDirectory")+nb);
			if (out.exists()) 
			out.delete();
		    int  t=(int) ((Integer.parseInt(mxt))-((IterativeSearchEngine1)main1.getEngine()).time);
		    args = new String[] { "-dependencies", dep, "-failing",
					fl+":" ,"-location",
					new File(solLocation).getAbsolutePath(),"-package", "org.apache.commons", "-srcjavafolder",
					"/src/main/java/", "-srctestfolder", "/src/test/java/", "-binjavafolder", "/target/classes",
					"-bintestfolder", "/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
					out.getAbsolutePath(),"-scope", "local", "-stopfirst", "true","-population", "1", "-maxtime", Integer.toString(t),"-maxgen", Integer.toString(g),
					
			
		    };
		  
		   
		    command = new CommandSummary(args);
			command.command.put("-loglevel", "INFO");
			command.command.put("-mode", "custom");
			command.command.put("-customengine",
					IterativeSearchEngine1.class.getCanonicalName());
			command.command.put("-validation", RCValidator.class.getCanonicalName());
			command.command.put("-parameters", "runexternalvalidator:true:maxmodificationpoints:"+nbmodp+":saveall:true:logtestexecution:true:maxsuspcandidates:300:limitbysuspicious:false" 

					+ ":autocompile:false:parsesourcefromoriginal:true");
			
		}
		}
		while(!AC && !timeout && ! mxg && ! exh);
		
			assertEquals(1, solutions.size());
		

	}
	
	@Test
	public void test1MathMsf() throws Exception {
		//test case where we combine fault
		int nb=0;
		String mxt="800";
		String mxgn="20000";
	    String nbmodp="4";
	    String fl="";
		List<ProgramVariant> solutions=null ;
	    //file where we save smc node
	    String solLocation="./examples/solution";
	    File sol = new File(solLocation);
	    if(sol.exists()) sol.delete();
	    FileUtils.copyDirectory(new File("./examples/Math_msf"), new File(solLocation));
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		if (out.exists()) 
		out.delete();			
		String[] args = new String[] { "-dependencies", dep, "-failing",
				"org.apache.commons.math.complex.ComplexTest", "-location",
				new File("./examples/Math_msf").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/main/java/", "-srctestfolder", "/src/test/java/", "-binjavafolder", "/target/classes",
				"-bintestfolder", "/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-stopfirst", "true","-population", "1", "-maxtime", mxt,"-maxgen", mxgn,
				
		};
		boolean AC;
		boolean SMC;
		boolean timeout;
		boolean mxg;
		boolean exh;
		CommandSummary command = new CommandSummary(args);
		command.command.put("-loglevel", "INFO");
		command.command.put("-mode", "custom");
		command.command.put("-customengine",
				IterativeSearchEngine1.class.getCanonicalName());
		command.command.put("-validation", RCValidator.class.getCanonicalName());
		command.command.put("-parameters",

				"maxnumbersolutions:1:runexternalvalidator:true:logtestexecution:true:maxmodificationpoints:"

				+ nbmodp + ":maxsuspcandidates:300:limitbysuspicious:false:parsesourcefromoriginal:true");
		
		do {
					
		System.out.println(Arrays.toString(args));
		main1.execute(command.flat());
		
		//get result from the previous iteration
		AC= ((IterativeSearchEngine1)main1.getEngine()).AC;
		SMC= ((IterativeSearchEngine1)main1.getEngine()).SMC;
		timeout= ((IterativeSearchEngine1)main1.getEngine()).timeout;
		mxg= ((IterativeSearchEngine1)main1.getEngine()).MAxGen;
		exh=((IterativeSearchEngine1)main1.getEngine()).exhausted;
		fl=((IterativeSearchEngine1)main1.getEngine()).failt;
		String cps=((IterativeSearchEngine1)main1.getEngine()).cpy;
		String cpb=((IterativeSearchEngine1)main1.getEngine()).cpy1;
		int nbop=((IterativeSearchEngine1)main1.getEngine()).operatorExecuted;
		int g= Integer.parseInt(mxgn)-nbop;
		    if(AC || SMC)
		    {
		    	//find the modified File and update src and bin of the original project & save solution:
            	
	           	 Stats s=main1.getEngine().getCurrentStat();
	           	 PatchStat patchstats = main1.getEngine().getPatchInfo().get(0);

	   			Stats stats = Stats.getCurrentStat();

	   			assertNotNull(stats);

	   			assertEquals(1, main1.getEngine().getPatchInfo().size());

	   			List<PatchHunkStats> hunksApi = (List<PatchHunkStats>) patchstats.getStats().get(PatchStatEnum.HUNKS);

	   			assertNotNull(hunksApi);

	   			PatchHunkStats hunkStats = hunksApi.get(0);
	   			String mpath = hunkStats.getStats().get(HunkStatEnum.MODIFIED_FILE_PATH).toString();
	   			String []st=mpath.split("//");
	   			String newst="";
	   			for (int i=0; i<st.length-2;i++) newst=st[i]+"/";	
	   			
	   		    String sb= mpath.substring(mpath.indexOf("/org"));
	   		    String sb1=sb.substring(0,sb.indexOf(".java"));
	   		    sb1=sb1+".class";
	   		    FileUtils.copyFile(new File(cps+sb),new File(solLocation+"/src/main/java"+sb));
	   			FileUtils.copyFile(new File(cpb+sb1),new File(solLocation+"/target/classes"+sb1));
	   			solutions = main1.getEngine().getSolutions();
	   			
		    }
			
		if(SMC) {  	       
	      			nb++;
	      		
		    //new argument
		    out = new File(ConfigurationProperties.getProperty("workingDirectory")+nb);
			if (out.exists()) 
			out.delete();
		    int  t=(int) ((Integer.parseInt(mxt))-((IterativeSearchEngine1)main1.getEngine()).time);
		    args = new String[] { "-dependencies", dep, "-failing",
					fl+":" ,"-location",
					new File(solLocation).getAbsolutePath(),"-package", "org.apache.commons", "-srcjavafolder",
					"/src/main/java/", "-srctestfolder", "/src/test/java/", "-binjavafolder", "/target/classes",
					"-bintestfolder", "/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
					out.getAbsolutePath(),"-scope", "local", "-stopfirst", "true","-population", "1", "-maxtime", Integer.toString(t),"-maxgen", Integer.toString(g),
					
			
		    };
		  
		   
		    command = new CommandSummary(args);
			command.command.put("-loglevel", "INFO");
			command.command.put("-mode", "custom");
			command.command.put("-customengine",
					IterativeSearchEngine1.class.getCanonicalName());
			command.command.put("-validation", RCValidator.class.getCanonicalName());
			command.command.put("-parameters", "runexternalvalidator:true:maxmodificationpoints:"+nbmodp+":saveall:true:logtestexecution:true:maxsuspcandidates:300:limitbysuspicious:false" 

					+ ":autocompile:false:parsesourcefromoriginal:true");
			
		}
		}
		while(!AC && !timeout && ! mxg && ! exh);
		
			assertEquals(1, solutions.size());
		

	}
	
	
	

}
