package com.syniverse.wdm.interview.armedforces.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Profile("repo-collections")
@Repository
public class ArmedForcesRepositoryCollectionImpl implements ArmedForcesRepository {

	private final Map<Long, Army> armies = new ConcurrentHashMap<>();

	@PostConstruct
	protected void initializeData() {

		// @formatter:off
		this.armies.put(1L, Army.builder().id(1L).name("North navy").type(ArmyType.NAVY)
				.units(new ArrayList<>(
						Arrays.asList(Unit.builder().id(1L).combatPower(20L).type(UnitType.CORVETTE).build(),
								Unit.builder().id(2L).combatPower(80L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(3L).combatPower(30L).type(UnitType.CORVETTE).build())))
				.build());

		this.armies.put(2L, Army.builder().id(2L).name("South navy").type(ArmyType.NAVY)
				.units(new ArrayList<>(
						Arrays.asList(Unit.builder().id(1L).combatPower(25L).type(UnitType.CORVETTE).build(),
								Unit.builder().id(2L).combatPower(55L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(3L).combatPower(45L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(4L).combatPower(65L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(5L).combatPower(35L).type(UnitType.CORVETTE).build(),
								Unit.builder().id(6L).combatPower(45L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(7L).combatPower(55L).type(UnitType.CORVETTE).build(),
								Unit.builder().id(8L).combatPower(65L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(9L).combatPower(95L).type(UnitType.AIRCRAFT_CARRIER).build(),
								Unit.builder().id(10L).combatPower(25L).type(UnitType.CORVETTE).build())))
				.build());

		this.armies.put(3L, Army.builder().id(3L).name("Royal Air Force").type(ArmyType.AIR_FORCE)
				.units(new ArrayList<>(
						Arrays.asList(Unit.builder().id(1L).combatPower(25L).type(UnitType.FIGHTER_JET).build(),
								Unit.builder().id(2L).combatPower(55L).type(UnitType.BOMBER).build(),
								Unit.builder().id(3L).combatPower(45L).type(UnitType.FIGHTER_JET).build(),
								Unit.builder().id(4L).combatPower(65L).type(UnitType.BOMBER).build(),
								Unit.builder().id(5L).combatPower(35L).type(UnitType.FIGHTER_JET).build(),
								Unit.builder().id(6L).combatPower(45L).type(UnitType.FIGHTER_JET).build(),
								Unit.builder().id(7L).combatPower(55L).type(UnitType.FIGHTER_JET).build(),
								Unit.builder().id(8L).combatPower(65L).type(UnitType.BOMBER).build(),
								Unit.builder().id(9L).combatPower(95L).type(UnitType.BOMBER).build(),
								Unit.builder().id(10L).combatPower(25L).type(UnitType.FIGHTER_JET).build())))
				.build());

		this.armies.put(4L, Army.builder().id(4L).name("15th Army").type(ArmyType.INFANTRY)
				.units(new ArrayList<>(
						Arrays.asList(Unit.builder().id(1L).combatPower(2L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(2L).combatPower(5L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(3L).combatPower(4L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(4L).combatPower(6L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(5L).combatPower(3L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(7L).combatPower(5L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(8L).combatPower(6L).type(UnitType.PARATROOPER).build(),
								Unit.builder().id(9L).combatPower(9L).type(UnitType.PARATROOPER).build())))
				.build());
		// @formatter:on
	}

	// done
	@Override
	public Long createArmy(final Army army) {
		if (this.armies.size() < 50) {
			final Long armyId = getNextArmyId();
			army.setId(armyId);
			this.armies.put(armyId, army);
			return armyId;
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot add more armies. You already have way too many to manage, Sir!");
		}
	}

	// done
	@Override
	public List<Army> getArmies() {
		return new ArrayList<>(this.armies.values());
	}

	// done
	@Override
	public Army getArmyById(final Long armyId) {
		return Optional.ofNullable(this.armies.get(armyId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Hmmm. That army does not seem to exist, Sir!"));
	}

	// done
	@Override
	public Long recruitUnit(final Long armyId, final Unit unit) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		// check army exist
		if (armyOptional.isPresent()) {
			Army amry = armyOptional.get();
			// check size unit in that army
			if (amry.getUnits().size() < 100) {
				// check unit type is not acceptable in that army
				if (amry.getType() == unit.getType().getArmyType()) {
					final Long unitId = getNextUnitId(amry);
					// check combat power
					if (unit.getCombatPower() <= 100 && unit.getCombatPower() >= 1) {
						amry.getUnits().add(Unit.builder().id(unitId).combatPower(unit.getCombatPower())
								.type(unit.getType()).build());
						return unitId;
					} else {
						throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
								"Cannot add units with combat power less than 1 or greater than 100, Sir!");
					}
				} else {
					throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
							"Cannot add units. Unit type is not acceptable in that army  , Sir!");
				}

			} else {
				throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Cannot add more units. Unit in one army less than 100, Sir!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot add unit to army. Army not existed!");
		}
	}

	// done
	@Override
	public List<Unit> getUnitsOfArmy(final Long armyId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			return getArmyById(armyId).getUnits();
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot add unit to army. Army not existed!");
		}
	}

	// done
	@Override
	public List<Army> getArmiesOfGivenType(ArmyType type) {
		List<Army> listArmies = new ArrayList<>();
		this.armies.forEach((armyId, army) -> {
			if (army.getType() == type) {
				listArmies.add(army);
			}
		});
		return listArmies;
	}

//question 11
	@Override
	public boolean removeUnit(Long armyId, Long unitId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		boolean isRemoved = false;
		if (armyOptional.isPresent()) {
			if (armyOptional.get().getUnits().removeIf(unit -> unit.getId() == unitId)) {
				isRemoved = true;
			}
			if (armyOptional.get().getUnits().size() == 0) {
				this.armies.remove(armyId);
			}
			return isRemoved;
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot add unit to army. Army not existed!");
		}

	}

	@Override
	public List<Unit> getUnitsOfArmySortedPower(Long armyId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			List<Unit> units = armyOptional.get().getUnits();
			units.sort(Comparator.comparing(Unit::getCombatPower).reversed());
			return units;
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot get unit of army with sorted compat power. Army not existed!");
		}

	}

	@Override
	public List<Unit> getUnitsWithPower50OrMore(Long armyId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			List<Unit> unit = armyOptional.get().getUnits().stream().filter(x -> x.getCombatPower() >= 50)
					.collect(Collectors.toList());
			return unit;
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cannot get unit of army with sorted compat power. Army not existed!");
		}

	}

	@Override
	public Unit getUnitOfArmy(Long armyId, Long unitId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			return armyOptional.get().getUnits().stream().filter(p -> p.getId() == unitId).findFirst().get();
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Army not existed!");
		}

	}

	@Override
	public boolean removeStrongestUnitOfArmy(Long armyId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			List<Unit> units = new ArrayList<Unit>(armyOptional.get().getUnits());
			Unit unit = Collections.max(units, Comparator.comparing(s -> s.getCombatPower()));
			Optional<Unit> unitOptional = Optional.ofNullable(unit);
			if (unitOptional.isPresent()) {
				units.remove(unit);
				this.armies.get(armyId).setUnits(units);
				return true;
			} else {
				return false;
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Army not existed!");
		}
	}

	@Override
	public boolean removeStrongestUnit(Long armyId) {
		// TODO Auto-generated method stub
		return false;
	}

	private Long getNextArmyId() {
		return (this.armies.keySet().isEmpty() ? 0L : Collections.max(this.armies.keySet())) + 1L;
	}

	private Long getNextUnitId(final Army army) {
		return (army.getUnits().isEmpty() ? 0L
				: Collections.max(army.getUnits().stream().map(Unit::getId).collect(Collectors.toList()))) + 1L;
	}

	@Override
	public boolean removeAmry(Long armyId) {
		Optional<Army> armyOptional = Optional.ofNullable(this.armies.get(armyId));
		if (armyOptional.isPresent()) {
			if (armyOptional.get().getUnits().isEmpty()) {
				this.armies.remove(armyId);
				return true;
			} else {
				throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Can not remove army when units still existed, Sir!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Army not existed!");
		}
	}

}
