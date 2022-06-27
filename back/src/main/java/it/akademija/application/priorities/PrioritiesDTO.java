package it.akademija.application.priorities;

public class PrioritiesDTO {

	private boolean livesInVilnius;

	private boolean childIsAdopted;

	private boolean familyHasThreeOrMoreChildrenInSchools;

	private boolean guardianInSchool;

	private boolean guardianDisability;
	
	private boolean parentLivedInVilniusMoreThan2Years;

	public PrioritiesDTO() {

	}

	public PrioritiesDTO(boolean livesInVilnius, boolean childIsAdopted, boolean familyHasThreeOrMoreChildrenInSchools,
			boolean guardianInSchool, boolean guardianDisability, boolean parentLivedInVilniusMoreThan2Years) {
		super();
		this.livesInVilnius = livesInVilnius;
		this.childIsAdopted = childIsAdopted;
		this.familyHasThreeOrMoreChildrenInSchools = familyHasThreeOrMoreChildrenInSchools;
		this.guardianInSchool = guardianInSchool;
		this.guardianDisability = guardianDisability;
		this.parentLivedInVilniusMoreThan2Years = parentLivedInVilniusMoreThan2Years;
	}

	public boolean isLivesInVilnius() {
		return livesInVilnius;
	}

	public void setLivesInVilnius(boolean livesInVilnius) {
		this.livesInVilnius = livesInVilnius;
	}

	public boolean isChildIsAdopted() {
		return childIsAdopted;
	}

	public void setChildIsAdopted(boolean childIsAdopted) {
		this.childIsAdopted = childIsAdopted;
	}

	public boolean isFamilyHasThreeOrMoreChildrenInSchools() {
		return familyHasThreeOrMoreChildrenInSchools;
	}

	public void setFamilyHasThreeOrMoreChildrenInSchools(boolean familyHasThreeOrMoreChildrenInSchools) {
		this.familyHasThreeOrMoreChildrenInSchools = familyHasThreeOrMoreChildrenInSchools;
	}

	public boolean isGuardianInSchool() {
		return guardianInSchool;
	}

	public void setGuardianInSchool(boolean guardianInSchool) {
		this.guardianInSchool = guardianInSchool;
	}

	public boolean isGuardianDisability() {
		return guardianDisability;
	}

	public void setGuardianDisability(boolean guardianDisability) {
		this.guardianDisability = guardianDisability;
	}

	public boolean isParentLivedInVilniusMoreThan2Years() {
		return parentLivedInVilniusMoreThan2Years;
	}

	public void setParentLivedInVilniusMoreThan2Years(boolean parentLivedInVilniusMoreThan2Years) {
		this.parentLivedInVilniusMoreThan2Years = parentLivedInVilniusMoreThan2Years;
	}
}
