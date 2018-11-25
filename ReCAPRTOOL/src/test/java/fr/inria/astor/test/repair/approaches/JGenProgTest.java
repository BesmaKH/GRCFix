package fr.inria.astor.test.repair.approaches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;

import fr.inria.astor.approaches.jgenprog.JGenProg;
import fr.inria.astor.approaches.jgenprog.operators.ReplaceOp;
import fr.inria.astor.core.entities.Ingredient;
import fr.inria.astor.core.entities.OperatorInstance;
import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.astor.core.entities.TestCaseVariantValidationResult;
import fr.inria.astor.core.manipulation.sourcecode.VariableResolver;
import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.astor.core.solutionsearch.population.PopulationConformation;
import fr.inria.astor.test.repair.core.BaseEvolutionaryTest;
import fr.inria.astor.test.repair.evaluation.regression.MathCommandsTests;
import fr.inria.main.AstorOutputStatus;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

/**
 * Test of Astor in mode jgenprog
 * 
 * @author Matias Martinez, matias.martinez@inria.fr
 *
 */
public class JGenProgTest extends BaseEvolutionaryTest {

	File out = null;

	public JGenProgTest() {
		out = new File(ConfigurationProperties.getProperty("workingDirectory"));
	}
	@Ignore
	public void testMat_c() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.complex.ComplexTest", "-location",
				new File("./examples/Math_c").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "200",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Ignore
	public void testMatb() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.analysis.solvers.BrentSolverTest", "-location",
				new File("./examples/Math_b").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "200",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Ignore
	public void testMatbs() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.analysis.solvers.BisectionSolverTest","location",
				new File("./examples/Math_70").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "200",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	
	
	@Test
	public void testMathMF() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.complex.ComplexTest:org.apache.commons.math.analysis.solvers.BrentSolverTest:org.apache.commons.math.analysis.solvers.BisectionSolverTest", "-location",
				new File("./examples/Math_mf").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),			//
				"-scope", "local", "-seed", "10", "-maxgen", "4000", "-stopfirst", "true", "-maxtime", "1000",
				"-population", "10","applyCrossover","true","-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Test
	public void testMathMsF() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.complex.ComplexTest", "-location",
				new File("./examples/Math_msf").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "4000", "-stopfirst", "true", "-maxtime", "2000",
				"-population", "10","applyCrossover","true", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Test
	public void testMathMsF1() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.complex.ComplexTest", "-location",
				new File("./examples/Math_67_ms").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "200",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
		
	@Ignore
	public void testMath70() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.analysis.solvers.BisectionSolverTest:", "-location",
				new File("./examples/Math_70").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "30",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Ignore
	public void testMathb() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.analysis.solvers.BrentSolverTest:", "-location",
				new File("./examples/Math_b").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "30",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	@Ignore
	public void testMathc() throws Exception {
		AstorMain main1 = new AstorMain();
		String dep = new File("./examples/libs/junit-4.4.jar").getAbsolutePath();
		File out = new File(ConfigurationProperties.getProperty("workingDirectory"));
		String[] args = new String[] { "-dependencies", dep, "-mode", "jgenprog", "-failing",
				"org.apache.commons.math.complex.ComplexTest:", "-location",
				new File("./examples/Math_c").getAbsolutePath(), "-package", "org.apache.commons", "-srcjavafolder",
				"/src/java/", "-srctestfolder", "/src/test/", "-binjavafolder", "/target/classes", "-bintestfolder",
				"/target/test-classes", "-javacompliancelevel", "7", "-flthreshold", "1", "-out",
				out.getAbsolutePath(),
				//
				"-scope", "local", "-seed", "10", "-maxgen", "2000", "-stopfirst", "true", "-maxtime", "30",
				"-population", "1", "-reintroduce", PopulationConformation.PARENTS.toString(), "-parameters",
				"maxnumbersolutions:1" };
		System.out.println(Arrays.toString(args));
		CommandSummary command = new CommandSummary(args);

		command.command.put("-parameters", "maxnumbersolutions:1");
		main1.execute(command.flat());

		List<ProgramVariant> solutions = main1.getEngine().getSolutions();
		assertEquals(1, solutions.size());



	}
	
}
