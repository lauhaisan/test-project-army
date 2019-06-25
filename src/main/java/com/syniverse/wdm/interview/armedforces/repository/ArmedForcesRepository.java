package com.syniverse.wdm.interview.armedforces.repository;

import java.util.List;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;

import org.springframework.stereotype.Repository;

@Repository
public interface ArmedForcesRepository {

	// question 2
	public Long createArmy(Army army);

	// question 3
	public List<Army> getArmies();

	//question 4
	// List armies of a given type
	// Liệt kê các đội quân của một loại nhất định
	public List<Army> getArmiesOfGivenType(UnitType type);

	// question 5
	// Fetch the army’ details
	public Army getArmyById(Long armyId);

	// question 6
	public Long recruitUnit(Long armyId, Unit unit);

	// question 7
	public List<Unit> getUnitsOfArmy(Long armyId);
		
		
	// question 8
	public List<Unit> getUnitsOfArmySortedPower(Long armyId);
	
	// question 9
	public List<Unit> getUnitsWithPower50OrMore();
	
	//question 10
	public Unit getUnitOfArmy(Long armyId, Long unitId);
	
	//question 11
	public boolean removeUnitOfArmy(Long armyId, Long unitId);
	
	// question 12
	public boolean removeStrongestUnitOfArmy(Long armyId);
	
	//question 13
	public boolean removeStrongestUnit(Long armyId);

}
