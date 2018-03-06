package org.springframework.roo.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.petclinic.config.DataSourceConfigurationValues;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * Controller which handles DataSource configuration view
 */
@Controller
@RequestMapping("/datasource")
public class DatasourceConfigurationController
{

  private DataSourceConfigurationValues values;

  @Autowired
  public DatasourceConfigurationController(DataSourceConfigurationValues values)
  {
    this.values = values;
  }

  /**
   * @return Datasource configuration form
   */
  @GetMapping
  public String getConfigurationForm(){
    return "datasource";
  }

  /**
   * Handles post request to set DataSource configuration
   *
   * @param filePath
   * @return
   */
  @PostMapping
  public String updateValue(@RequestParam String filePath) {
    // Update configuration
    values.setFile( new File(filePath) );
    // Redired to index
    return "redirect:/";
  }

}
