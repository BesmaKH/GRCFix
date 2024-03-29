package fr.inria.astor.core.entities;

/**
 * Represents the result of a validation process. 
 * Indicates if a variant is valid or not
 * @author Matias Martinez
 *
 */
public interface VariantValidationResult {

	public boolean isSuccessful();

	public int isAC(int[] tab0, int[] tab1, int[] tab2);
}
