// $Id: CompanyResource.java 6699 2018-04-18 14:58:06Z g801797 $
// $URL: https://am1p-gen-ias-vcs001.syniverse.com/svn-am/obf/obf-rest/branches/obf_dev_Victor/src/main/java/com/syniverse/obf/company/ui/CompanyResource.java $
package com.syniverse.wdm.interview.armedforces.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.repository.ArmedForcesRepository;
import com.syniverse.wdm.interview.armedforces.view.ArmyDetailsView;
import com.syniverse.wdm.interview.armedforces.view.ArmyInputView;
import com.syniverse.wdm.interview.armedforces.view.UnitDetailsView;
import com.syniverse.wdm.interview.armedforces.view.UnitInputView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/armed-forces/v1/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ArmedForcesController {

  private final ArmedForcesRepository armedForcesRepository;

  @ApiOperation(value = "Create an army", notes = "Returns the ID of the newly created army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
  @PostMapping("/armies")
  public Long createArmy(@RequestBody final ArmyInputView army) {
    return this.armedForcesRepository.createArmy(Optional.ofNullable(army).map(ArmyInputView::toArmy).orElse(null));
  }
  @ApiOperation(value = "Remove the army", notes = "Returns result of removing the army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
  @DeleteMapping("/armies/{armyId:[\\d]+}")
  public boolean removeArmy(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.removeAmry(armyId);
  }
  
  

  @ApiOperation(value = "List the summary of all the armies", notes = "Returns a list of all armies")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies")
  public List<ArmyDetailsView> getArmies() {
    return this.armedForcesRepository.getArmies().stream().map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
  }
  @ApiOperation(value = "List armies of a given type", notes = "Returns a list armies of a given type")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies/type/{type}")
  public List<ArmyDetailsView> getArmiesByType(@PathVariable(name = "type")  final String type) {
    return this.armedForcesRepository.getArmiesOfGivenType(ArmyType.valueOf(type)).stream().map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
  }
  @ApiOperation(value = "Recruit a unit to the army", notes = "Returns the ID of the newly recruited unit")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
  @PostMapping("/armies/{armyId:[\\d]+}/units")
  public Long recruitUnit(@PathVariable(name = "armyId") final Long armyId, @RequestBody final UnitInputView unit) {
    return this.armedForcesRepository.recruitUnit(armyId, Optional.ofNullable(unit).map(UnitInputView::toUnit).orElse(null));
  }

  @ApiOperation(value = "Fetch all units of the army", notes = "Returns a list of all units of the army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies/{armyId:[\\d]+}/units")
  public List<UnitDetailsView> getUnitsOfArmy(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.getUnitsOfArmy(armyId).stream().map(UnitDetailsView::fromUnit).collect(Collectors.toList());
  }
  
  
  @ApiOperation(value = "Fetch the army’ details", notes = "Returns the army’ details")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "Object")})
  @GetMapping("/armies/{armyId:[\\d]+}")
  public ArmyDetailsView getArmyById(@PathVariable(name = "armyId") final Long armyId) {
    return ArmyDetailsView.fromArmy(this.armedForcesRepository.getArmyById(armyId));
  }
  @ApiOperation(value = "The given unit killed/destroyed (removed from the army)", notes = "Returns boolean result removed unit")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
  @DeleteMapping("/armies/{armyId:[\\d]+}/units/{unitId:[\\d]+}")
  public boolean removeUnit(@PathVariable(name = "armyId") final Long armyId, @PathVariable(name = "unitId") final Long unitId) {
    return this.armedForcesRepository.removeUnit(armyId, unitId);
  }

  
  @ApiOperation(value = "Fetch the unit details", notes = "Returns the unit of the army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class)})
  @GetMapping("/armies/{armyId:[\\d]+}/units/{unitId:[\\d]+}")
  public UnitDetailsView getUnitOfArmy(@PathVariable(name = "armyId") final Long armyId, @PathVariable(name = "unitId") final Long unitId) {
    return UnitDetailsView.fromUnit(this.armedForcesRepository.getUnitOfArmy(armyId, unitId)) ;
  }
  
  @ApiOperation(value = "The strongest unit (by combat power) killed/destroyed (removed from the army)", notes = "Returns boolean result removed strongest unit")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
  @DeleteMapping("/armies/{armyId:[\\d]+}/units/strongest")
  public boolean removeStrongestUnitOfArmy(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.removeStrongestUnitOfArmy(armyId);
  }
 
  @ApiOperation(value = "Fetch all units of the army sorted by combat power descending", notes = "Returns a list of all stored units of the army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies/{armyId:[\\d]+}/units/sorted")
  public List<UnitDetailsView> getUnitsOfArmySortedPower(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.getUnitsOfArmySortedPower(armyId).stream().map(UnitDetailsView::fromUnit).collect(Collectors.toList());
  }
  
  @ApiOperation(value = "Fetch those units of the army which have combat power 50 or more", notes = "Returns a list units of the army which have combat power 50 or more")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies/{armyId:[\\d]+}/units/power50")
  public List<UnitDetailsView> getUnitsWithPower50OrMore(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.getUnitsWithPower50OrMore(armyId).stream().map(UnitDetailsView::fromUnit).collect(Collectors.toList());
  }

  
  @ApiOperation(value = " Merge one or more armies into the army identified by ID ", notes = "Returns boolean result merge armies")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
  @PutMapping("/armies/{armyId:[\\d]+}/units/{mergeArmyIds}")
   public boolean merge(@PathVariable(name = "armyId") final Long armyId,@PathVariable("mergeArmyIds") List<Long> mergeArmyIds) {
    return this.armedForcesRepository.mergeArmyIds(armyId, mergeArmyIds);
  }
}
